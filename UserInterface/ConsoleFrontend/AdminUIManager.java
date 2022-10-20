package UserInterface.ConsoleFrontend;

import java.util.Collection;

import UserInterface.Helpers.Table;
import Core.Middleware.Issues.AdminIssueManager;
import Core.Middleware.Users.EmployeeDetailsManager;
import Core.Models.Issues.Issue;
import Core.Models.Users.Employee;
import Core.Models.Users.EmployeeRole;
import Core.Models.Users.SystemAdmin;
import Core.Models.Users.SystemEngineer;
import Database.Middleware.Issues.DBIssueManager;
import Database.Middleware.Users.DBEmployeeManager;
import UserInterface.Helpers.Logger;
import UserInterface.Helpers.Scanner;

public class AdminUIManager {

	private final SystemAdmin employee;

	public AdminUIManager(SystemAdmin employee) {
		this.employee = employee;
	}

	public void mainMenu() {
		do {

			Logger.logInfo(
					"1. View all Issues",
					"2. View unassigned Issues",
					"3. View all Engineers",
					"4. Assign Issue to Engineer",
					"5. Logout");

			String input = Scanner.getString();
			if (!input.matches("[1-5]")) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			switch (input) {
				case "1":
					viewIssues(IssueViewType.ALL);
					break;

				case "2":
					viewIssues(IssueViewType.UNASSIGNED);
					break;

				case "3":
					viewEngineers();
					break;

				case "4":
					assignIssue();
					break;

				case "5":
					return;

			}

		} while (true);
	}

	private void viewIssues(IssueViewType type) {
		Collection<Issue> issues = type.equals(IssueViewType.UNASSIGNED) ? this.employee.getUnassignedIssues()
				: this.employee.getAllIssues();
		if (issues.size() < 1) {
			Logger.logError("No Issues available");
			return;
		}

		Table table = new Table("Index", "Issue ID", "Category", "Status", "Description").withUnicode(true);

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

			if (!input.matches("[0-9]+") || Integer.parseInt(input) > issues.size() || Integer.parseInt(input) < 0) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			if (input.equals("0"))
				return;

			System.out.println(issues.toArray(new Issue[issues.size()])[Integer.parseInt(input) - 1].toString());

		} while (true);
	}

	private void viewEngineers() {
		Collection<SystemEngineer> engineers = this.employee.getAllEngineers();

		if (engineers.size() < 1) {
			Logger.logError("No Engineers available");
			return;
		}

		Table table = new Table(2, "Employee ID", "Name", "Issues assigned").withUnicode(true);

		for (SystemEngineer e : engineers) {
			table.addRow(e.getEmployeeID(), e.getEmployeeName(), e.getAllAssignedIssues().size() + "");
		}

		table.print();

		Scanner.getString("Press any key to continue");
	}

	private void assignIssue() {

		Issue issue;
		Employee employee;

		do {

			String issueID = Scanner.getString("Enter the Issue ID");
			AdminIssueManager issueManager = DBIssueManager.getInstance();
			issue = issueManager.getIssue(issueID);

			if (issue == null) {
				Logger.logError("Issue with ID: " + issueID + " does not exist");
				String input = Scanner.getString("Press 1 to retry or any key to return to main menu");
				if (input.equals("1"))
					continue;

				return;
			}

			break;

		} while (true);

		do {

			String employeeID = Scanner.getString("Enter the employee ID of the engineer");
			EmployeeDetailsManager detailsManager = DBEmployeeManager.getInstance();
			employee = detailsManager.getEmployeeByID(employeeID);

			if (employee == null) {
				Logger.logError("Employee with ID: " + employeeID + " do not exist");
				String input = Scanner.getString("Press 1 to retry or any key to return to main menu");
				if (input.equals("1"))
					continue;
				return;
			}

			if (!employee.getEmployeeRole().equals(EmployeeRole.SYSTEM_ENGINEER)) {
				Logger.logError("Employee is not a Engineer. Issues can only be assigned to a Engineer");
				String input = Scanner.getString("Press 1 to retry or any key to return to main menu");
				if (input.equals("1"))
					continue;
				return;
			}

			break;

		} while (true);

		this.employee.assignIssueToEngineer(issue, (SystemEngineer) employee);
		Logger.logSuccess(
				"Assigned Issue with ID: " + issue.issueID + " to Engineer " + employee.getEmployeeName());

		Scanner.getString("Press any key to continue");
		return;
	}

}

enum IssueViewType {
	UNASSIGNED,
	ALL;
}
