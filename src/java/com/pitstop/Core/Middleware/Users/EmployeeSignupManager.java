package com.pitstop.Core.Middleware.Users;

import com.pitstop.Core.Models.Users.EmployeeRole;

public interface EmployeeSignupManager {

	public enum SignUpStatus {
		SUCCESS,
		USERNAME_UNAVAILABLE;
	}

	SignUpStatus signUp(String username, String password, String employeeName, EmployeeRole employeeRole);

}
