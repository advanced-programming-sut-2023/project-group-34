package src.model.enums;

public enum SecurityQuestion {
    FIRST_QUESTION("What is my father's name?"),
    SECOND_QUESTION("What was my first pet's name?"),
    THIRD_QUESTION("What is my mother's last name?")
    ;
    final String securityQuestion;
    SecurityQuestion(String securityQuestion){
        this.securityQuestion = securityQuestion;
    }
}
