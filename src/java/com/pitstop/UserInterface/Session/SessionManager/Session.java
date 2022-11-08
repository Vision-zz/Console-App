package com.pitstop.UserInterface.Session.SessionManager;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pitstop.ManagerProvider;
import com.pitstop.Authentication.Database.AuthLevel;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.EmployeeRole;

public final class Session {

	private HashMap<String, ActiveSession> savedLogins;

	private static Session instance = null;
	private ActiveSession currentSession = null;

	class ActiveSession {
		private final SessionEmployee loggedInEmployee;
		private Date sessionExpiresAt;
		private final String token;
		private final String sessionID;

		private ActiveSession(SessionEmployee loggedInEmployee, String token) {
			this.loggedInEmployee = loggedInEmployee;
			this.token = token;
			sessionID = UUID.randomUUID().toString();
		}

		public SessionEmployee getLoggedInEmployee() {
			return loggedInEmployee;
		}

		public Date getSessionExpiresAt() {
			return sessionExpiresAt;
		}

		public String getSessionID() {
			return sessionID;
		}

		public String getToken() {
			return token;
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

		String newToken = getToken(employee);
		currentSession = new ActiveSession(sessionEmployee, newToken);

		return SignInStatus.SUCCESS;
	}

	public Map<String, SessionEmployee> getSavedLogins() {
		Collection<ActiveSession> currentSavedCache = this.savedLogins.values();
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

		ActiveSession savedSession = savedLogins.get(username);
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

		currentSession = null;
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

		return ManagerProvider.getAuthTokenManager().generateAuthToken(authLevel, employee);

	}

}