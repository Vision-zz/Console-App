package com.pitstop.Database.Middleware.Provider;

import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;

public interface Provider {
	DevIssueManager getDevIssueManager();

	EngineerIssueManager getEngineerIssueManager();

	AdminIssueManager getAdminIssueManager();

	EmployeeDetailsManager getEmployeeDetailsManager();

	EmployeeSignupManager getEmployeeSignupManager();
}
