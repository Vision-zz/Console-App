package com.pitstop.Session;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pitstop.ManagerProvider;
import com.pitstop.Authentication.Manager.SignInManager.AuthenticationManager;
import com.pitstop.Authentication.Model.AuthenticationStatus;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.EmployeeRole;

public final class SessionManager implements SessionFunctions {

	private HashMap<String, Session> savedLogins;

	private static SessionManager instance = null;
	private Session currentSession = null;
	private final AuthenticationManager signInManager;

	class Session {
		private final SessionEmployee loggedInEmployee;
		private Date sessionExpiresAt;
		private final String token;

		private Session(SessionEmployee loggedInEmployee, String token) {
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

	public static SessionManager getInstance(AuthenticationManager manager) {
		if (instance == null)
			instance = new SessionManager(manager);
		return instance;
	}

	private SessionManager(AuthenticationManager manager) {
		this.savedLogins = new HashMap<>();
		this.signInManager = manager;
	}

	@Override
	public Employee getLoggedInAs() {
		return SessionEmployeeUtil.cloneToEmployee(currentSession.loggedInEmployee);
	}

	@Override
	public SessionSignInStatus signIn(String username, String password, EmployeeDetailsManager manager) {

		AuthenticationStatus status = signInManager.authenticate(username, password);

		if (status instanceof AuthenticationStatus.Fail) {
			AuthenticationStatus.Fail failedStatus = (AuthenticationStatus.Fail) status;
			return failedStatus.reason.equals("UNKNOWN_USERNAME") ? SessionSignInStatus.UNKNOWN_USERNAME
					: SessionSignInStatus.INVALID_PASSWORD;
		}

		AuthenticationStatus.Success successStatus = (AuthenticationStatus.Success) status;

		SessionEmployee sessionEmployee = SessionEmployeeUtil.cloneToSessionEmployee(successStatus.employee);

		currentSession = new Session(sessionEmployee, successStatus.token);
		ManagerProvider.getSessoinAuthTokenManager().updateCurrentToken(successStatus.token);

		return SessionSignInStatus.SUCCESS;
	}

	@Override
	public Map<String, SessionEmployee> getSavedLogins() {
		Collection<Session> currentSavedCache = this.savedLogins.values();
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

		Session savedSession = savedLogins.get(username);
		if (savedSession.sessionExpiresAt.before(new Date(System.currentTimeMillis()))) {
			savedLogins.remove(username);
			return SessionSignInStatus.SESSION_EXPIRED;
		}

		AuthenticationStatus status = signInManager.authenticate(savedSession.getLoggedInEmployee().getUsername(),
				savedSession.getLoggedInEmployee().getPassword());

		if (status instanceof AuthenticationStatus.Fail) {
			AuthenticationStatus.Fail failedStatus = (AuthenticationStatus.Fail) status;
			savedLogins.remove(username);
			return failedStatus.reason == "UNKNOWN_USERNAME" ? SessionSignInStatus.UNKNOWN_EMPLOYEE
					: SessionSignInStatus.SESSION_EXPIRED;
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