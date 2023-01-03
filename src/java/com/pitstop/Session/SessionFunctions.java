package com.pitstop.Session;

import java.util.Map;

import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.EmployeeRole;

public interface SessionFunctions {
	public Employee getLoggedInAs();

	public SessionSignInStatus signIn(String username, String password, EmployeeDetailsManager manager);

	public Map<String, SessionEmployee> getSavedLogins();

	public SessionSignInStatus signInFromSavedLogin(String username);

	public EmployeeSignupManager.SignUpStatus signUp(String username, String password, String employeeName,
			EmployeeRole employeeRole, EmployeeSignupManager manager);

	public void logout(boolean saveLoginDetails);
}
