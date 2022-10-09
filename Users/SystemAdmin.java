package Users;

public final class SystemAdmin extends Employee {

    SystemAdmin(String username, String password, String employeeName) {
        super(username, password, employeeName, "SA");
    }

    @Override
    void start() {
        // TODO Auto-generated method stub

    }

}
