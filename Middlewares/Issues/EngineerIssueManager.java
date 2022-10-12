package Middlewares.Issues;

import java.util.Collection;

import Models.Issue;
import Users.SystemEngineer;

public interface EngineerIssueManager {
	void resolveIssue(Issue issue);
	Collection<Issue> getAssignedIssues(SystemEngineer engineer);
}
