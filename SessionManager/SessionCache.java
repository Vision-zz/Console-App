package SessionManager;

import Users.Employee;

public final class SessionCache {
    private static Employee loggedInAs = null;

    private static SessionCache instance = null;

    private SessionCache() {
    }

    public static SessionCache getInstance() {
        if (instance == null)
            instance = new SessionCache();
        return instance;
    }

    public static Employee getLoggedInUser() {
        return loggedInAs;
    }

    protected void login(Employee emp) {
        loggedInAs = emp;
    }

    protected void logOut() {
        loggedInAs = null;
    }
}
