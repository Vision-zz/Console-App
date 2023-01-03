package com.pitstop.Authentication.Manager.SignInManager;

import com.pitstop.Authentication.Manager.AuthenticationManager.AuthTokenManager;
import com.pitstop.Authentication.Model.AuthLevel;
import com.pitstop.Authentication.Model.AuthenticationStatus;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Models.Users.Employee;

public class AppAuthManager implements AuthenticationManager {

	private final AuthTokenManager tokenManager;
	private final EmployeeDetailsManager manager;

	public AppAuthManager(AuthTokenManager tokenManager, EmployeeDetailsManager manager) {
		this.tokenManager = tokenManager;
		this.manager = manager;
	}

	@Override
	public AuthenticationStatus authenticate(String username, String password) {

		Employee employee = manager.getEmployee(username);

		if (employee == null) {
			return new AuthenticationStatus.Fail("UNKNOWN_USERNAME");
		}

		if (!employee.getPassword().equals(password)) {
			return new AuthenticationStatus.Fail("INVALID_PASSWORD");
		}

		String newToken = getToken(employee);
		return new AuthenticationStatus.Success(newToken, employee);

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
