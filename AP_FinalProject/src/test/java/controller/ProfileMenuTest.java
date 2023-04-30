package controller;

import com.sun.tools.javac.Main;
import model.enums.Commands;
import model.user.Password;
import model.user.User;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class ProfileMenuTest {

    void createUser(String username, String gmail){
        String password = "1234As#12";
        Password password1 = new Password(password);
        password1.setPasswordName(password);
        String slogan = "Let's go";
        User user = new User(username, password1, "hamed", gmail);
        user.setSlogan(slogan);
        User.setCurrentUser(user);
    }
    @Test
    void emptyChangeUserError(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change username -u", Commands.CHANGE_USER);
        assertEquals("The username field is empty, changing username failed", MainController.changeUsername(matcher));
    }

    @Test
    void invalidUserError(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change username -u dfdf$#", Commands.CHANGE_USER);
        assertEquals("Username's format is invalid, changing username failed", MainController.changeUsername(matcher));
    }

    @Test
    void alreadyUsedUsername(){
        createUser("ahmad", "ali@gmail.com");
        createUser("hamed", "reza@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change username -u ahmad", Commands.CHANGE_USER);
        assertEquals("Username already exists, changing username failed", MainController.changeUsername(matcher));
    }

    @Test
    void theSameUsername(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change username -u ahmad", Commands.CHANGE_USER);
        assertEquals("You username is already this, changing username failed", MainController.changeUsername(matcher));
    }

    @Test
    void runChangeUsername(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change username -u reza", Commands.CHANGE_USER);
        assertEquals("Username successfully changed", MainController.changeUsername(matcher));
        assertNotNull(UserController.getUserByUsername("reza"));
    }

    @Test
    void emptyChangeNicknameError(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change -n  ", Commands.CHANGE_NICKNAME);
        assertEquals("The nickname field is empty, changing nickname failed", MainController.changeNickname(matcher));
    }

    @Test
    void theSameNickname(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change -n hamed", Commands.CHANGE_NICKNAME);
        assertEquals("Your nickname is already this, changing nickname failed", MainController.changeNickname(matcher));
    }

    @Test
    void runChangeNickname(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change -n reza", Commands.CHANGE_NICKNAME);
        assertEquals("Nickname changed successfully", MainController.changeNickname(matcher));
        assertEquals("reza", User.currentUser.getNickname());

    }

    @Test
    void emptyChangePasswordOldField(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o -n 1234", Commands.CHANGE_PASSWORD);
        assertEquals("The old password filed is empty, changing password failed", MainController.changePasswordPart1(matcher));
    }

    @Test
    void emptyChangePasswordNewField(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234 -n ", Commands.CHANGE_PASSWORD);
        assertEquals("The new password filed is empty, changing password failed", MainController.changePasswordPart1(matcher));
    }

    @Test
    void checkPasswordValidation(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234As#12 -n 1234", Commands.CHANGE_PASSWORD);
        assertEquals("weak password(less than 6 chars)!", MainController.changePasswordPart1(matcher));
    }

    @Test
    void checkPasswordValidation1(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234As#12 -n 1234567sA", Commands.CHANGE_PASSWORD);
        assertEquals("weak password(doesn't have needed chars)!", MainController.changePasswordPart1(matcher));
    }

    @Test
    void isCurrentPasswordCorrect(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234As#149 -n 123456As#", Commands.CHANGE_PASSWORD);
        assertEquals("Incorrect current password, changing password failed", MainController.changePasswordPart1(matcher));
    }

    @Test
    void isCurrentPasswordDifferent(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234As#12 -n 1234As#12", Commands.CHANGE_PASSWORD);
        assertEquals("Your new password has to be different from your current password, changing password failed", MainController.changePasswordPart1(matcher));
    }

    @Test
    void runChangePasswordPart1(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -n 4567As#12 -o 1234As#12", Commands.CHANGE_PASSWORD);
        assertEquals("good for now", MainController.changePasswordPart1(matcher));
    }

    @Test
    void passwordConfirmation(){
        createUser("ahmad", "ali@gmail.com");
        String newPass = "4567As#12";
        String confirmation = "56895dfd$2";
        assertEquals("confirmation password does not match the initial password, changing password failed", MainController.changePasswordPart2(newPass, confirmation));

    }

    @Test
    void runChangePasswordPart2(){
        createUser("ahmad", "ali@gmail.com");
        String newPass = "4567As#12";
        String confirmation = "4567As#12";
        assertEquals("Password changed successfully", MainController.changePasswordPart2(newPass, confirmation));
        assertTrue(User.currentUser.getPassword().checkPassword(newPass));
    }



}