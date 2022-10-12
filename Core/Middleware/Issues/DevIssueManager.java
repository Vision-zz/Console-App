package Core.Middleware.Issues;

import java.util.Collection;

import Core.Models.Issues.Issue;
import Core.Models.Users.Developer;

public interface DevIssueManager {
	public void newIssueRequest(Issue issue);
	public Collection<Issue> getCreatedIssues(Developer developer);
}
