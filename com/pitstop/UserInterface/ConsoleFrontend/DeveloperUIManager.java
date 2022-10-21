package pitstop.UserInterface.ConsoleFrontend;

import java.util.Collection;

import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Issues.IssueCategory;
import pitstop.Core.Models.Users.Developer;
import pitstop.UserInterface.Helpers.Logger;
import pitstop.UserInterface.Helpers.Scanner;

public class DeveloperUIManager {

	private final Developer employee;

	public DeveloperUIManager(Developer employee) {
		this.employee = employee;
	}

	public void mainMenu() {
		do {

			Logger.logInfo(
					"1. View Issues",
					"2. Create new Issue",
					"3. Logout");

			String input = Scanner.getString();
			if (!input.matches("[1-3]")) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			switch (input) {
				case "1":
					viewIssues();
					break;

				case "2":
					createNewIssue();
					break;

				case "3":

					return;
			}

		} while (true);
	}

	private void viewIssues() {
		Collection<Issue> issues = employee.getAllCreatedIssues();
		new IssuePrinterUtil().printIssuesAsTable(issues);
	}

	private void createNewIssue() {

		IssueCategory issueCategory;
		do {
			Logger.logInfo("Seelct the issue category", "1. Hardware | 2. Software | 0. Cancel");
			String input = Scanner.getString();
			if (!input.matches("[120]")) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			if (input.equals("0")) {
				Logger.logSuccess("Cancelled the proces");
				Scanner.getString("Press any key to continue");
				return;
			}

			issueCategory = input.equals("1") ? IssueCategory.HARDWARE : IssueCategory.SOFTWARE;
			break;

		} while (true);

		String description = Scanner.getString("Describe your issue briefly. Or press 0 to cancel");
		if (description.equals("0")) {
			Logger.logSuccess("Cancelled the process");
			Scanner.getString("Press any key to continue");
			return;
		}

		String createdIssueID = employee.createIssue(issueCategory, description);
		Logger.logSuccess(
				"Created a new Issue. Issue ID: " + createdIssueID + " Category: " + issueCategory.toString());
		Scanner.getString("Press any key to continue");
		return;

	}

}
