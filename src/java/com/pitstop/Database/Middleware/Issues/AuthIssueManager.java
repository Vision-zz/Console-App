package com.pitstop.Database.Middleware.Issues;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Issues.IssueCategory;
import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Authentication.AuthLevelManager;
import com.pitstop.Database.Middleware.Authentication.AuthTokenUpdater;
import com.pitstop.Database.Models.Authentication.AuthLevel;
import com.pitstop.Database.Models.Issues.IssueDatabaseFunctions;

public class AuthIssueManager extends DBIssueManager implements AuthTokenUpdater {

	private final AuthLevelManager manager;
	private String token = null;

	private static final AuthLevel ADMIN_AUTH_LEVEL = AuthLevel.ADMIN;
	private static final AuthLevel ENGINEER_AUTH_LEVEL = AuthLevel.ENGINEER;
	private static final AuthLevel DEV_AUTH_LEVEL = AuthLevel.DEVELOPER;
	private static final String EXCEPTION_MESSAGE = "You are not authorized to make this action";

	public AuthIssueManager(IssueDatabaseFunctions database, AuthLevelManager manager) {
		super(database);
		this.manager = manager;
	}

	@Override
	public void setAuthToken(String token) {
		this.token = token;
	}

	@Override
	public void removeAuthToken() {
		this.token = null;
	}

	@Override
	public void assignIssue(Issue issue, SystemEngineer engineer) throws RuntimeException {
		if (!checkAuth(ADMIN_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		super.assignIssue(issue, engineer);
	}

	@Override
	public String createIssue(IssueCategory category, String description, Developer createdBy) {
		if (!checkAuth(DEV_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		return super.createIssue(category, description, createdBy);
	}

	@Override
	public Collection<Issue> getAllIssues() {
		if (!checkAuth(ADMIN_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		return super.getAllIssues();
	}

	@Override
	public Collection<Issue> getAssignedIssues(SystemEngineer engineer) {
		if (!checkAuth(ENGINEER_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		return super.getAssignedIssues(engineer);
	}

	@Override
	public Collection<Issue> getDevCreatedIssues(Developer developer) {
		if (!checkAuth(DEV_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		return super.getDevCreatedIssues(developer);
	}

	@Override
	public Issue getIssue(String issueID) {
		if (!checkAuth(ADMIN_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		return super.getIssue(issueID);
	}

	@Override
	public void resolveIssue(Issue issue) throws RuntimeException {
		if (!checkAuth(ENGINEER_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		super.resolveIssue(issue);
	}

	private boolean checkAuth(AuthLevel authLevel) {
		return manager.validateTokenAuthLevel(token, authLevel);
	}

}
