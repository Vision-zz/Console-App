package pitstop.Core.Middleware.Issues;

import java.util.Collection;

import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Users.SystemEngineer;

public interface AdminIssueManager {
	Collection<Issue> getAllIssues();
	Issue getIssue(String issueID);
	void assignIssue(Issue issue, SystemEngineer engineer);
}