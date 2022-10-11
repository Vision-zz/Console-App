package Middlewares.Issues;

import java.util.Collection;

import Modules.Issue;
import Users.SystemEngineer;

public interface EngineerIssueManager {
	void requestResolveIssue(Issue issue);
	Collection<Issue> requestAssignedIssues(SystemEngineer engineer);
}
