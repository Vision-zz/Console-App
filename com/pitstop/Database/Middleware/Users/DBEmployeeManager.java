package pitstop.Database.Middleware.Users;

import java.util.Collection;
import java.util.HashSet;

import pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import pitstop.Core.Models.Users.Developer;
import pitstop.Core.Models.Users.Employee;
import pitstop.Core.Models.Users.EmployeeRole;
import pitstop.Core.Models.Users.SystemAdmin;
import pitstop.Core.Models.Users.SystemEngineer;
import pitstop.Database.Middleware.Issues.DBIssueManager;
import pitstop.Database.Models.Users.DBEmployee;
import pitstop.Database.Models.Users.EmployeeDatabase;

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
	public Employee getEmployee(String username) {
		DBEmployee dbEmployee = EmployeeDatabase.getInstance().get(username);
		if (dbEmployee == null)
			return null;
		return EmployeeUtil.cloneToEmployee(dbEmployee);
	}

	@Override
	public Employee getEmployeeByID(String employeeID) {
		Collection<DBEmployee> dbEmployee = EmployeeDatabase.getInstance().getAll().values();

		Employee employee = null;
		for (DBEmployee e : dbEmployee) {
			if (e.getEmployeeID().equals(employeeID)) {
				return EmployeeUtil.cloneToEmployee(e);
			}
		}
		return employee;
	}

	@Override
	public SignUpStatus signUp(String username, String password, String employeeName, EmployeeRole employeeRole) {
		DBEmployee employee = EmployeeDatabase.getInstance().get(username);
		if (employee != null) {
			return SignUpStatus.USERNAME_UNAVAILABLE;
		}

		Employee newEmployee;
		switch (employeeRole) {
			case SYSTEM_ADMIN:
				newEmployee = new SystemAdmin(username, password, employeeName, DBIssueManager.getInstance(),
						DBEmployeeManager.getInstance());
				break;

			case SYSTEM_ENGINEER:
				newEmployee = new SystemEngineer(username, password, employeeName, DBIssueManager.getInstance());
				break;

			default:
				newEmployee = new Developer(username, password, employeeName, DBIssueManager.getInstance());
				break;
		}

		DBEmployee dbEmployee = EmployeeUtil.cloneToDBEmployee(newEmployee);
		EmployeeDatabase.getInstance().add(dbEmployee);
		return SignUpStatus.SUCCESS;
	}

}
