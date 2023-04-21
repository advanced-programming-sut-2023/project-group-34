package model.user;

import model.enums.SecurityQuestion;

import java.security.MessageDigest;

public class Password {
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

    public boolean checkPassword(String password) {
        return passwordName.equals(sha256Encrypt(password));
    }

    public void setPasswordName(String passwordName) {
        this.passwordName = sha256Encrypt (passwordName);
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}

