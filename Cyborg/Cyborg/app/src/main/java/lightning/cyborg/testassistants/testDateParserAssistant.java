package lightning.cyborg.testassistants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Name on 19/03/2016.
 */
public class testDateParserAssistant {
    public testDateParserAssistant(){

    }

    public boolean testDateParser(String date) throws ParseException {
        int age;
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date dateofBirth = df.parse(date.toString());
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateofBirth);

        Calendar todayDate = Calendar.getInstance();
        age = todayDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (todayDate.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        if(age >= 18){
            System.out.println("Above 18: " + age);
            return true;
        } else {


            System.out.println("Above 18: " + age);
            return false;


        }

    }
}
