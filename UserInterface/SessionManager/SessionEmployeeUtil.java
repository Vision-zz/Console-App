package UserInterface.SessionManager;

import Core.Models.Users.Developer;
import Core.Models.Users.Employee;
import Core.Models.Users.SystemAdmin;
import Core.Models.Users.SystemEngineer;
import Database.Middleware.Issues.DBIssueManager;
import Database.Middleware.Users.DBEmployeeManager;
import Database.Middleware.Users.EmployeeUtil;

public class SessionEmployeeUtil extends EmployeeUtil {

	public static final SessionEmployee cloneToSessionEmployee(Employee employee) {
		return new SessionEmployee(employee.getUsername(), employee.getPassword(),
				employee.getEmployeeName(), employee.getEmployeeRole());
	}

	public static final Employee cloneToEmployee(SessionEmployee employee) {
		switch (employee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				return new SystemAdmin(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), DBIssueManager.getInstance(), DBEmployeeManager.getInstance());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), DBIssueManager.getInstance());
			case DEVELOPER:
				return new Developer(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), DBIssueManager.getInstance());
			default:
				throw new RuntimeException("Unknown employee role");
		}
	}
}
