package com.pitstop.Database.Storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.pitstop.Core.Models.Issues.Issue;

import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Issues.IssueStorageManager;
import com.pitstop.Database.Models.Users.DBEmployee;
import com.pitstop.Database.Models.Users.EmployeeStorageManager;

public final class JSONDatamanager implements IssueStorageManager, EmployeeStorageManager {

	public enum LoadType {
		DEFAULT,
		LAST_SESSION
	}

	private class IssueJSON {
		private int currentID;
		private List<DBIssue> issues = new ArrayList<DBIssue>();
	}

	private class EmployeeJson {
		private int currentID;
		private List<DBEmployee> employees = new ArrayList<DBEmployee>();
	}

	private class ParsedJson {
		private EmployeeJson employeeDatabase = new EmployeeJson();
		private IssueJSON issueDatabase = new IssueJSON();
	}

	private static final String BASE_LOCATION = "./com/pitstop/Database/Storage/";
	private static final String DEFAULT_JSON_FILENAME = "defaultSession.json";
	private static final String PREVIOUS_SESSION_JSON_FILENAME = "previousSession";

	private final ParsedJson parsedJson;

	JSONDatamanager(LoadType type) {
		String location;
		if (type.equals(LoadType.DEFAULT))
			location = BASE_LOCATION + DEFAULT_JSON_FILENAME;
		else
			location = BASE_LOCATION + PREVIOUS_SESSION_JSON_FILENAME;

		Gson gson = new GsonBuilder().serializeNulls().create();
		try {
			parsedJson = gson.fromJson(new FileReader(location), ParsedJson.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			throw new RuntimeException("Error while parsing json through GSON", e);
		}
	}

	@Override
	public int getCurrentEmployeeID() {
		return parsedJson.employeeDatabase.currentID;
	}

	@Override
	public Map<String, DBEmployee> getEmployees() {
		Map<String, DBEmployee> employees = new HashMap<>();
		parsedJson.employeeDatabase.employees.forEach(employee -> {
			employees.put(employee.getEmployeeID(), employee);
		});
		return employees;
	}

	@Override
	public void saveEmployees(int currentIssueID, Collection<DBEmployee> employees) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCurrentIssueID() {
		return parsedJson.issueDatabase.currentID;
	}

	@Override
	public Map<String, DBIssue> getIssues() {
		Map<String, DBIssue> issues = new HashMap<>();
		parsedJson.issueDatabase.issues.forEach(issue -> {
			issues.put(issue.issueID, issue);
		});
		return issues;
	}

	@Override
	public void saveIssues(int currentIssueID, Collection<Issue> issues) {
		// TODO Auto-generated method stub

	}

}
