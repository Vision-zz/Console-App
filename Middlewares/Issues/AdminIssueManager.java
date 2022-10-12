package Middlewares.Issues;

import java.util.Collection;

import Models.Issue;
import Users.SystemEngineer;

public interface AdminIssueManager {
	Collection<Issue> requestAllIssues();
	void requestAssignIssue(Issue issue, SystemEngineer engineer);
}