package Core.Middleware.Issues;

import java.util.Collection;

import Core.Models.Issues.Issue;
import Core.Models.Users.SystemEngineer;

public interface AdminIssueManager {
	Collection<Issue> getAllIssues();
	Issue getIssue(String issueID);
	void assignIssue(Issue issue, SystemEngineer engineer);
}