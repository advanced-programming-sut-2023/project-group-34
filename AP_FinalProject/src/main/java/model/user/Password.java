package model.user;

import model.enums.SecurityQuestion;

import java.security.MessageDigest;
import java.util.Random;

public class Password {
    public String getPasswordName() {
        return passwordName;
    }

    private String passwordName;
    private SecurityQuestion securityQuestion;
    private String answer;

    private static String sha256Encrypt (final String string) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(string.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public Password(String password) {
        this.passwordName = sha256Encrypt(password);
    }

    public boolean checkPassword(String password) {
        return passwordName.equals(sha256Encrypt(password));
    }

    public void setPasswordName(String passwordName) {
        this.passwordName = sha256Encrypt (passwordName);
    }

    public static String randomPassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));

        for(int i = 0; i< 10 ; i++) {
            password.append(combinedChars.charAt(random.nextInt(combinedChars.length())));
        }
        return password.toString();
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(sha256Encrypt(answer));
    }

    public void setAnswer(String answer) {
        this.answer = sha256Encrypt(answer);
    }
}

