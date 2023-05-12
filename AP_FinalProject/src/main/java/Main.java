import controller.Runner;
import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1 , 2 , 3 , 5));
        System.out.println(integers.indexOf(10));
        //User.loadAllUsersFromDataBase();
        //Runner.run();

    }
}