package Middlewares.Employee;

import Users.EmployeeRole;

public interface EmployeeSignupManager {

	void signUp(String username, String password, String employeeName, EmployeeRole employeeRole);

}
