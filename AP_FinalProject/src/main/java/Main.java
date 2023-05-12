import controller.Runner;
import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        User.loadAllUsersFromDataBase();
        Runner.run();
    }
}