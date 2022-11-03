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

	public boolean validateData() {
		if (defaultData == null || defaultData.issueDatabase == null || defaultData.employeeDatabase == null)
			return false;
		if (defaultData.issueDatabase.currentID == null || defaultData.employeeDatabase.currentID == null)
			return false;
		if (defaultData.issueDatabase.issues == null || defaultData.employeeDatabase.employees == null)
			return false;

		return true;
	}

	public int getCurrentEmployeeID() {
		return defaultData.employeeDatabase.currentID;
	}

	public Map<String, DBEmployee> getEmployees() {
		Map<String, DBEmployee> employees = new HashMap<>();
		defaultData.employeeDatabase.employees.forEach(employee -> {
			employees.put(employee.getEmployeeID(), employee);
		});
		return employees;
	}

	public void saveEmployees(int currentEmployeeID, Collection<DBEmployee> employees)
			throws IOException {
		this.defaultData.employeeDatabase.currentID = currentEmployeeID;
		this.defaultData.employeeDatabase.employees = new ArrayList<DBEmployee>(employees);
		Writer fileWriter = new FileWriter(PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(defaultData, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

	public int getCurrentIssueID() {
		return defaultData.issueDatabase.currentID;
	}

	public Map<String, DBIssue> getIssues() {
		Map<String, DBIssue> issues = new HashMap<>();
		defaultData.issueDatabase.issues.forEach(issue -> {
			issues.put(issue.issueID, issue);
		});
		return issues;
	}

	public void saveIssues(int currentIssueID, Collection<DBIssue> issues) throws IOException {
		this.defaultData.issueDatabase.currentID = currentIssueID;
		this.defaultData.issueDatabase.issues = new ArrayList<DBIssue>(issues);
		Writer fileWriter = new FileWriter(PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(defaultData, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

}
