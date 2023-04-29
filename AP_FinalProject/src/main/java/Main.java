import controller.Runner;
import model.user.User;

public class Main {
    public static void main(String[] args) {
        User.loadAllUsersFromDataBase();
        User.loadCurrentUser();
        Runner.run();
    }
}