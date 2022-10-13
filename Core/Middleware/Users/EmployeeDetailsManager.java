package Core.Middleware.Users;

import java.util.Collection;

import Core.Models.Users.Employee;

public interface EmployeeDetailsManager {
	Collection<Employee> getAllEmployees();
	Employee getEmployee(String username);
}
