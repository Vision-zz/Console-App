package com.pitstop.Database.Middleware.Utils;

import com.pitstop.Core.Models.Issues.IssueCategory;
import com.pitstop.Core.Models.Users.EmployeeRole;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public final class IDGenerator {

	public static String generateEmployeeID(EmployeeRole employeeRole) {
		EmployeeDatabase db = EmployeeDatabase.getInstance();
		int currentID = db.getCurrentID();
		String ID = employeeRole.toString() + "_" + currentID;
		db.updateCurrentID(currentID + 1);
		return ID;
	}

	public static String generateIssueID(IssueCategory category) {
		IssuesDatabase db = IssuesDatabase.getInstance();
		int currentID = db.getCurrentID();
		String ID = category.toString() + "_" + currentID;
		db.updateCurrentID(currentID + 1);
		return ID;
	}

}
