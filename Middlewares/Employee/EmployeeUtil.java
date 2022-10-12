package Middlewares.Employee;

import Database.DBEmployee;
import Helpers.Logger;
import Users.Developer;
import Users.Employee;
import Users.SystemAdmin;
import Users.SystemEngineer;

public final class EmployeeUtil {
	public static DBEmployee cloneToDBEmployee(Employee employee) {
		return new DBEmployee(employee.getUsername(), employee.getPassword(), employee.getEmployeeName(),
				employee.getEmployeeRole());
	}

	public static Employee cloneToEmployee(DBEmployee employee) {
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
