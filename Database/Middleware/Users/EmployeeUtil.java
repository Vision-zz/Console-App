package Database.Middleware.Users;

import Core.Models.Users.Developer;
import Core.Models.Users.Employee;
import Core.Models.Users.SystemAdmin;
import Core.Models.Users.SystemEngineer;
import Database.Middleware.Issues.DBIssueManager;
import Database.Models.Users.DBEmployee;

public class EmployeeUtil {
	public static final DBEmployee cloneToDBEmployee(Employee employee) {
		return new DBEmployee(employee.getUsername(), employee.getPassword(), employee.getEmployeeName(),
				employee.getEmployeeRole());
	}

	public static final Employee cloneToEmployee(DBEmployee sessionEmployee) {
		switch (sessionEmployee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				return new SystemAdmin(sessionEmployee.getUsername(), sessionEmployee.getPassword(),
						sessionEmployee.getEmployeeName(), DBIssueManager.getInstance(), DBEmployeeManager.getInstance());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(sessionEmployee.getUsername(), sessionEmployee.getPassword(),
						sessionEmployee.getEmployeeName(), DBIssueManager.getInstance());
			case DEVELOPER:
				return new Developer(sessionEmployee.getUsername(), sessionEmployee.getPassword(),
						sessionEmployee.getEmployeeName(), DBIssueManager.getInstance());
			default:
				throw new RuntimeException("Unknown employee role");
		}
	}

}
