package Middlewares.Issues;

import Modules.Issue;
import Users.Developer;

public interface DevIssueManager {
	public void newIssueRequest(Issue issue);
	public Issue[] requestCreatedIssues(Developer developer);
}
