package com.pitstop.Core.Middleware.Users;

import java.util.Collection;

import com.pitstop.Core.Models.Users.Employee;

public interface EmployeeDetailsManager {
	Collection<Employee> getAllEmployees();
	Employee getEmployee(String username);
	Employee getEmployeeByID(String employeeID);
}
