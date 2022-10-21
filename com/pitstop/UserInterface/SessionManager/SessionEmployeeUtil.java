package pitstop.UserInterface.SessionManager;

import pitstop.Core.Models.Users.Developer;
import pitstop.Core.Models.Users.Employee;
import pitstop.Core.Models.Users.SystemAdmin;
import pitstop.Core.Models.Users.SystemEngineer;
import pitstop.Database.Middleware.Issues.DBIssueManager;
import pitstop.Database.Middleware.Users.DBEmployeeManager;
import pitstop.Database.Middleware.Users.EmployeeUtil;

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
						DBIssueManager.getInstance(),
						DBEmployeeManager.getInstance());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), employee.getEmployeeID(),
						DBIssueManager.getInstance());
			case DEVELOPER:
				return new Developer(employee.getUsername(), employee.getPassword(),
						employee.getEmployeeName(), employee.getEmployeeID(),
						DBIssueManager.getInstance());
			default:
				throw new RuntimeException("Unknown employee role");
		}
	}
}
