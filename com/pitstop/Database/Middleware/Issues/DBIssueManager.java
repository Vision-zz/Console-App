package pitstop.Database.Middleware.Issues;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;

import pitstop.Core.Middleware.Issues.AdminIssueManager;
import pitstop.Core.Middleware.Issues.DevIssueManager;
import pitstop.Core.Middleware.Issues.EngineerIssueManager;
import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Issues.IssueStatus;
import pitstop.Core.Models.Users.Developer;
import pitstop.Core.Models.Users.SystemEngineer;
import pitstop.Database.Models.Issues.DBIssue;
import pitstop.Database.Models.Issues.IssuesDatabase;

public class DBIssueManager implements DevIssueManager, EngineerIssueManager, AdminIssueManager {

	private static DBIssueManager instance = null;

	public static DBIssueManager getInstance() {
		if (instance == null)
			instance = new DBIssueManager();
		return instance;
	}

	private DBIssueManager() {
	}

	@Override
	public void newIssueRequest(Issue issue) {
		DBIssue dbIssue = IssueUtil.cloneToDBIssue(issue);
		IssuesDatabase.getInstance().add(dbIssue);
	}

	@Override
	public Collection<Issue> getAllIssues() {

		Collection<DBIssue> dbIssues = IssuesDatabase.getInstance().getAll().values();

		Collection<Issue> allIssues = new HashSet<>();
		dbIssues.forEach(issue -> {
			allIssues.add(IssueUtil.cloneToIssue(issue));
		});

		return allIssues;
	}

	@Override
	public Issue getIssue(String issueID) {
		HashMap<String, DBIssue> dbIssues = IssuesDatabase.getInstance().getAll();
		DBIssue dbIssue = dbIssues.get(issueID);
		if (dbIssue == null)
			return null;
		return IssueUtil.cloneToIssue(dbIssue);
	}

	@Override
	public Collection<Issue> getDevCreatedIssues(Developer developer) {

		Collection<DBIssue> dbIssue = IssuesDatabase.getInstance().getAll().values();

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

		Collection<DBIssue> dbIssues = IssuesDatabase.getInstance().getAll().values();

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
		DBIssue dbIssue = IssuesDatabase.getInstance().get(issue.issueID);
		if (dbIssue == null) {
			throw new RuntimeException(
					"Unknown Issue. Issue ID: " + issue.issueID + " is invalid or the Issue does not exist");
		}
		Issue updatedIssue = IssueUtil.cloneToIssue(dbIssue);
		updatedIssue.assignEngineer(engineer);
		updatedIssue.updateStatus(IssueStatus.IN_PROGRESS);

		IssuesDatabase.getInstance().udpate(IssueUtil.cloneToDBIssue(updatedIssue));
		return;
	}

	@Override
	public void resolveIssue(Issue issue) throws RuntimeException {
		DBIssue dbIssue = IssuesDatabase.getInstance().get(issue.issueID);
		if (dbIssue == null) {
			throw new RuntimeException(
					"Unknown Issue. Issue ID: " + issue.issueID + " is invalid or the Issue does not exist");
		}

		Issue updatedIssue = IssueUtil.cloneToIssue(dbIssue);
		updatedIssue.updateStatus(IssueStatus.RESOLVED);
		updatedIssue.setResolvedAt(new Date());

		IssuesDatabase.getInstance().udpate(IssueUtil.cloneToDBIssue(updatedIssue));
		return;
	}

}
