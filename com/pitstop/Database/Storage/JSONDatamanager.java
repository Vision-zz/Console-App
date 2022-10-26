package com.pitstop.Database.Storage;

import java.util.Collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Database.Models.Issues.IssueStorageManager;
import com.pitstop.Database.Models.Users.EmployeeStorageManager;

public final class JSONDatamanager implements IssueStorageManager, EmployeeStorageManager {

	enum JSONTypes {
		DEFAULT,
		LAST_SESSION;
	}

	private final JSONObject employeeDatabase;
	private final JSONObject issueDatabase;

	public JSONDatamanager(JSONTypes jsonType) throws ParseException {
		JSONParser parser = new JSONParser();

		JSONObject object;
		switch (jsonType) {
			case DEFAULT:
				object = (JSONObject) parser.parse("/com/pitstop/Database/Storage/defaultSession.json");
				break;

			case LAST_SESSION:
				object = (JSONObject) parser.parse("/com/pitstop/Database/Storage/previousSession.json");
				break;

			default:
				throw new RuntimeException("Unknown JSON type");
		}

		employeeDatabase = (JSONObject) object.get("employeeDatabase");
		issueDatabase = (JSONObject) object.get("issueDatabase");
	}

	@Override
	public int getCurrentEmployeeID() {
		int employeeID = (int) employeeDatabase.get("currentID");
		return employeeID;
	}

	@Override
	public Collection<Employee> getEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEmployees(int currentIssueID, Collection<Employee> employees) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCurrentIssueID() {
		int issueID = (int) issueDatabase.get("currentID");
		return issueID;
	}

	@Override
	public Collection<Issue> getIssues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveIssues(int currentIssueID, Collection<Issue> issues) {
		// TODO Auto-generated method stub
	}

}
