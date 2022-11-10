package com.pitstop.Session;

import com.pitstop.ManagerProvider;
import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.SystemAdmin;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Utils.EmployeeUtil;

public class SessionEmployeeUtil extends EmployeeUtil {

	public static final SessionEmployee cloneToSessionEmployee(Employee employee) {
		return new SessionEmployee(employee.getUsername(), employee.getPassword(),
				employee.getEmployeeName(), employee.getEmployeeID(), employee.getEmployeeRole());
	}

	public static final Employee cloneToEmployee(SessionEmployee employee) {
		switch (employee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				return new SystemAdmin(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), employee.getEmployeeID(),
						ManagerProvider.getAdminIssueManager(),
						ManagerProvider.getEmployeeDetailsManager());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), employee.getEmployeeID(),
						ManagerProvider.getEngineerIssueManager());
			case DEVELOPER:
				return new Developer(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), employee.getEmployeeID(),
						ManagerProvider.getDevIssueManager());
			default:
				throw new RuntimeException("Unknown employee role");
		}
	}
}
