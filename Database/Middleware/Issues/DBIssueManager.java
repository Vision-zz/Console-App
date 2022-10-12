package Database.Middleware.Issues;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.function.Predicate;

import Core.Middleware.Issues.AdminIssueManager;
import Core.Middleware.Issues.DevIssueManager;
import Core.Middleware.Issues.EngineerIssueManager;
import Core.Models.Issues.Issue;
import Core.Models.Issues.IssueStatus;
import Core.Models.Users.Developer;
import Core.Models.Users.SystemEngineer;
import Database.Models.Issues.DBIssue;
import Database.Models.Issues.IssuesDatabase;

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
	public Collection<Issue> getDevCreatedIssues(Developer developer) {

		Collection<DBIssue> dbIssue = IssuesDatabase.getInstance().getAll().values();

		Predicate<DBIssue> predicate = issue -> issue.getCreatedBy().getUsername().equals(developer.getUsername());
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

		Predicate<DBIssue> predicate = issue -> issue.getAssignedTo().getUsername().equals(engineer.getUsername());
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
