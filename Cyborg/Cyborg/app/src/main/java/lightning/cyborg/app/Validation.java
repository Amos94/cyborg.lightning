package lightning.cyborg.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public String getValidMessage(String sin){
        return sin.replaceAll("\\\\", "").replaceAll(";", "");
    }

    public String getValidInterest(String sin){
        return getValidMessage(sin).toLowerCase().replaceAll(", ", ",").replaceAll(" ,", ",");
    }

    //validating users details
    public boolean isValidPassword(String password) {
        if (password.length() > 5) {
            return true;
        }
        return false;
    }

    public boolean isValidName(String x){
        if(x.equals("")){
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email){
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

    public boolean isValidateDOB(String date) {
        if (date.length() == 10){
            String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            int curDate = Integer.parseInt(strDate);
            int intDate = 0;
            try {
                intDate = Integer.parseInt(date.replace("-", ""));
                if(1110000 > (curDate - intDate) && (curDate - intDate) >= 180000){
                    return true;
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
