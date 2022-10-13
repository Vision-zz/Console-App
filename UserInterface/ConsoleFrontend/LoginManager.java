package UserInterface.ConsoleFrontend;

import Database.Middleware.Users.DBEmployeeManager;
import UserInterface.Helpers.Logger;
import UserInterface.Helpers.Scanner;
import UserInterface.SessionManager.Session;
import UserInterface.SessionManager.SignInStatus;

public class LoginManager {

	public boolean start() {

		Logger.logSuccess("---- Jogo Pitstop ----", "Select an option below");

		do {

			String input = Scanner.getString("1. Sign In | 2. Sign Up | 3. Exit");
			if (!input.matches("[123]")) {
				Logger.logWarning("Please select a valid option");
				continue;
			}

			if (input.equals("3"))
				return false;

			if (input.equals("1"))
				return signIn();

			if (input.equals("2")) {
				boolean signUpStatus = signUp();
				if (!signUpStatus)
					return false;

				return signIn();

			}

		} while (true);

	}

	public boolean signIn() {

		do {

			String username = Scanner.getString("Enter your username");
			String password = Scanner.getString("Enter your password");

			SignInStatus status = Session.getInstance().signIn(username, password, DBEmployeeManager.getInstance());
			if (status.equals(SignInStatus.UNKNOWN_USERNAME)) {
				Logger.logWarning("Username invalid. Try again");
				continue;
			}

			if (status.equals(SignInStatus.INVALID_PASSWORD)) {
				Logger.logWarning("Incorrect password. Try again");
				continue;
			}

			if (status.equals(SignInStatus.SUCCESS))
				break;

		} while (true);

		return true;
	}

	public boolean signUp() {
		return false;
	}

	public void logout() {

	}

}
