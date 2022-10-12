package Middlewares.Employee;

import Database.DBEmployee;
import Helpers.Logger;
import SessionManager.SessionEmployee;
import Users.Developer;
import Users.Employee;
import Users.SystemAdmin;
import Users.SystemEngineer;

public final class EmployeeUtil {
	public static DBEmployee cloneToDBEmployee(Employee employee) {
		return new DBEmployee(employee.getUsername(), employee.getPassword(), employee.getEmployeeName(),
				employee.getEmployeeRole());
	}

	public static SessionEmployee cloneToSessionEmployee(Employee employee) {
		return new SessionEmployee(employee.getUsername(), employee.getPassword(),
				employee.getEmployeeName(), employee.getEmployeeRole());
	}

	public static Employee cloneToEmployee(DBEmployee sessionEmployee) {
		switch (sessionEmployee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				return new SystemAdmin(sessionEmployee.getUsername(), sessionEmployee.getPassword(), sessionEmployee.getEmployeeName());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(sessionEmployee.getUsername(), sessionEmployee.getPassword(), sessionEmployee.getEmployeeName());
			case DEVELOPER:
				return new Developer(sessionEmployee.getUsername(), sessionEmployee.getPassword(), sessionEmployee.getEmployeeName());
			default:
				Logger.logError("Unknown employee role");
				return null;
		}
	}

	public static Employee cloneToEmployee(SessionEmployee employee) {
		switch (employee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				return new SystemAdmin(employee.getUsername(), employee.getPassword(), employee.getEmployeeName());
			case SYSTEM_ENGINEER:
				return new SystemEngineer(employee.getUsername(), employee.getPassword(), employee.getEmployeeName());
			case DEVELOPER:
				return new Developer(employee.getUsername(), employee.getPassword(), employee.getEmployeeName());
			default:
				Logger.logError("Unknown employee role");
				return null;
		}
	}
	
}
