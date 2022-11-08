package com.pitstop;

import com.pitstop.Authentication.Database.SessionAuthDatabase;
import com.pitstop.Authentication.Manager.AuthEmployeeManager;
import com.pitstop.Authentication.Manager.AuthIssueManager;
import com.pitstop.Authentication.Manager.AuthLevelManager;
import com.pitstop.Authentication.Manager.AuthTokenManager;
import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;
import com.pitstop.UserInterface.Session.AuthManager.SessionAuthManager;

public class ManagerProvider {

	public static DevIssueManager getDevIssueManager() {
		return new AuthIssueManager(IssuesDatabase.getInstance(), getAuthLevelManager());
	}

	public static EngineerIssueManager getEngineerIssueManager() {
		return new AuthIssueManager(IssuesDatabase.getInstance(), getAuthLevelManager());
	}

	public static AdminIssueManager getAdminIssueManager() {
		return new AuthIssueManager(IssuesDatabase.getInstance(), getAuthLevelManager());
	}

	public static EmployeeDetailsManager getEmployeeDetailsManager() {
		return  new AuthEmployeeManager(EmployeeDatabase.getInstance(), getAuthLevelManager());
	}

	public static EmployeeSignupManager getEmployeeSignupManager() {
		return  new AuthEmployeeManager(EmployeeDatabase.getInstance(), getAuthLevelManager());
	}

	public static AuthLevelManager getAuthLevelManager(){
		return new SessionAuthManager(SessionAuthDatabase.getInstance());
	}

	public static AuthTokenManager getAuthTokenManager(){
		return new SessionAuthManager(SessionAuthDatabase.getInstance());
	}

}
