package lightning.cyborg.testassistants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Name on 19/03/2016.
 */
public class testEmailValidationAssistant {

    public testEmailValidationAssistant(){

    }

    public static boolean testPasswordValidationParser(String email){
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
}
