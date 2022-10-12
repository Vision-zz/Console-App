package Middlewares.Issues;

import java.util.Collection;

import Models.Issue;
import Users.SystemEngineer;

public interface EngineerIssueManager {
	void requestResolveIssue(Issue issue);
	Collection<Issue> requestAssignedIssues(SystemEngineer engineer);
}
