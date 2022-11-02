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

public final class JSONDataParser {

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

	private ParsedJson parsedJson;
	private final Gson gson = new GsonBuilder().serializeNulls().create();

	public JSONDataParser(StorageLoadTypes type) {

		if (type.equals(StorageLoadTypes.DEFAULT)) {
			InputStream stream = JSONDataParser.class.getClassLoader().getResourceAsStream(DEFAULT_JSON);
			parsedJson = gson.fromJson(new InputStreamReader(stream), ParsedJson.class);
		}

		else {

			File previousSessionFile = new File(PREVIOUS_SESSION_JSON_FILENAME);
			if (!previousSessionFile.isFile())
				try {
					previousSessionFile.createNewFile();
				} catch (IOException e1) {
					throw new RuntimeException("Error while creating new previousSession.json");
				}

			try {
				parsedJson = gson.fromJson(new FileReader(PREVIOUS_SESSION_JSON_FILENAME), ParsedJson.class);
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				throw new RuntimeException(
						"Error while parsing json through GSON. Cannot find file " + PREVIOUS_SESSION_JSON_FILENAME, e);
			}
		}

		if (parsedJson == null)
			parsedJson = new ParsedJson();
	}

	public boolean validateData() {
		if (parsedJson == null || parsedJson.issueDatabase == null || parsedJson.employeeDatabase == null)
			return false;
		if (parsedJson.issueDatabase.currentID == null || parsedJson.employeeDatabase.currentID == null)
			return false;
		if (parsedJson.issueDatabase.issues == null || parsedJson.employeeDatabase.employees == null)
			return false;

		return true;
	}

	public int getCurrentEmployeeID() {
		return parsedJson.employeeDatabase.currentID;
	}

	public Map<String, DBEmployee> getEmployees() {
		Map<String, DBEmployee> employees = new HashMap<>();
		parsedJson.employeeDatabase.employees.forEach(employee -> {
			employees.put(employee.getEmployeeID(), employee);
		});
		return employees;
	}

	public void saveEmployees(int currentEmployeeID, Collection<DBEmployee> employees)
			throws IOException {
		this.parsedJson.employeeDatabase.currentID = currentEmployeeID;
		this.parsedJson.employeeDatabase.employees = new ArrayList<DBEmployee>(employees);
		Writer fileWriter = new FileWriter(PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(parsedJson, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

	public int getCurrentIssueID() {
		return parsedJson.issueDatabase.currentID;
	}

	public Map<String, DBIssue> getIssues() {
		Map<String, DBIssue> issues = new HashMap<>();
		parsedJson.issueDatabase.issues.forEach(issue -> {
			issues.put(issue.issueID, issue);
		});
		return issues;
	}

	public void saveIssues(int currentIssueID, Collection<DBIssue> issues) throws IOException {
		this.parsedJson.issueDatabase.currentID = currentIssueID;
		this.parsedJson.issueDatabase.issues = new ArrayList<DBIssue>(issues);
		Writer fileWriter = new FileWriter(PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(parsedJson, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

}
