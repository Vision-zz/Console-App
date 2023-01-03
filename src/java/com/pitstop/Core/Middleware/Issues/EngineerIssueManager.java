package com.pitstop.Core.Middleware.Issues;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Users.SystemEngineer;

public interface EngineerIssueManager {
	void resolveIssue(Issue issue);

	Collection<Issue> getAssignedIssues(SystemEngineer engineer);
}
