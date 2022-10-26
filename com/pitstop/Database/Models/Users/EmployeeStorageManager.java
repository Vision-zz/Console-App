package com.pitstop.Database.Models.Users;

import java.util.Collection;

import com.pitstop.Core.Models.Users.Employee;

public interface EmployeeStorageManager {
	int getCurrentEmployeeID();
	Collection<Employee> getEmployees();
	void saveEmployees(int currentIssueID, Collection<Employee> employees);

}
