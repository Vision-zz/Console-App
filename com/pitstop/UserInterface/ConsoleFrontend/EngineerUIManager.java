package pitstop.UserInterface.ConsoleFrontend;

import java.util.Collection;

import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Users.SystemEngineer;
import pitstop.UserInterface.Helpers.Logger;
import pitstop.UserInterface.Helpers.Scanner;

public class EngineerUIManager {

	private final SystemEngineer employee;

	public EngineerUIManager(SystemEngineer employee) {
		this.employee = employee;
	}

	public void mainMenu() {
		do {

			Logger.logInfo(
					"1. View pending Issues",
					"2. Resolve Issue",
					"3. Logout");

			String input = Scanner.getString();
			if (!input.matches("[1-3]")) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			switch (input) {
				case "1":
					viewAssignedIssues();
					break;

				case "2":
					resolveIssue();
					break;

				case "3":
					return;
			}

		} while (true);
	}

	private void viewAssignedIssues() {
		Collection<Issue> issues = employee.getAllAssignedIssues();
		new IssuePrinterUtil().printIssuesAsTable(issues);
	}

	private void resolveIssue() {

		Collection<Issue> issues = employee.getAllAssignedIssues();
		if (issues.size() < 1) {
			Logger.logError("No Issues pending");
			Scanner.getString("Press any key to continue");
			return;
		}

		String issueID = Scanner.getString("Enter Issue ID");
		Issue issue = null;

		for (Issue i : issues)
			if (i.issueID.equals(issueID))
				issue = i;

		if (issue == null) {
			Logger.logError("Issue with ID: " + issueID + " does not exist or is not assigned to you ");
			Scanner.getString("Press any key to continue to main menu");
			return;
		}

		do {

			String confirmID = Scanner.getString("Enter the Issue ID again to resolve the Issue");

			if (!confirmID.equals(issue.issueID)) {

				Logger.logWarning("Incorrect ID. Press 1 to try again or any key to exit");
				String input = Scanner.getString();
				if (!input.equals("1"))
					return;

				continue;
			}

			break;

		} while (true);

		employee.markIssueAsResolved(issue);
		Logger.logSuccess("Issue resolved. Issue ID: " + issue.issueID);
		Scanner.getString("Press any key to continue");
	}

}