package Database;

import Users.Employee;

public class Session {
    private static Employee loggedInAs = null;

    private Session() {
    }

    public static Employee getLoggedInUser() {
        return loggedInAs;
    }

    public static void login(Employee emp) {
        loggedInAs = emp;
    }

    public static void logOut() {
        loggedInAs = null;
    }
}
