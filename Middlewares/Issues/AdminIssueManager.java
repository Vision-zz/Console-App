package Middlewares.Issues;

import java.util.Collection;

import Modules.Issue;
import Users.SystemEngineer;

public interface AdminIssueManager {
	Collection<Issue> requestAllIssues();
	void requestAssignIssue(Issue issue, SystemEngineer engineer);
}
