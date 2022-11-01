package com.pitstop.UserInterface.ConsoleFrontend;

import java.util.Collection;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.UserInterface.Helpers.Logger;
import com.pitstop.UserInterface.Helpers.Scanner;
import com.pitstop.UserInterface.Helpers.Table;

public class IssuePrinterUtil {
	
	public void printIssuesAsTable(Collection<Issue> issues) {
		if (issues.size() < 1) {
			Logger.logError("No Issues available");
			Scanner.getString("Press any key to continue");
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

}
