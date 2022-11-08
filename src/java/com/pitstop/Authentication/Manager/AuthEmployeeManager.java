package com.pitstop.Authentication.Manager;

import java.util.Collection;

import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class AuthEmployeeManager extends DBEmployeeManager {

	private final AuthLevelManager manager;
	private String token = null;

	private static final AuthLevel ADMIN_AUTH_LEVEL = AuthLevel.ADMIN;
	// private static final AuthLevel ENGINEER_AUTH_LEVEL = AuthLevel.ENGINEER;
	// private static final AuthLevel DEV_AUTH_LEVEL = AuthLevel.DEVELOPER;
	private static final String EXCEPTION_MESSAGE = "You are not authorized to make this action";

	public AuthEmployeeManager(EmployeeDatabase database, AuthLevelManager manager) {
		super(database);
		this.manager = manager;
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		checkAuth();
		return super.getAllEmployees();
	}

	@Override
	public Employee getEmployee(String username) {
		checkAuth();
		return super.getEmployee(username);
	}

	@Override
	public Employee getEmployeeByID(String employeeID) {
		checkAuth();
		return super.getEmployeeByID(employeeID);
	}

	private boolean checkAuth() {
		if (!manager.validateTokenAuthLevel(token, ADMIN_AUTH_LEVEL))
			throw new RuntimeException(EXCEPTION_MESSAGE);
		return true;
	}

}
