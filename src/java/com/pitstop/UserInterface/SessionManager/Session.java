package com.pitstop.UserInterface.SessionManager;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.EmployeeRole;
import com.pitstop.Database.Middleware.Provider.ManagerProvider;
import com.pitstop.Database.Models.Authentication.AuthLevel;

public final class Session {

	private HashMap<String, SessionCache> savedLogins;

	private static Session instance = null;
	private SessionCache currentSession = null;

	private class SessionCache {
		private final SessionEmployee loggedInEmployee;
		private Date sessionExpiresAt;
		private final String token;

		private SessionCache(SessionEmployee loggedInEmployee, String token) {
			this.loggedInEmployee = loggedInEmployee;
			this.token = token;
		}
	}

	public static Session getInstance() {
		if (instance == null)
			instance = new Session();
		return instance;
	}

	private Session() {
		this.savedLogins = new HashMap<>();
	}

	public Employee getLoggedInAs() {
		return SessionEmployeeUtil.cloneToEmployee(currentSession.loggedInEmployee);
	}

	public SignInStatus signIn(String username, String password, EmployeeDetailsManager manager) {

		Employee employee = manager.getEmployee(username);

		if (employee == null) {
			return SignInStatus.UNKNOWN_USERNAME;
		}

		if (!employee.getPassword().equals(password)) {
			return SignInStatus.INVALID_PASSWORD;
		}

		SessionEmployee sessionEmployee = SessionEmployeeUtil.cloneToSessionEmployee(employee);

		String newToken = getToken(sessionEmployee.getEmployeeRole());
		currentSession = new SessionCache(sessionEmployee, newToken);

		ManagerProvider.getEmployeeManagerAuthUpdater().setAuthToken(currentSession.token);
		ManagerProvider.getIssueManagerAuthUpdater().setAuthToken(currentSession.token);
		return SignInStatus.SUCCESS;
	}

	public Map<String, SessionEmployee> getSavedLogins() {
		Collection<SessionCache> currentSavedCache = this.savedLogins.values();
		Map<String, SessionEmployee> savedLogins = new HashMap<>();

		currentSavedCache
				.forEach(cache -> savedLogins.put(cache.loggedInEmployee.getUsername(), cache.loggedInEmployee));

		return savedLogins;

	}

	public SignInStatus signInFromSavedLogin(String username) {

		if (!savedLogins.containsKey(username)) {
			return SignInStatus.UNKNOWN_USERNAME;
		}

		Employee employee = ManagerProvider.getEmployeeDetailsManager().getEmployee(username);
		if (employee == null) {
			savedLogins.remove(username);
			return SignInStatus.UNKNOWN_EMPLOYEE;
		}

		SessionCache savedSession = savedLogins.get(username);
		if (savedSession.sessionExpiresAt.before(new Date(System.currentTimeMillis()))) {
			savedLogins.remove(username);
			return SignInStatus.SESSION_EXPIRED;
		}

		Employee savedEmployeeLogin = SessionEmployeeUtil.cloneToEmployee(savedLogins.get(username).loggedInEmployee);
		if (!employee.getPassword().equals(savedEmployeeLogin.getPassword())) {
			savedLogins.remove(username);
			return SignInStatus.SESSION_EXPIRED;
		}

		currentSession = savedLogins.get(username);
		return SignInStatus.SUCCESS;

	}

	public EmployeeSignupManager.SignUpStatus signUp(String username, String password, String employeeName,
			EmployeeRole employeeRole, EmployeeSignupManager manager) {
		return manager.signUp(username, password, employeeName, employeeRole);
	}

	public void logout(boolean saveLoginDetails) {
		if (saveLoginDetails) {
			currentSession.sessionExpiresAt = new Date(System.currentTimeMillis() + 180000);
			savedLogins.put(currentSession.loggedInEmployee.getUsername(), currentSession);
		} else {
			if (savedLogins.containsKey(currentSession.loggedInEmployee.getUsername()))
				savedLogins.remove(currentSession.loggedInEmployee.getUsername());
		}

		ManagerProvider.getIssueManagerAuthUpdater().removeAuthToken();
		ManagerProvider.getEmployeeManagerAuthUpdater().removeAuthToken();

		currentSession = null;
	}

	private String getToken(EmployeeRole role) {
		AuthLevel authLevel = AuthLevel.UNKNOWN;

		switch (role) {
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

		return ManagerProvider.getAuthTokenManager().generateAuthToken(authLevel);

	}

}