package com.pitstop.StorageManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Users.DBEmployee;

public final class JSONDataParser implements StorageDataManager {

	private class IssueJSON {
		Integer currentID;
		List<DBIssue> issues = new ArrayList<DBIssue>();
	}

	private class EmployeeJson {
		Integer currentID;
		List<DBEmployee> employees = new ArrayList<DBEmployee>();
	}

	private class ParsedJson {
		EmployeeJson employeeDatabase;
		IssueJSON issueDatabase;

		ParsedJson() {
			employeeDatabase = new EmployeeJson();
			issueDatabase = new IssueJSON();
		}

	}

	private static final String DEFAULT_JSON = "json/defaultSession.json";
	private static final String PREVIOUS_SESSION_JSON_FILENAME = "./previousSession.json";

	private ParsedJson defaultData = null;
	private ParsedJson previousData = null;

	private final Gson gson = new GsonBuilder().serializeNulls().create();

	private static JSONDataParser instance = null;

	public static JSONDataParser getInstance() {
		if (instance == null)
			instance = new JSONDataParser();
		return instance;
	}

	private JSONDataParser() {

		InputStream stream = JSONDataParser.class.getClassLoader().getResourceAsStream(DEFAULT_JSON);
		defaultData = gson.fromJson(new InputStreamReader(stream), ParsedJson.class);

		File previousSessionFile = new File(PREVIOUS_SESSION_JSON_FILENAME);
		if (!previousSessionFile.isFile())
			try {
				previousSessionFile.createNewFile();
			} catch (IOException e1) {
				throw new RuntimeException("Error while creating new previousSession.json");
			}

		try {
			defaultData = gson.fromJson(new FileReader(PREVIOUS_SESSION_JSON_FILENAME), ParsedJson.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			throw new RuntimeException(
					"Error while parsing json through GSON. Cannot find file " + PREVIOUS_SESSION_JSON_FILENAME, e);
		}

		if (defaultData == null)
			defaultData = new ParsedJson();
		if (previousData == null)
			previousData = new ParsedJson();

	}

	@Override
	public boolean validateData(StorageLoadTypes type) {
		ParsedJson data = getData(type); 
		if (data == null || data.issueDatabase == null || data.employeeDatabase == null)
			return false;
		if (data.issueDatabase.currentID == null || data.employeeDatabase.currentID == null)
			return false;
		if (data.issueDatabase.issues == null || data.employeeDatabase.employees == null)
			return false;

		return true;
	}

	@Override
	public int getCurrentEmployeeID(StorageLoadTypes type) {
		return getData(type).employeeDatabase.currentID;
	}

	@Override
	public Map<String, DBEmployee> getEmployees(StorageLoadTypes type) {
		Map<String, DBEmployee> employees = new HashMap<>();
		getData(type).employeeDatabase.employees.forEach(employee -> {
			employees.put(employee.getEmployeeID(), employee);
		});
		return employees;
	}

	@Override
	public void saveEmployees(int currentEmployeeID, Collection<DBEmployee> employees)
			throws IOException {
		this.previousData.employeeDatabase.currentID = currentEmployeeID;
		this.previousData.employeeDatabase.employees = new ArrayList<DBEmployee>(employees);
		Writer fileWriter = new FileWriter(PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(previousData, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

	@Override
	public int getCurrentIssueID(StorageLoadTypes type) {
		return getData(type).issueDatabase.currentID;
	}

	@Override
	public Map<String, DBIssue> getIssues(StorageLoadTypes type) {
		Map<String, DBIssue> issues = new HashMap<>();
		getData(type).issueDatabase.issues.forEach(issue -> {
			issues.put(issue.issueID, issue);
		});
		return issues;
	}

	@Override
	public void saveIssues(int currentIssueID, Collection<DBIssue> issues) throws IOException {
		this.previousData.issueDatabase.currentID = currentIssueID;
		this.previousData.issueDatabase.issues = new ArrayList<DBIssue>(issues);
		Writer fileWriter = new FileWriter(PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(previousData, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

	private ParsedJson getData(StorageLoadTypes type) {
		return type.equals(StorageLoadTypes.DEFAULT) ? this.defaultData : this.previousData;
	}

}
