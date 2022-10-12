package Database.Middleware.Users;

import java.util.Collection;
import java.util.HashSet;

import Core.Middleware.Users.EmployeeDetailsManager;
import Core.Models.Users.Employee;
import Core.Models.Users.EmployeeRole;
import Database.Models.Users.DBEmployee;
import Database.Models.Users.EmployeeDatabase;

public class DBEmployeeManager implements EmployeeDetailsManager, EmployeeSignupManager {

	private static DBEmployeeManager instance = null;

	public static DBEmployeeManager getInstance() {
		if (instance == null)
			instance = new DBEmployeeManager();
		return instance;
	}

	private DBEmployeeManager() {
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		Collection<DBEmployee> dbEmployees = EmployeeDatabase.getInstance().getAll().values();
		Collection<Employee> allEmployees = new HashSet<>();
		dbEmployees.forEach(employee -> {
			allEmployees.add(EmployeeUtil.cloneToEmployee(employee));
		});
		return allEmployees;
	}

	@Override
	public void signUp(String username, String password, String employeeName, EmployeeRole employeeRole) {
		DBEmployee employee = EmployeeDatabase.getInstance().get(username);
		if (employee != null) {
			throw new RuntimeException("Username not available");
		}
		DBEmployee dbEmployee = new DBEmployee(username, password, employeeName, employeeRole);
		EmployeeDatabase.getInstance().add(dbEmployee);
	}

}
