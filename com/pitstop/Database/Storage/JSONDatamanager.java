package com.pitstop.Database.Storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import com.pitstop.Database.Middleware.Storage.StorageParseable;
import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Users.DBEmployee;

public final class JSONDatamanager implements StorageParseable {

	public enum LoadType {
		DEFAULT,
		LAST_SESSION
	}

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
			employeeDatabase= new EmployeeJson();
			issueDatabase = new IssueJSON();
		}

	}

	private static final String BASE_LOCATION = "./com/pitstop/Database/Storage/";
	private static final String DEFAULT_JSON_FILENAME = "defaultSession.json";
	private static final String PREVIOUS_SESSION_JSON_FILENAME = "previousSession.json";

	private ParsedJson parsedJson;
	private final Gson gson = new GsonBuilder().serializeNulls().create();

	public JSONDatamanager(LoadType type) {
		String location;
		if (type.equals(LoadType.DEFAULT))
			location = BASE_LOCATION + DEFAULT_JSON_FILENAME;
		else
			location = BASE_LOCATION + PREVIOUS_SESSION_JSON_FILENAME;

		try {
			parsedJson = gson.fromJson(new FileReader(location), ParsedJson.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			throw new RuntimeException("Error while parsing json through GSON", e);
		}

		if(parsedJson == null) parsedJson = new ParsedJson();
	}

	@Override
	public boolean validateData() {
		if (parsedJson == null || parsedJson.issueDatabase == null || parsedJson.employeeDatabase == null)
			return false;
		if (parsedJson.issueDatabase.currentID == null || parsedJson.employeeDatabase.currentID == null)
			return false;
		if (parsedJson.issueDatabase.issues == null || parsedJson.employeeDatabase.employees == null)
			return false;

		return true;
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
	public void saveEmployees(int currentEmployeeID, Collection<DBEmployee> employees)
			throws IOException {
		this.parsedJson.employeeDatabase.currentID = currentEmployeeID;
		this.parsedJson.employeeDatabase.employees = new ArrayList<DBEmployee>(employees);
		Writer fileWriter = new FileWriter(BASE_LOCATION + PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(parsedJson, fileWriter);
		fileWriter.flush();
		fileWriter.close();
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
	public void saveIssues(int currentIssueID, Collection<DBIssue> issues) throws IOException {
		System.out.println(this.parsedJson);
		System.out.println(this.parsedJson.issueDatabase);
		System.out.println(this.parsedJson.issueDatabase.currentID);
		this.parsedJson.issueDatabase.currentID = currentIssueID;
		this.parsedJson.issueDatabase.issues = new ArrayList<DBIssue>(issues);
		Writer fileWriter = new FileWriter(BASE_LOCATION + PREVIOUS_SESSION_JSON_FILENAME);
		gson.toJson(parsedJson, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

}
