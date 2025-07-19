package myappuserservice.springmyapp.validation;

public class Validation {
    private static final String EMAIL_PATTERN =
            "^[\\w-\\+]+(\\.[\\w-\\+]+)?@[\\w-\\+]+(\\.[\\w-\\+]+)?$";

    public boolean checkId(String strId) {
        try {
            Long id = Long.parseLong(strId);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkName(String strName) {
        return !strName.isEmpty() && strName.length() <= 255;
    }

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_PATTERN) && !email.isEmpty() && email.length() <= 255;
    }

    public boolean checkAge(int age) {
            return age >= 1 && age <= 125;
    }
}
