package controller;

import model.enums.Commands;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void isRegisterCommandNULL(){
        Matcher matcher = Commands.getOutput("user create -u ahmad -p 1234 1234 -e ali@gmail.com -n hamed", Commands.CREATE_USER);
        Assertions.assertNotNull(matcher);
    }

}