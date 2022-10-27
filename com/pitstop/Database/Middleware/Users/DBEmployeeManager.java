package com.pitstop.Database.Middleware.Users;

import java.util.Collection;
import java.util.HashSet;

import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.EmployeeRole;
import com.pitstop.Core.Models.Users.SystemAdmin;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Issues.DBIssueManager;
import com.pitstop.Database.Models.Users.DBEmployee;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class DBEmployeeManager implements EmployeeDetailsManager, EmployeeSignupManager {

	private static DBEmployeeManager instance = null;
	private static int employeeID = 0;
	private final EmployeeDatabase database;

	public static DBEmployeeManager getInstance() {
		if (instance == null)
			instance = new DBEmployeeManager(EmployeeDatabase.getInstance());
		return instance;
	}

	private DBEmployeeManager(EmployeeDatabase database) {
		this.database = database;
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		Collection<DBEmployee> dbEmployees = database.getAll().values();
		Collection<Employee> allEmployees = new HashSet<>();
		dbEmployees.forEach(employee -> {
			allEmployees.add(EmployeeUtil.cloneToEmployee(employee));
		});
		return allEmployees;
	}

	@Override
	public Employee getEmployee(String username) {
		DBEmployee dbEmployee = database.get(username);
		if (dbEmployee == null)
			return null;
		return EmployeeUtil.cloneToEmployee(dbEmployee);
	}

	@Override
	public Employee getEmployeeByID(String employeeID) {
		Collection<DBEmployee> dbEmployee = database.getAll().values();

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
		DBEmployee employee = database.get(username);
		if (employee != null) {
			return SignUpStatus.USERNAME_UNAVAILABLE;
		}

		Employee newEmployee;
		switch (employeeRole) {
			case SYSTEM_ADMIN:
				newEmployee = new SystemAdmin(username, password, employeeName,
						EmployeeRole.SYSTEM_ADMIN + "_" + employeeID++, DBIssueManager.getInstance(),
						DBEmployeeManager.getInstance());
				break;

			case SYSTEM_ENGINEER:
				newEmployee = new SystemEngineer(username, password, employeeName,
						EmployeeRole.SYSTEM_ENGINEER + "_" + employeeID++, DBIssueManager.getInstance());
				break;

			default:
				newEmployee = new Developer(username, password, employeeName,
						EmployeeRole.DEVELOPER + "_" + employeeID++, DBIssueManager.getInstance());
				break;
		}

		DBEmployee dbEmployee = EmployeeUtil.cloneToDBEmployee(newEmployee);
		database.add(dbEmployee);
		return SignUpStatus.SUCCESS;
	}

}
