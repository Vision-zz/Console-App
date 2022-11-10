package com.pitstop.Authentication.Manager.SignInManager;

import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Authentication.Manager.SessionAuthenticationManager.DBAuthTokenManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Models.Users.Employee;

public class SignInAuthenticationManager implements SignInManager {

	private final DBAuthTokenManager tokenManager;
	private final EmployeeDetailsManager manager;

	public SignInAuthenticationManager(DBAuthTokenManager tokenManager, EmployeeDetailsManager manager) {
		this.tokenManager = tokenManager;
		this.manager = manager;
	}

	@Override
	public SignInStatus signIn(String username, String password) {

		Employee employee = manager.getEmployee(username);

		if (employee == null) {
			return new SignInStatus(true, null, "UNKNOWN_USERNAME", null);
		}

		if (!employee.getPassword().equals(password)) {
			return new SignInStatus(true, null, "INVALID_PASSWORD", null);
		}

		String newToken = getToken(employee);
		return new SignInStatus(false, newToken, "SUCCESS", employee);

	}

	private String getToken(Employee employee) {
		AuthLevel authLevel = AuthLevel.UNKNOWN;

		switch (employee.getEmployeeRole()) {
			case SYSTEM_ADMIN:
				authLevel = AuthLevel.ADMIN;
				break;
			case SYSTEM_ENGINEER:
				authLevel = AuthLevel.ENGINEER;
				break;
			case DEVELOPER:
				authLevel = AuthLevel.DEVELOPER;
				break;
		}

		return tokenManager.generateAuthToken(authLevel, employee);

	}

}
