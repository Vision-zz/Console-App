package UserInterface.ConsoleFrontend;

import java.util.Collection;

import Core.Models.Issues.Issue;
import Core.Models.Users.SystemEngineer;
import UserInterface.Helpers.Logger;
import UserInterface.Helpers.Scanner;
import UserInterface.Helpers.Table;

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
					viewIssues();
					break;

				case "2":
					resolveIssue();
					break;

				case "3":
					return;
			}

		} while (true);
	}

	private void viewIssues() {
		Collection<Issue> issues = employee.getAllAssignedIssues();
		if (issues.size() < 1) {
			Logger.logError("No Issues pending");
			return;
		}

		Table table = new Table( "Index", "Issue ID", "Category", "Status", "Description").withUnicode(true);

		int index = 1;
		for (Issue i : issues) {
			String description = i.getDescription().length() > 40
					? i.getDescription().subSequence(0, 41).toString() + "..."
					: i.getDescription();
			table.addRow("" + index, i.issueID, i.getCategory().toString(), i.getStatus().toString(), description);
			index++;
		}

		table.print();

		do {

			String input = Scanner
					.getString("Enter the index of the Issue to view more info or enter 0 to return to main menu");

			if (!input.matches("[0-9]") || Integer.parseInt(input) > issues.size() - 1 || Integer.parseInt(input) < 0) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			if (input.equals("0"))
				return;

			Issue issue = issues.toArray(new Issue[issues.size()])[Integer.parseInt(input) - 1];

			System.out.println(issue.toString());

		} while (true);
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
			Logger.logError("You do not have an issue with ID: " + issueID + " assigned to you");
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
	}

}
