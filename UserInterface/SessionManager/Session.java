package UserInterface.SessionManager;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import Core.Models.Users.Employee;
import Core.Models.Users.EmployeeRole;
import Database.Middleware.Users.EmployeeSignupManager;
import Database.Middleware.Users.EmployeeUtil;
import Database.Middleware.Users.SignUpStatus;
import Database.Models.Users.DBEmployee;
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
		return EmployeeUtil.cloneToEmployee(currentSession.loggedInEmployee);
	}

	public SignInStatus signIn(String username, String password) {
		// TODO
		DBEmployee dbEmployee = EmployeeDatabase.getInstance().get(username);
		if (dbEmployee == null) {
			return SignInStatus.UNKNOWN_USERNAME;
		}

		if (!dbEmployee.getPassword().equals(password)) {
			return SignInStatus.INVALID_PASSWORD;
		}

		SessionEmployee employee = EmployeeUtil.cloneToSessionEmployee(EmployeeUtil.cloneToEmployee(dbEmployee));
		currentSession = new SessionCache(employee);
		return SignInStatus.SUCCESS;
	}

	public Collection<Employee> getSavedLogins() {
		Collection<SessionCache> currentSavedCache = this.savedLogins.values();
		Collection<Employee> savedLogins = new HashSet<>();

		currentSavedCache.forEach(cache -> {
			savedLogins.add(EmployeeUtil.cloneToEmployee(cache.loggedInEmployee));
		});

		return savedLogins;
	}

	public SignInStatus signInFromSavedLogin(String username) {

		if (!savedLogins.containsKey(username)) {
			return SignInStatus.UNKNOWN_USERNAME;
		}

		Employee dbEmployee = EmployeeUtil.cloneToEmployee(EmployeeDatabase.getInstance().get(username));
		if(dbEmployee == null) {
			return SignInStatus.UNKNOWN_EMPLOYEE;
		}
		
		SessionCache savedSession = savedLogins.get(username);
		if(savedSession.sessionExpiresAt.after(new Date(System.currentTimeMillis()))) {
			return SignInStatus.SESSION_EXPIRED;
		}
		
		Employee savedEmployeeLogin = EmployeeUtil.cloneToEmployee(savedLogins.get(username).loggedInEmployee);
		if(!dbEmployee.getPassword().equals(savedEmployeeLogin.getPassword())) {
			return SignInStatus.SESSION_EXPIRED;
		}

		return SignInStatus.SUCCESS;

	}

	public SignUpStatus signUp(String username, String password, String employeeName, EmployeeRole employeeRole, EmployeeSignupManager manager) {
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