package com.pitstop.UserInterface.ConsoleFrontend;

import com.pitstop.ManagerProvider;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Middleware.Users.EmployeeSignupManager;
import com.pitstop.Core.Models.Users.EmployeeRole;
import com.pitstop.UserInterface.Helpers.Logger;
import com.pitstop.UserInterface.Helpers.Scanner;
import com.pitstop.UserInterface.Session.SessionManager.Session;
import com.pitstop.UserInterface.Session.SessionManager.SignInStatus;

public final class StartupUI {

	public SessionInitializeStatus showUserInterface() {

		Logger.logSuccess("---- Jogo Pitstop ----", "Select an option below");

		do {

			String input = Scanner.getString("1. Sign In | 2. Sign Up | 3. Exit");
			if (!input.matches("[123]")) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			if (input.equals("3"))
				return SessionInitializeStatus.FAILED;

			if (input.equals("2")) {
				SessionInitializeStatus signUpStatus = signUp();
				if (signUpStatus.equals(SessionInitializeStatus.RESTART))
					continue;

			}

			SessionInitializeStatus signInStatus = signIn();
			if (signInStatus.equals(SessionInitializeStatus.RESTART))
				continue;

			return signInStatus;

		} while (true);

	}

	public SessionInitializeStatus signIn() {

		EmployeeDetailsManager manager = ManagerProvider.getEmployeeDetailsManager();
		do {

			String username = Scanner.getString("Enter your username");

			SignInStatus savedLogInStatus = Session.getInstance().signInFromSavedLogin(username);

			if (savedLogInStatus.equals(SignInStatus.SUCCESS)) {
				Logger.logInfo("Logged in from saved session");
				break;
			}

			String password = Scanner.getString("Enter your password");

			SignInStatus status = Session.getInstance().signIn(username, password, manager);
			if (status.equals(SignInStatus.UNKNOWN_USERNAME)) {
				Logger.logWarning("User does not exist. Press 1 to try again or any key to Exit");

				String output = Scanner.getString();
				if (!output.equals("1"))
					return SessionInitializeStatus.RESTART;

				continue;
			}

			if (status.equals(SignInStatus.INVALID_PASSWORD)) {
				Logger.logWarning("Incorrect password. Press 1 to try again or any key to Exit.");

				String output = Scanner.getString();
				if (!output.equals("1"))
					return SessionInitializeStatus.RESTART;

				continue;
			}

			if (status.equals(SignInStatus.SUCCESS))
				break;

		} while (true);

		return SessionInitializeStatus.SUCCESS;

	}

	public SessionInitializeStatus signUp() {

		do {

			EmployeeRole employeeRole;
			do {
				Logger.logInfo("Select a role from the list below", "1. System Admin", "2. System Engineer",
						"3. Developer");
				String role = Scanner.getString();

				if (!role.matches("[123]")) {
					Logger.logWarning("Please select a valid option");
					continue;
				}

				employeeRole = role.equals("1") ? EmployeeRole.SYSTEM_ADMIN
						: role.equals("2") ? EmployeeRole.SYSTEM_ENGINEER : EmployeeRole.DEVELOPER;
				break;
			} while (true);

			String username = Scanner.getString("Enter username");

			if (!username.matches("^([a-z0-9_]*)$") || username.length() < 3) {
				Logger.logWarning(
						"Username contains invalid characters. Only lowercase alphabets, numbers and underscores are allowed and username should be atleast 3 characters long");

				String output = Scanner.getString("Press 1 to try again or any key to Exit");
				if (!output.equals("1"))
					return SessionInitializeStatus.RESTART;

				continue;

			}

			String password = Scanner.getString("Enter a password");

			if (password.matches("^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$")) {
				Logger.logWarning(
						"Invalid password. Password must contain atleast 1 Uppercase, Lowercase, Number and a character and should be more than 8 characters in length");

				String output = Scanner.getString("Press 1 to try again or any key to Exit");
				if (!output.equals("1"))
					return SessionInitializeStatus.RESTART;

				continue;
			}

			String employeeName = Scanner.getString("Enter your full name");

			EmployeeSignupManager manager = ManagerProvider.getEmployeeSignupManager();
			EmployeeSignupManager.SignUpStatus status = manager.signUp(username, password, employeeName, employeeRole);

			while (status.equals(EmployeeSignupManager.SignUpStatus.USERNAME_UNAVAILABLE)) {

				Logger.logWarning("Username not available. Press 1 to try again or any key to exit");
				String confirm = Scanner.getString();
				if (!confirm.equals("1"))
					return SessionInitializeStatus.RESTART;

				username = Scanner.getString("Enter username");
				status = manager.signUp(username, password, employeeName, employeeRole);

			}

			Logger.logSuccess("Successfully signed up as " + username);
			Scanner.getString("Press any key to continue into login page");

			break;

		} while (true);

		return SessionInitializeStatus.SUCCESS;
	}

}
