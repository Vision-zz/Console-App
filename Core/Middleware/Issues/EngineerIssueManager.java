package Core.Middleware.Issues;

import java.util.Collection;

import Core.Models.Issues.Issue;
import Core.Models.Users.SystemEngineer;

public interface EngineerIssueManager {
	void resolveIssue(Issue issue);
	Collection<Issue> getAssignedIssues(SystemEngineer engineer);
}
