package com.pitstop.Database.Models.Issues;

import java.util.Collection;
import java.util.Map;

import com.pitstop.Core.Models.Issues.Issue;

public interface IssueStorageManager {
	int getCurrentIssueID();

	Map<String, DBIssue> getIssues();

	void saveIssues(int currentIssueID, Collection<Issue> issues);
}
