package Users;

public final class SystemAdmin extends Employee {

    public SystemAdmin(String username, String password, String employeeName) {
        super(username, password, employeeName, "ADMIN");
    }

    @Override
    void start() {
        // TODO Auto-generated method stub

    }

}
