package com.pitstop.Database.Models.Users;

import java.util.Collection;
import java.util.Map;

public interface EmployeeStorageManager {
	int getCurrentEmployeeID();

	Map<String, DBEmployee> getEmployees();

	void saveEmployees(int currentIssueID, Collection<DBEmployee> employees);

}
