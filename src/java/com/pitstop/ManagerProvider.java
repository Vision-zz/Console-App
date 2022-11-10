package com.pitstop;

import com.pitstop.Authentication.Database.SessionAuthDatabase;
import com.pitstop.Authentication.Manager.AuthenicationWrapper.AuthEmployeeManager;
import com.pitstop.Authentication.Manager.AuthenicationWrapper.AuthIssueManager;
import com.pitstop.Authentication.Manager.SessionAuthenticationManager.AuthLevelManager;
import com.pitstop.Authentication.Manager.SessionAuthenticationManager.DBAuthTokenManager;
import com.pitstop.Authentication.Manager.SessionAuthenticationManager.SessionAuthManager;
import com.pitstop.Authentication.Manager.SessionAuthenticationManager.SessionAuthTokenManager;
import com.pitstop.Authentication.Manager.SignInManager.SignInAuthenticationManager;
import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;
import com.pitstop.UserInterface.SessionManager.Session;
import com.pitstop.UserInterface.SessionManager.SessionFunctions;

public class ManagerProvider {

	public static DevIssueManager getDevIssueManager() {
		return new AuthIssueManager(IssuesDatabase.getInstance(), getSessoinAuthTokenManager());
	}

	public static EngineerIssueManager getEngineerIssueManager() {
		return new AuthIssueManager(IssuesDatabase.getInstance(), getSessoinAuthTokenManager());
	}

	public static AdminIssueManager getAdminIssueManager() {
		return new AuthIssueManager(IssuesDatabase.getInstance(), getSessoinAuthTokenManager());
	}

	public static EmployeeDetailsManager getEmployeeDetailsManager() {
		return new AuthEmployeeManager(EmployeeDatabase.getInstance(), getSessoinAuthTokenManager());
	}

	public static EmployeeSignupManager getEmployeeSignupManager() {
		return new AuthEmployeeManager(EmployeeDatabase.getInstance(), getSessoinAuthTokenManager());
	}

	public static AuthLevelManager getAuthLevelManager() {
		return SessionAuthManager.getInstance(SessionAuthDatabase.getInstance());
	}

	public static DBAuthTokenManager getAuthTokenManager() {
		return SessionAuthManager.getInstance(SessionAuthDatabase.getInstance());
	}

	public static SessionAuthTokenManager getSessoinAuthTokenManager() {
		return SessionAuthManager.getInstance(SessionAuthDatabase.getInstance());
	}

	public static SessionFunctions getSession() {
		return Session.getInstance(new SignInAuthenticationManager(getAuthTokenManager(),
				new DBEmployeeManager(EmployeeDatabase.getInstance())));
	}

}
