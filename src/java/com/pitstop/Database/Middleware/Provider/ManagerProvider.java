package com.pitstop.Database.Middleware.Provider;

import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Database.Middleware.Issues.AuthIssueManager;
import com.pitstop.Database.Middleware.Issues.DBIssueManager;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class ManagerProvider {

	private static DBIssueManager issueManager = null;
	private static DBEmployeeManager employeeManager = null;

	public static DevIssueManager getDevIssueManager() {
		return getIssueManager();
	}

	public static EngineerIssueManager getEngineerIssueManager() {
		return getIssueManager();
	}

	public static AdminIssueManager getAdminIssueManager() {
		return getIssueManager();
	}

	public static EmployeeDetailsManager getEmployeeDetailsManager() {
		return getEmployeeManager();
	}

	public static EmployeeSignupManager getEmployeeSignupManager() {
		return getEmployeeManager();
	}

	private static DBIssueManager getIssueManager() {
		if (issueManager == null)
			issueManager = new AuthIssueManager(IssuesDatabase.getInstance());
		return issueManager;
	}

	private static DBEmployeeManager getEmployeeManager() {
		if (employeeManager == null)
			employeeManager = new DBEmployeeManager(EmployeeDatabase.getInstance());
		return employeeManager;
	}

}
