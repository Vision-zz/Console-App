package pitstop.Core.Middleware.Issues;

import java.util.Collection;

import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Users.Developer;

public interface DevIssueManager {
	public void newIssueRequest(Issue issue);
	public Collection<Issue> getDevCreatedIssues(Developer developer);
}
