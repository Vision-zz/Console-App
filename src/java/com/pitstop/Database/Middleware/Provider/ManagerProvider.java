package com.pitstop.Database.Middleware.Provider;

import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Database.Middleware.Issues.DBIssueManager;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;

public class ManagerProvider {

	public static DevIssueManager getDevIssueManager() {
		return DBIssueManager.getInstance();
	}

	public static EngineerIssueManager getEngineerIssueManager() {
		return DBIssueManager.getInstance();
	}

	public static AdminIssueManager getAdminIssueManager() {
		return DBIssueManager.getInstance();
	}

	public static EmployeeDetailsManager getEmployeeDetailsManager() {
		return DBEmployeeManager.getInstance();
	}

	public static EmployeeSignupManager getEmployeeSignupManager() {
		return DBEmployeeManager.getInstance();
	}

}
