package Middlewares.Issues;

import Modules.Issue;
import Users.SystemEngineer;

public interface EngineerIssueManager {
	void requestResolveIssue(Issue issue);
	Issue[] requestAssignedIssues(SystemEngineer engineer);
}
