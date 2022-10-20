package UserInterface.ConsoleFrontend;

import java.util.Collection;

import Core.Models.Issues.Issue;
import Core.Models.Issues.IssueCategory;
import Core.Models.Users.Developer;
import UserInterface.Helpers.Logger;
import UserInterface.Helpers.Scanner;
import UserInterface.Helpers.Table;

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
		if (issues.size() < 1) {
			Logger.logError("No Issues available");
			Scanner.getString("Press any key to continue");
			return;
		}

		Table table = new Table("Index", "Issue ID", "Category", "Status", "Description").withUnicode(true);

		int index = 1;
		for (Issue i : issues) {
			String description = i.getDescription().length() > 40 ? i.getDescription().subSequence(0, 41).toString() + "..." : i.getDescription();
			table.addRow("" + index,i.issueID, i.getCategory().toString(), i.getStatus().toString(), description);
			index++;
		}

		table.print();

		do {

			String input = Scanner
					.getString("Enter the index of the Issue to view more info or enter 0 to return to main menu");

			if (!input.matches("[0-9]+") || Integer.parseInt(input) > issues.size() || Integer.parseInt(input) < 0) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			if (input.equals("0"))
				return;

			System.out.println(issues.toArray(new Issue[issues.size()])[Integer.parseInt(input) - 1].toString());

		} while (true);

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
