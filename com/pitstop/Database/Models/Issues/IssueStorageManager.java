package com.pitstop.Database.Models.Issues;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;

public interface IssueStorageManager {
	int getCurrentIssueID();
	Collection<Issue> getIssues();
	void saveIssues(int currentIssueID, Collection<Issue> issues);
}
