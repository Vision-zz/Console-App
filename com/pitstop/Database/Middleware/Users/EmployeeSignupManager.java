package com.pitstop.Database.Middleware.Users;

import com.pitstop.Core.Models.Users.EmployeeRole;

public interface EmployeeSignupManager {

	SignUpStatus signUp(String username, String password, String employeeName, EmployeeRole employeeRole);

}
