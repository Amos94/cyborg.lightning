package lightning.cyborg;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import java.security.Key;

import lightning.cyborg.activity.RegistrationActivity;
/**
 * Team CyborgLightning 2016 - King's College London - Project Run
 * RegistrationTest implements the Instrumental testing for the Registration Activity
 * Tests focus and input
 * @author Simeon
 */
public class RegistrationTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {


RegistrationActivity registrationActivity;//tested Activity

    //constructor for the test class
    public RegistrationTest(){

        super(RegistrationActivity.class);
    }

    //Test Not Null Check
    public void testActivityExists(){
      RegistrationActivity registrationActivity = getActivity();
        assertNotNull(registrationActivity);
    }

    //Request focus on Email Txt Field
    public void testEmailEditText1(){

        registrationActivity = getActivity();
        final EditText emailET = (EditText)registrationActivity.findViewById(R.id.txtEmail);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                emailET.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("100");
    }

    //Request focus on Next Button
    public void testNextButton() {

        registrationActivity = getActivity();
        final Button btnNext = (Button) registrationActivity.findViewById(R.id.btnNext);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                btnNext.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

    }

    //Request focus on ConfirmEmail
    public void testConfirmEmailEditText2() {

        registrationActivity = getActivity();
        final EditText emailConfirmET = (EditText) registrationActivity.findViewById(R.id.txtConfirmEmail);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                emailConfirmET.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("100");
    }

    //Request focus on Login Button
    public void testloginPageButton() {

        registrationActivity = getActivity();
        final Button loginPage = (Button) registrationActivity.findViewById(R.id.btnLoginPage);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                loginPage.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

    }

    //Request focus on Edit Name
    public void testEditTextName1() {

        registrationActivity = getActivity();
        final EditText nameET = (EditText) registrationActivity.findViewById(R.id.txtName);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameET.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("Simeon");
    }



    //Request focus on Edit Password
    public void testEditTextPassword1() {

        registrationActivity = getActivity();
        final EditText passwordET = (EditText) registrationActivity.findViewById(R.id.txtPassword);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                passwordET.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("zazaza");
    }


    //Request focus on Confirm Password
    public void testEditTextConfirmPassword2() {

        registrationActivity = getActivity();
        final EditText ConfirmpasswordET = (EditText) registrationActivity.findViewById(R.id.txtConfirmPassword);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ConfirmpasswordET.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);

    }


    //Request focus on Edit Text Date of Birth
    public void testEditTextDateOfBirth() {

        registrationActivity = getActivity();
        final EditText dobET = (EditText) registrationActivity.findViewById(R.id.txtdob);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                dobET.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();

        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);

    }
}
