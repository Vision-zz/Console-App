package com.pitstop.StorageManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Users.DBEmployee;

public interface StorageDataManager {

	boolean validateData(StorageLoadTypes type);

	int getCurrentIssueID(StorageLoadTypes type);

	Map<String, DBIssue> getIssues(StorageLoadTypes type);

	void saveIssues(int currentIssueID, Collection<DBIssue> issues) throws IOException;

	int getCurrentEmployeeID(StorageLoadTypes type);

	Map<String, DBEmployee> getEmployees(StorageLoadTypes type);

	void saveEmployees(int currentEmployeeID, Collection<DBEmployee> employees) throws IOException;

}
