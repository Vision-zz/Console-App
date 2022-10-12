package Database.Middleware.Users;

import Core.Models.Users.EmployeeRole;

public interface EmployeeSignupManager {

	SignUpStatus signUp(String username, String password, String employeeName, EmployeeRole employeeRole);

}
