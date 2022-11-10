package com.pitstop;

import com.pitstop.Authentication.Database.AuthenticationDatabase;
import com.pitstop.Authentication.Manager.AuthenicationWrapper.AuthEmployeeManager;
import com.pitstop.Authentication.Manager.AuthenicationWrapper.AuthIssueManager;
import com.pitstop.Authentication.Manager.AuthenticationManager.TokenLevelManager;
import com.pitstop.Authentication.Manager.AuthenticationManager.AuthTokenManager;
import com.pitstop.Authentication.Manager.AuthenticationManager.AuthenticationManager;
import com.pitstop.Authentication.Manager.AuthenticationManager.AuthLevelManager;
import com.pitstop.Authentication.Manager.SignInManager.AppAuthManager;
import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;
import com.pitstop.Session.SessionManager;
import com.pitstop.Session.SessionFunctions;

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

	public static TokenLevelManager getAuthLevelManager() {
		return AuthenticationManager.getInstance(AuthenticationDatabase.getInstance());
	}

	public static AuthTokenManager getAuthTokenManager() {
		return AuthenticationManager.getInstance(AuthenticationDatabase.getInstance());
	}

	public static AuthLevelManager getSessoinAuthTokenManager() {
		return AuthenticationManager.getInstance(AuthenticationDatabase.getInstance());
	}

	public static SessionFunctions getSession() {
		return SessionManager.getInstance(new AppAuthManager(getAuthTokenManager(),
				new DBEmployeeManager(EmployeeDatabase.getInstance())));
	}

}
