package Core.Middleware.Users;

import java.util.Collection;

import Core.Models.Users.Employee;

public interface AdminEmployeeManager {
	Collection<Employee> getAllEmployees();
}
