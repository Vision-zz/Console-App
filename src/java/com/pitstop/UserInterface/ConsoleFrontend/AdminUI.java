package com.pitstop.UserInterface.ConsoleFrontend;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Users.SystemAdmin;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.UserInterface.Helpers.Logger;
import com.pitstop.UserInterface.Helpers.Scanner;
import com.pitstop.UserInterface.Helpers.Table;

public class AdminUI {

	private final SystemAdmin admin;

	public AdminUI(SystemAdmin employee) {
		this.admin = employee;
	}

	public void showUserInterface() {
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
		Collection<Issue> issues = type.equals(IssueViewType.UNASSIGNED) ? this.admin.getUnassignedIssues()
				: this.admin.getAllIssues();
		new IssuePrinterUtil().printIssuesAsTable(issues);
	}

	private void viewEngineers() {
		Collection<SystemEngineer> engineers = this.admin.getAllEngineers();

		if (engineers.size() < 1) {
			Logger.logError("No Engineers available");
			return;
		}

		Table table = new Table("Employee ID", "Name", "Issues assigned").withUnicode(true);

		for (SystemEngineer e : engineers) {
			table.addRow(e.getEmployeeID(), e.getEmployeeName(), e.getAllAssignedIssues().size() + "");
		}

		table.print();

		Scanner.getString("Press any key to continue");
	}

	private void assignIssue() {

		Issue issue = null;
		SystemEngineer engineer = null;

		Collection<Issue> unassignedIssues = this.admin.getUnassignedIssues();
		Collection<SystemEngineer> engineers = this.admin.getAllEngineers();

		if (unassignedIssues.size() < 1) {
			Logger.logWarning("No unassigned issues available");
			Scanner.getString("Press any key to continue");
			return;
		}

		if (engineers.size() < 1) {
			Logger.logWarning("No Engineers available");
			Scanner.getString("Press any key to continue");
			return;
		}

		do {

			String issueID = Scanner
					.getString("Enter the Issue ID. Press 0 to view all unassigned issues or 1 to exit");
			if (issueID.equals("0")) {
				viewIssues(IssueViewType.UNASSIGNED);
				continue;
			} else if (issueID.equals("1")) {
				return;
			}

			for (Issue i : unassignedIssues) {
				if (i.issueID.equals(issueID)) {
					issue = i;
					break;
				}
			}

			if (issue == null) {
				Logger.logError("Issue with ID: " + issueID + " does not exist");
				String input = Scanner.getString("Press 1 to retry or any key to return to main menu");
				if (input.equals("1"))
					continue;

				return;
			}

			if (issue.getAssignedEngineer() != null) {
				Logger.logError("Issue is already assigned to " + issue.getAssignedEngineer().getEmployeeName()
						+ " EID: " + issue.getAssignedEngineer().getEmployeeID());
				String input = Scanner.getString("Press 1 to retry or any key to return to main menu");
				if (input.equals("1"))
					continue;

				return;
			}

			break;

		} while (true);

		do {

			String employeeID = Scanner
					.getString("Enter the employee ID of the engineer. Press 0 to view all engineers or 1 to exit");
			if (employeeID.equals("0")) {
				this.viewEngineers();
				continue;
			} else if (employeeID.equals("1")) {
				return;
			}

			for (SystemEngineer e : engineers) {
				if (e.getEmployeeID().equals(employeeID)) {
					engineer = e;
					break;
				}
			}

			if (engineer == null) {
				Logger.logError("Employee with ID: " + employeeID + " do not exist or is not an Engineer.");
				String input = Scanner.getString("Press 1 to retry or any key to return to main menu");
				if (input.equals("1"))
					continue;
				return;
			}

			break;

		} while (true);

		this.admin.assignIssueToEngineer(issue, engineer);
		Logger.logSuccess(
				"Assigned Issue with ID: " + issue.issueID + " to Engineer " + engineer.getEmployeeName());

		Scanner.getString("Press any key to continue");
		return;
	}

}

enum IssueViewType {
	UNASSIGNED,
	ALL;
}
