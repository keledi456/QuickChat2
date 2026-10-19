
package quickchat2;

public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellphone;

    // Check that the username contains an underscore
    // and is no more than five characters long.
    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    // Check the password requirements.
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasCapital = true;
            }

            if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Check a South African cellphone number with +27.
    // The +27 prefix is followed by nine digits.
    // Regex reference: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
    public boolean checkCellPhoneNumber(String cellphone) {
        return cellphone != null
                && cellphone.matches("\\+27\\d{9}");
    }

    // Register the user after validating the details.
    public String registerUser(String username,
                               String password,
                               String cellphone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure "
                    + "that your username contains an underscore and is "
                    + "no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure "
                    + "that the password contains at least eight characters, "
                    + "a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellphone)) {
            return "Cell number is incorrectly formatted or does not contain "
                    + "an international code; please correct the number "
                    + "and try again.";
        }

        registeredUsername = username;
        registeredPassword = password;
        registeredCellphone = cellphone;

        return "User registered successfully.";
    }

    // Compare the login details with the registered details.
    public boolean loginUser(String username, String password) {
        return registeredUsername != null
                && registeredPassword != null
                && registeredUsername.equals(username)
                && registeredPassword.equals(password);
    }

    // Return the login status message.
    public String returnLoginStatus(boolean successful) {
        if (successful) {
            return "Welcome to QuickChat.";
        }

        return "Username or password incorrect, please try again.";
    }

    public String getRegisteredUsername() {
        return registeredUsername;
    }

    public String getRegisteredCellphone() {
        return registeredCellphone;
    }
}