package Middlewares.Issues;

import java.util.Collection;

import Models.Issue;
import Users.SystemEngineer;

public interface AdminIssueManager {
	Collection<Issue> getAllIssues();
	void assignIssue(Issue issue, SystemEngineer engineer);
}