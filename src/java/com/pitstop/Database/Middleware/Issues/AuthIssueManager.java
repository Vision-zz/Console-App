package com.pitstop.Database.Middleware.Issues;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Issues.IssueCategory;
import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Authentication.AuthLevelManager;
import com.pitstop.Database.Middleware.Authentication.AuthTokenUpdater;
import com.pitstop.Database.Models.Issues.IssueDatabaseFunctions;

public class AuthIssueManager extends DBIssueManager implements AuthTokenUpdater {

	// private final AuthLevelManager manager;
	// private String token;

	public AuthIssueManager(IssueDatabaseFunctions database, AuthLevelManager manager) {
		super(database);
		this.manager = manager;
	}

	@Override
	public void assignIssue(Issue issue, SystemEngineer engineer) throws RuntimeException {
		super.assignIssue(issue, engineer);
	}

	@Override
	public String createIssue(IssueCategory category, String description, Developer createdBy) {
		return super.createIssue(category, description, createdBy);
	}

	@Override
	public Collection<Issue> getAllIssues() {
		return super.getAllIssues();
	}

	@Override
	public Collection<Issue> getAssignedIssues(SystemEngineer engineer) {
		return super.getAssignedIssues(engineer);
	}

	@Override
	public Collection<Issue> getDevCreatedIssues(Developer developer) {
		return super.getDevCreatedIssues(developer);
	}

	@Override
	public Issue getIssue(String issueID) {
		return super.getIssue(issueID);
	}

	@Override
	public void resolveIssue(Issue issue) throws RuntimeException {
		super.resolveIssue(issue);
	}

	@Override
	public void setAuthToken(String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAuthToken(String token) {
		// TODO Auto-generated method stub
		
	}

}
