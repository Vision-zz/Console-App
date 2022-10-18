package UserInterface.SessionManager;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Core.Middleware.Users.EmployeeDetailsManager;
import Core.Models.Users.Employee;
import Core.Models.Users.EmployeeRole;
import Database.Middleware.Users.EmployeeSignupManager;
import Database.Middleware.Users.SignUpStatus;
import Database.Models.Users.EmployeeDatabase;

public final class Session {

	private HashMap<String, SessionCache> savedLogins;

	private static Session instance = null;
	private SessionCache currentSession = null;

	private class SessionCache {
		private final SessionEmployee loggedInEmployee;
		private Date sessionExpiresAt;

		SessionCache(SessionEmployee loggedInEmployee) {
			this.loggedInEmployee = loggedInEmployee;
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

		// DBEmployee dbEmployee2 = manager
		if (employee == null) {
			return SignInStatus.UNKNOWN_USERNAME;
		}

		if (!employee.getPassword().equals(password)) {
			return SignInStatus.INVALID_PASSWORD;
		}

		SessionEmployee sessionEmployee = SessionEmployeeUtil.cloneToSessionEmployee(employee);
		currentSession = new SessionCache(sessionEmployee);
		return SignInStatus.SUCCESS;
	}

	public Map<String, SessionEmployee> getSavedLogins() {
		Collection<SessionCache> currentSavedCache = this.savedLogins.values();
		Map<String, SessionEmployee> savedLogins = new HashMap<>();

		currentSavedCache.forEach(cache -> {
			
			savedLogins.put(cache.loggedInEmployee.getUsername(), cache.loggedInEmployee);
		});

		return savedLogins;
	}

	public SignInStatus signInFromSavedLogin(String username) {

		if (!savedLogins.containsKey(username)) {
			return SignInStatus.UNKNOWN_USERNAME;
		}

		Employee dbEmployee = SessionEmployeeUtil.cloneToEmployee(EmployeeDatabase.getInstance().get(username));
		if (dbEmployee == null) {
			return SignInStatus.UNKNOWN_EMPLOYEE;
		}

		SessionCache savedSession = savedLogins.get(username);
		if (savedSession.sessionExpiresAt.after(new Date(System.currentTimeMillis()))) {
			return SignInStatus.SESSION_EXPIRED;
		}

		Employee savedEmployeeLogin = SessionEmployeeUtil.cloneToEmployee(savedLogins.get(username).loggedInEmployee);
		if (!dbEmployee.getPassword().equals(savedEmployeeLogin.getPassword())) {
			return SignInStatus.SESSION_EXPIRED;
		}

		return SignInStatus.SUCCESS;

	}

	public SignUpStatus signUp(String username, String password, String employeeName, EmployeeRole employeeRole,
			EmployeeSignupManager manager) {
		return manager.signUp(username, password, employeeName, employeeRole);

	}

	public void logout(boolean saveLoginDetails) {
		if (saveLoginDetails) {
			currentSession.sessionExpiresAt = new Date(System.currentTimeMillis() + 180000);
			savedLogins.put(currentSession.loggedInEmployee.getEmployeeName(), currentSession);
		}
		currentSession = null;
	}

}