package pitstop.Core.Middleware.Issues;

import java.util.Collection;

import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Users.SystemEngineer;

public interface EngineerIssueManager {
	void resolveIssue(Issue issue);
	Collection<Issue> getAssignedIssues(SystemEngineer engineer);
}
