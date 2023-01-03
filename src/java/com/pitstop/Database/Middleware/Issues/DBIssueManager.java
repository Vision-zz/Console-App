package com.pitstop.Database.Middleware.Issues;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Issues.IssueCategory;
import com.pitstop.Core.Models.Issues.IssueStatus;
import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Utils.IDGenerator;
import com.pitstop.Database.Middleware.Utils.IssueUtil;
import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Issues.IssueDatabaseFunctions;

public class DBIssueManager implements DevIssueManager, EngineerIssueManager, AdminIssueManager {

	private final IssueDatabaseFunctions database;

	public DBIssueManager(IssueDatabaseFunctions database) {
		this.database = database;
	}

	@Override
	public String createIssue(IssueCategory category, String description, Developer createdBy) {
		Issue issue = new Issue(IDGenerator.generateIssueID(category), category, description, createdBy);
		DBIssue dbIssue = IssueUtil.cloneToDBIssue(issue);
		database.add(dbIssue);
		return dbIssue.issueID;
	}

	@Override
	public Collection<Issue> getAllIssues() {

		Collection<DBIssue> dbIssues = database.getAll().values();

		Collection<Issue> allIssues = new HashSet<>();
		dbIssues.forEach(issue -> {
			allIssues.add(IssueUtil.cloneToIssue(issue));
		});

		return allIssues;
	}

	@Override
	public Issue getIssue(String issueID) {
		HashMap<String, DBIssue> dbIssues = database.getAll();
		DBIssue dbIssue = dbIssues.get(issueID);
		if (dbIssue == null)
			return null;
		return IssueUtil.cloneToIssue(dbIssue);
	}

	@Override
	public Collection<Issue> getDevCreatedIssues(Developer developer) {

		Collection<DBIssue> dbIssue = database.getAll().values();

		Predicate<DBIssue> predicate = issue -> !issue.getCreatedBy().getUsername().equals(developer.getUsername());
		dbIssue.removeIf(predicate);

		Collection<Issue> issueCollection = new HashSet<Issue>();
		dbIssue.forEach((issue) -> {
			issueCollection.add(IssueUtil.cloneToIssue(issue));
		});

		return issueCollection;

	}

	@Override
	public Collection<Issue> getAssignedIssues(SystemEngineer engineer) {

		Collection<DBIssue> dbIssues = database.getAll().values();

		Predicate<DBIssue> predicate = issue -> (issue.getAssignedTo() == null
				|| !issue.getAssignedTo().getUsername().equals(engineer.getUsername())
				|| issue.getStatus().equals(IssueStatus.RESOLVED));
		dbIssues.removeIf(predicate);

		Collection<Issue> issueCollection = new HashSet<Issue>();
		dbIssues.forEach((issue) -> {
			issueCollection.add(IssueUtil.cloneToIssue(issue));
		});

		return issueCollection;
	}

	@Override
	public void assignIssue(Issue issue, SystemEngineer engineer) throws RuntimeException {
		DBIssue dbIssue = database.get(issue.issueID);
		if (dbIssue == null) {
			throw new RuntimeException(
					"Unknown Issue. Issue ID: " + issue.issueID + " is invalid or the Issue does not exist");
		}
		Issue updatedIssue = IssueUtil.cloneToIssue(dbIssue);
		updatedIssue.assignEngineer(engineer);
		updatedIssue.updateStatus(IssueStatus.IN_PROGRESS);

		database.udpate(IssueUtil.cloneToDBIssue(updatedIssue));
		return;
	}

	@Override
	public void resolveIssue(Issue issue) throws RuntimeException {
		DBIssue dbIssue = database.get(issue.issueID);
		if (dbIssue == null) {
			throw new RuntimeException(
					"Unknown Issue. Issue ID: " + issue.issueID + " is invalid or the Issue does not exist");
		}

		Issue updatedIssue = IssueUtil.cloneToIssue(dbIssue);
		updatedIssue.updateStatus(IssueStatus.RESOLVED);
		updatedIssue.setResolvedAt(new Date());

		database.udpate(IssueUtil.cloneToDBIssue(updatedIssue));
		return;
	}

}
