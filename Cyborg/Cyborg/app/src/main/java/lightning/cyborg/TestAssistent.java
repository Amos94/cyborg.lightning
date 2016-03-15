package lightning.cyborg;

import junit.framework.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Name on 14/03/2016.
 */
public class TestAssistent {
    public TestAssistent(){}
    static int age;

    public static boolean Method(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date dateofBirth = df.parse(date.toString());
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateofBirth);

        Calendar todayDate = Calendar.getInstance();
        age = todayDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (todayDate.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        if (age >= 18) {
            System.out.println("Above 18: " + age);
            return true;
        } else {

           // dobET.setError("Please Enter Age Above 18..");
            System.out.println("Above 18: " + age);
            return false;
        }


    }

    public static boolean Method2(String email){
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

    public static boolean Method3(String x){

        if(x.equals("")){
           // nameET.setError("Please Enter Name");
            return false;
        }

        return true;
    }

    public static boolean Method4ValidPassword(String password, String confirmPassword) {
        if (password.toString().equals(confirmPassword.toString()) && (!password.equals("") || !confirmPassword.equals(""))) {
            return true;
        }
      //  passwordET.setError("Please Enter same password");
        return false;
    }
}
