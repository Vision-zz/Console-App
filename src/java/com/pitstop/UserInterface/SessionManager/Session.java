package com.pitstop.UserInterface.SessionManager;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pitstop.ManagerProvider;
import com.pitstop.Authentication.Manager.SignInManager.SignInManager;
import com.pitstop.Authentication.Manager.SignInManager.SignInStatus;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.EmployeeRole;

public final class Session implements SessionFunctions {

	private HashMap<String, SessionCache> savedLogins;

	private static Session instance = null;
	private SessionCache currentSession = null;
	private final SignInManager signInManager;

	class SessionCache {
		private final SessionEmployee loggedInEmployee;
		private Date sessionExpiresAt;
		private final String token;

		private SessionCache(SessionEmployee loggedInEmployee, String token) {
			this.loggedInEmployee = loggedInEmployee;
			this.token = token;
		}

		public SessionEmployee getLoggedInEmployee() {
			return loggedInEmployee;
		}

		public Date getSessionExpiresAt() {
			return sessionExpiresAt;
		}

		public String getToken() {
			return token;
		}
	}

	public static Session getInstance(SignInManager manager) {
		if (instance == null)
			instance = new Session(manager);
		return instance;
	}

	private Session(SignInManager manager) {
		this.savedLogins = new HashMap<>();
		this.signInManager = manager;
	}

	@Override
	public Employee getLoggedInAs() {
		return SessionEmployeeUtil.cloneToEmployee(currentSession.loggedInEmployee);
	}

	@Override
	public SessionSignInStatus signIn(String username, String password, EmployeeDetailsManager manager) {

		SignInStatus status = signInManager.signIn(username, password);

		if (status.isProcessFailed) {
			return status.reason.equals("UNKNOWN_USERNAME") ? SessionSignInStatus.UNKNOWN_USERNAME
					: SessionSignInStatus.INVALID_PASSWORD;
		}

		SessionEmployee sessionEmployee = SessionEmployeeUtil.cloneToSessionEmployee(status.employee);

		currentSession = new SessionCache(sessionEmployee, status.token);
		ManagerProvider.getSessoinAuthTokenManager().updateCurrentToken(status.token);

		return SessionSignInStatus.SUCCESS;
	}

	@Override
	public Map<String, SessionEmployee> getSavedLogins() {
		Collection<SessionCache> currentSavedCache = this.savedLogins.values();
		Map<String, SessionEmployee> savedLogins = new HashMap<>();

		currentSavedCache
				.forEach(cache -> savedLogins.put(cache.loggedInEmployee.getUsername(), cache.loggedInEmployee));

		return savedLogins;

	}

	@Override
	public SessionSignInStatus signInFromSavedLogin(String username) {

		if (!savedLogins.containsKey(username)) {
			return SessionSignInStatus.UNKNOWN_USERNAME;
		}

		SessionCache savedSession = savedLogins.get(username);
		if (savedSession.sessionExpiresAt.before(new Date(System.currentTimeMillis()))) {
			savedLogins.remove(username);
			return SessionSignInStatus.SESSION_EXPIRED;
		}

		SignInStatus status = signInManager.signIn(savedSession.getLoggedInEmployee().getUsername(),
				savedSession.getLoggedInEmployee().getPassword());

		if (status.isProcessFailed) {
			savedLogins.remove(username);
			return status.reason == "UNKNOWN_USERNAME" ? SessionSignInStatus.UNKNOWN_EMPLOYEE : SessionSignInStatus.SESSION_EXPIRED;
		}

		currentSession = savedLogins.get(username);
		ManagerProvider.getSessoinAuthTokenManager().updateCurrentToken(currentSession.getToken());
		return SessionSignInStatus.SUCCESS;

	}

	@Override
	public EmployeeSignupManager.SignUpStatus signUp(String username, String password, String employeeName,
			EmployeeRole employeeRole, EmployeeSignupManager manager) {
		return manager.signUp(username, password, employeeName, employeeRole);
	}

	@Override
	public void logout(boolean saveLoginDetails) {
		if (saveLoginDetails) {
			currentSession.sessionExpiresAt = new Date(System.currentTimeMillis() + 180000);
			savedLogins.put(currentSession.loggedInEmployee.getUsername(), currentSession);
		} else {
			if (savedLogins.containsKey(currentSession.loggedInEmployee.getUsername())) {
				ManagerProvider.getAuthTokenManager().invalidateToken(currentSession.getToken());
				savedLogins.remove(currentSession.loggedInEmployee.getUsername());
			}
		}

		currentSession = null;
		ManagerProvider.getSessoinAuthTokenManager().invalidateCurrentToken();
	}

}