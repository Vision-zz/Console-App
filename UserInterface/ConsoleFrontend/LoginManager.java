package UserInterface.ConsoleFrontend;

import Core.Middleware.Users.EmployeeDetailsManager;
import Core.Models.Users.EmployeeRole;
import Database.Middleware.Users.DBEmployeeManager;
import Database.Middleware.Users.EmployeeSignupManager;
import Database.Middleware.Users.SignUpStatus;
import UserInterface.Helpers.Logger;
import UserInterface.Helpers.Scanner;
import UserInterface.SessionManager.Session;
import UserInterface.SessionManager.SignInStatus;

public final class LoginManager {

	public SessionInitializeStatus initializeSession() {

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

		EmployeeDetailsManager manager = DBEmployeeManager.getInstance();
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

			String username = Scanner.getString("Enter username");

			if (username.matches("[^a-z0-9_]+") || username.length() < 3) {
				Logger.logWarning(
						"Username contains invalid characters. Only lowercase alphabets, numbers and underscores are allowed and username should be atleast 3 characters long");

				String output = Scanner.getString("Press 1 to try again or any key to Exit");
				if (!output.equals("1"))
					return SessionInitializeStatus.RESTART;

				continue;

			}

			String password = Scanner.getString("Enter a password");
			String employeeName = Scanner.getString("Enter your full name");

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

			EmployeeSignupManager manager = DBEmployeeManager.getInstance();
			SignUpStatus status = manager.signUp(username, password, employeeName, employeeRole);

			while (status.equals(SignUpStatus.USERNAME_UNAVAILABLE)) {

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
