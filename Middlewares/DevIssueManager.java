package Middlewares.Issues;

import Modules.Issue;

public interface DevIssueManager {
	public void newIssueRequest(Issue issue);
	public Issue[] requestCreatedIssues();
}
