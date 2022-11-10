package com.pitstop.StorageManager.Structure;

import java.util.ArrayList;
import java.util.List;

import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Users.DBEmployee;

public class ParsedStorageData {
	public class IssueData {
		private Integer currentID;
		private List<DBIssue> issues = new ArrayList<DBIssue>();

		public Integer getCurrentID() {
			return this.currentID;
		}

		public List<DBIssue> getIssues() {
			return this.issues;
		}

		public void setCurrentID(Integer currentID) {
			this.currentID = currentID;
		}

		public void addIssue(DBIssue issue) {
			this.issues.add(issue);
		}

	}

	public class EmployeeData {

		private Integer currentID;
		private List<DBEmployee> employees = new ArrayList<DBEmployee>();

		public Integer getCurrentID() {
			return this.currentID;
		}

		public List<DBEmployee> getEmployees() {
			return this.employees;
		}

		public void setCurrentID(Integer currentID) {
			this.currentID = currentID;
		}

		public void addEmployee(DBEmployee employee) {
			this.employees.add(employee);
		}
	}

	private EmployeeData employeeData;
	private IssueData issueData;

	public ParsedStorageData() {
		employeeData = new EmployeeData();
		issueData = new IssueData();
	}

	public EmployeeData getEmployeeData() {
		return this.employeeData;
	}

	public IssueData getIssueData() {
		return this.issueData;
	}

}
