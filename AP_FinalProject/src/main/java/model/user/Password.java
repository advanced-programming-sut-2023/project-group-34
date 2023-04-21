package model.user;

import model.enums.SecurityQuestion;

public class Password {
    private String passwordName;
    private SecurityQuestion securityQuestion;
    private String answer;

    private static String sha256Encrypt (String string) {
        return null;
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

