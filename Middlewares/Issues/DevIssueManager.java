package Middlewares.Issues;

import java.util.Collection;

import Models.Issue;
import Users.Developer;

public interface DevIssueManager {
	public void newIssueRequest(Issue issue);
	public Collection<Issue> requestCreatedIssues(Developer developer);
}
