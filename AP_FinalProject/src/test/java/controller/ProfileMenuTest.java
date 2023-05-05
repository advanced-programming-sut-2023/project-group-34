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

    void createUser1(String username, String gmail){
        String password = "1234As#12";
        Password password1 = new Password(password);
        password1.setPasswordName(password);
        User user = new User(username, password1, "hamed", gmail);
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
        assertEquals("Username already exists", MainController.changeUsername(matcher));
    }

    @Test
    void theSameUsername(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile change username -u ahmad", Commands.CHANGE_USER);
        assertEquals("Your username is already this, changing username failed", MainController.changeUsername(matcher));
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

    @Test
    void emptyChangePasswordRandomly(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -n random -o", Commands.CHANGE_PASSWORD_RANDOMLY);
        assertEquals("The required field is empty, changing password failed", MainController.changePasswordRandomly1(matcher));
    }

    @Test
    void wrongOldPassChangePasswordRandomly(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234As#1234 -n random", Commands.CHANGE_PASSWORD);
        assertEquals("Incorrect current password, changing password failed", MainController.changePasswordRandomly1(matcher));
    }

    @Test
    void runChangePasswordRandomly(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   password -o 1234As#12 -n random", Commands.CHANGE_PASSWORD);
        String oldPass = matcher.group("oldPass");
        Password password = new Password(oldPass);
        assertEquals("good for now", MainController.changePasswordRandomly1(matcher));
        assertFalse(password.checkPassword(User.currentUser.getPassword().getPasswordName()));
    }

    @Test
    void runChangePasswordRandomly2(){

    }

    @Test
    void emailEmptyField(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   -e  ", Commands.CHANGE_EMAIL);
        assertEquals("The required field is empty, changing email failed", MainController.changeEmail(matcher));
    }

    @Test
    void checkEmailFormat(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   -e  abbasagha", Commands.CHANGE_EMAIL);
        assertEquals("Email's format is invalid, changing email failed", MainController.changeEmail(matcher));
    }

    @Test
    void checkAlreadyUsedEmail(){
        createUser("ahmad", "ali@gmail.com");
        createUser("hamed", "reza@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   -e  ali@gmail.com", Commands.CHANGE_EMAIL);
        assertEquals("Email already exists, changing email failed", MainController.changeEmail(matcher));
    }

    @Test
    void theSameEmailChecker(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   -e  ali@gmail.com", Commands.CHANGE_EMAIL);
        assertEquals("Your email is already this, changing email failed", MainController.changeEmail(matcher));
    }

    @Test
    void runEmailChanger(){
        createUser("ahmad", "aa@gmail.com");
        Matcher matcher = Commands.getOutput("Profile  change   -e  reza@gmail.com", Commands.CHANGE_EMAIL);
        String email = matcher.group("email");
        assertEquals("Email changed successfully", MainController.changeEmail(matcher));
        assertEquals(email, User.currentUser.getEmail());
    }

    @Test
    void removeEmptySlogan(){
        createUser1("dadash", "a@gmail.com");
        Matcher matcher = Commands.getOutput("Profile    remove   slogan", Commands.CHANGE_EMAIL);
        assertEquals("Your slogan field is already empty, removing slogan failed", MainController.removeSlogan(matcher));
    }

    @Test
    void runRemoveSlogan(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile    remove   slogan", Commands.CHANGE_EMAIL);
        assertEquals("Slogan removed successfully", MainController.removeSlogan(matcher));
        assertNull(User.currentUser.getSlogan());
    }

    @Test
    void runChangeSloganRandomly(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile    remove   slogan  random", Commands.CHANGE_EMAIL);
        String oldSlogan = User.currentUser.getSlogan();
        assertEquals("Slogan changed successfully", MainController.changeSloganRandomly(matcher));
        assertNotEquals(oldSlogan, User.currentUser.getSlogan());
    }

    @Test
    void emptySloganField(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile    change   slogan -s ", Commands.CHANGE_SLOGAN);
        assertEquals("The slogan field is empty, changing slogan failed", MainController.changeSlogan(matcher));
    }

    @Test
    void usingTheSameSlogan(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile    change   slogan -s \"Let's go\"", Commands.CHANGE_SLOGAN);
        assertEquals("Your slogan is already this, changing slogan failed", MainController.changeSlogan(matcher));
    }

    @Test
    void runChangeSlogan(){
        createUser("ahmad", "ali@gmail.com");
        Matcher matcher = Commands.getOutput("Profile    change   slogan -s \"You will get what you deserve\"", Commands.CHANGE_SLOGAN);
        assertEquals("Slogan changed successfully", MainController.changeSlogan(matcher));

    }







}