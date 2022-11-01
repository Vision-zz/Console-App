package com.pitstop.Core.Middleware.Issues;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Issues.IssueCategory;
import com.pitstop.Core.Models.Users.Developer;

public interface DevIssueManager {
	public String createIssue(IssueCategory category, String description, Developer createdBy);
	public Collection<Issue> getDevCreatedIssues(Developer developer);
}
