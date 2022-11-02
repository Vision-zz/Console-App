package com.pitstop.Database.Middleware.Storage;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Users.DBEmployee;

public interface StorageParseable {

	boolean validateData();

	int getCurrentIssueID();

	Map<String, DBIssue> getIssues();

	void saveIssues(int currentIssueID, Collection<DBIssue> issues) throws IOException;

	int getCurrentEmployeeID();

	Map<String, DBEmployee> getEmployees();

	void saveEmployees(int currentEmployeeID, Collection<DBEmployee> employees) throws IOException;

}
