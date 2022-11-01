package com.pitstop.Database.Middleware.Utils;

import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.SystemAdmin;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Issues.DBIssueManager;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;
import com.pitstop.Database.Models.Users.DBEmployee;

public class EmployeeUtil {
	public static final DBEmployee cloneToDBEmployee(Employee employee) {
		return new DBEmployee(employee.getUsername(), employee.getPassword(), employee.getEmployeeName(),
				employee.getEmployeeID(), employee.getEmployeeRole());
	}

	public static final Employee cloneToEmployee(DBEmployee sessionEmployee) {
		switch (sessionEmployee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				return new SystemAdmin(sessionEmployee.getUsername(), sessionEmployee.getPassword(),
						sessionEmployee.getEmployeeName(), sessionEmployee.getEmployeeID(),
						DBIssueManager.getInstance(),
						DBEmployeeManager.getInstance());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(sessionEmployee.getUsername(), sessionEmployee.getPassword(),
						sessionEmployee.getEmployeeName(), sessionEmployee.getEmployeeID(),
						DBIssueManager.getInstance());
			case DEVELOPER:
				return new Developer(sessionEmployee.getUsername(), sessionEmployee.getPassword(),
						sessionEmployee.getEmployeeName(), sessionEmployee.getEmployeeID(),
						DBIssueManager.getInstance());
			default:
				throw new RuntimeException("Unknown employee role");
		}
	}

}