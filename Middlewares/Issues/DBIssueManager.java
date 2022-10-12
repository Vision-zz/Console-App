package Middlewares.Issues;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.function.Predicate;

import Database.DBIssue;
import Database.IssuesDatabase;
import SessionManager.Session;
import Helpers.Logger;
import Models.Issue;
import Models.IssueStatus;
import Users.Developer;
import Users.SystemAdmin;
import Users.SystemEngineer;

public class DBIssueManager implements IssueManagerProvider, DevIssueManager, EngineerIssueManager, AdminIssueManager {

	private static DBIssueManager instance = null;

	public static DBIssueManager getInstance() {
		if (instance == null)
			instance = new DBIssueManager();
		return instance;
	}

	private DBIssueManager() {
	}

	@Override
	public DevIssueManager getDevIssueManager() {
		return DBIssueManager.getInstance();
	}

	@Override
	public EngineerIssueManager getEngineerIssueManager() {
		return DBIssueManager.getInstance();
	}

	@Override
	public AdminIssueManager getAdminIssueManager() {
		return DBIssueManager.getInstance();
	}

	// Developer Issue Management methods
	@Override
	public void newIssueRequest(Issue issue) {
		DBIssue dbIssue = IssueUtil.cloneToDBIssue(issue);
		IssuesDatabase.getInstance().add(dbIssue);
		Logger.logSuccess("New issue with ID: " + dbIssue.issueID
				+ " has been submitted and will be reviewed soon by the Engineers");
	}

	@Override
	public Collection<Issue> getCreatedIssues(Developer developer) {

		if (!(Session.getInstance().getLoggedInAs() instanceof Developer)) {
			Logger.logWarning("Invalid login. Login as a Developer to view issues.");
			return null;
		}
		Developer dev = (Developer) Session.getInstance().getLoggedInAs();
		if (!dev.getUsername().equals(developer.getUsername())) {
			Logger.logWarning("Invalid request. Cannot view other employee's Issues");
			return null;
		}

		Collection<DBIssue> dbIssue = IssuesDatabase.getInstance().getAll().values();

		Predicate<DBIssue> predicate = issue -> issue.getCreatedBy().getUsername().equals(developer.getUsername());
		dbIssue.removeIf(predicate);

		Collection<Issue> issueCollection = new HashSet<Issue>();
		dbIssue.forEach((issue) -> {
			issueCollection.add(IssueUtil.cloneToIssue(issue));
		});

		return issueCollection;

	}

	// System Engineer Issue Management methods
	@Override
	public void resolveIssue(Issue issue) {
		DBIssue dbIssue = IssuesDatabase.getInstance().get(issue.issueID);
		if (dbIssue == null) {
			Logger.logError("Issue with ID: " + issue.issueID + " could not be found in the database");
			return;
		}

		Issue updatedIssue = IssueUtil.cloneToIssue(dbIssue);
		updatedIssue.updateStatus(IssueStatus.RESOLVED);
		updatedIssue.setResolvedAt(new Date());

		IssuesDatabase.getInstance().udpate(IssueUtil.cloneToDBIssue(updatedIssue));

		Logger.logSuccess("Issue with ID: " + issue.issueID + " has been marked as Resolved");
	}

	@Override
	public Collection<Issue> getAssignedIssues(SystemEngineer engineer) {
		if (!(Session.getInstance().getLoggedInAs() instanceof SystemEngineer)) {
			Logger.logWarning("Invalid login. Login as a System Engineer to view issues.");
			return null;
		}
		SystemEngineer eng = (SystemEngineer) Session.getInstance().getLoggedInAs();
		if (!eng.getUsername().equals(engineer.getUsername())) {
			Logger.logWarning("Invalid request. Cannot view other Engineer's Issues");
			return null;
		}

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
	public Collection<Issue> getAllIssues() {
		if (!(Session.getInstance().getLoggedInAs() instanceof SystemAdmin)) {
			Logger.logWarning("You do not have access to do this operation.");
			return null;
		}
		Collection<DBIssue> dbIssues = IssuesDatabase.getInstance().getAll().values();

		Collection<Issue> allIssues = new HashSet<>();
		dbIssues.forEach(issue -> {
			allIssues.add(IssueUtil.cloneToIssue(issue));
		});

		return allIssues;
	}

	@Override
	public void assignIssue(Issue issue, SystemEngineer engineer) {
		DBIssue dbIssue = IssuesDatabase.getInstance().get(issue.issueID);
		if (dbIssue == null) {
			Logger.logError("Issue with ID: " + issue.issueID + " does not exist. Cannot assign Engineer to Issue.");
			return;
		}

		Issue updatedIssue = IssueUtil.cloneToIssue(dbIssue);
		updatedIssue.assignEngineer(engineer);

		IssuesDatabase.getInstance().udpate(IssueUtil.cloneToDBIssue(updatedIssue));

	}
}
