//package controller;
//
//import model.enums.Commands;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions.*;
//
//import java.util.regex.Matcher;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserControllerTest {
//
//    @Test
//    void isRegisterCommandNULL(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad -p 1234 1234 -e ali@gmail.com -n hamed", Commands.CREATE_USER);
//        Assertions.assertNotNull(matcher);
//    }
//
//    @Test
//    void isUsernameValid(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad$% -p 1234 1234 -e ali@gmail.com -n hamed", Commands.CREATE_USER);
//        String response = UserController.registerUser(matcher);
//        assertEquals("Couldn't create user: invalid username!", response);
//    }
//
//    @Test
//    void isPasswordValid(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad -p 1234 1234 -e ali@gmail.com -n hamed", Commands.CREATE_USER);
//        String response = UserController.registerUser(matcher);
//        String password = matcher.group("password");
//        assertEquals(UserController.passwordChecker(password), response);
//    }
//
//    @Test
//    void isSloganRandom(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad -p 1234 1234 -e ali@gmail.com -n hamed -s random", Commands.CREATE_USER);
//        String response = UserController.registerUser(matcher);
//        String slogan = matcher.group("slogan");
//        assertNotNull(slogan);
//
//    }
//
//    @Test
//    void isPasswordConfirmationCorrect(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad -p 12F%34fsdfS fsdf34 -e ali@gmail.com -n hamed -s random", Commands.CREATE_USER);
//        String response = UserController.registerUser(matcher);
//        assertEquals("Couldn't create user: password confirmation failed!", response);
//    }
//
//    @Test
//    void isUserNameAlreadyUsed(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad11 -p 12F%34fsdfS 12F%34fsdfS -e aligmail.com -n hamed", Commands.CREATE_USER);
//        String response = UserController.registerUser(matcher);
//        assertEquals("Couldn't create user: invalid email!", response);
//    }
//
//    @Test
//    void isRegisterWorking(){
//        Matcher matcher = Commands.getOutput("user create -u ahmad111 -p 12F%34fsdfS 12F%34fsdfS -e alidada@gmail.com -n hamed", Commands.CREATE_USER);
//        String response = UserController.registerUser(matcher);
//        assertEquals("User created successfully!", response);
//    }
//
//
//
//
//}