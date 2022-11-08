package com.pitstop.Database.Middleware.Provider;

import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Database.Middleware.Authentication.AuthLevelManager;
import com.pitstop.Database.Middleware.Authentication.AuthTokenManager;
import com.pitstop.Database.Middleware.Authentication.AuthTokenUpdater;
import com.pitstop.Database.Middleware.Authentication.SessionAuthManager;
import com.pitstop.Database.Middleware.Issues.AuthIssueManager;
import com.pitstop.Database.Middleware.Users.AuthEmployeeManager;
import com.pitstop.Database.Models.Authentication.SessionAuthDatabase;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class ManagerProvider {

	private static AuthIssueManager issueManager = null;
	private static AuthEmployeeManager employeeManager = null;
	private static SessionAuthManager authManager = null;

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

	public static AuthTokenUpdater getIssueManagerAuthUpdater() {
		return getIssueManager();
	}

	public static AuthTokenUpdater getEmployeeManagerAuthUpdater() {
		return getEmployeeManager();
	}

	public static AuthLevelManager getAuthLevelManager(){
		return getAuthManager();
	}

	public static AuthTokenManager getAuthTokenManager(){
		return getAuthManager();
	}

	private static AuthIssueManager getIssueManager() {
		if (issueManager == null)
			issueManager = new AuthIssueManager(IssuesDatabase.getInstance(), getAuthLevelManager());
		return issueManager;
	}

	private static AuthEmployeeManager getEmployeeManager() {
		if (employeeManager == null)
			employeeManager = new AuthEmployeeManager(EmployeeDatabase.getInstance(), getAuthLevelManager());
		return employeeManager;
	}

	private static SessionAuthManager getAuthManager() {
		if (authManager == null)
			authManager = new SessionAuthManager(SessionAuthDatabase.getInstance());
		return authManager;
	}

}
