package Middlewares.Employee;

import Database.DBEmployee;
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
			case "System Admin":
				return new SystemAdmin(employee.getUsername(), employee.getPassword(), employee.getEmployeeName());
			case "System Engineer":
				return new SystemEngineer(employee.getUsername(), employee.getPassword(), employee.getEmployeeName());
			default:
				return new Developer(employee.getUsername(), employee.getPassword(), employee.getEmployeeName());
		}
	}
}
