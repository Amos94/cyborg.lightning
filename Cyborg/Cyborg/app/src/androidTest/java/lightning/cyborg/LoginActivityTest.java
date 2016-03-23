package lightning.cyborg;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import lightning.cyborg.activity.LoginActivity;
import lightning.cyborg.activity.RegistrationActivity;
/**
 * Team CyborgLightning 2016 - King's College London - Project Run
 * LoginActivityTest implements the Instrumental testing for the Login Activity UI Components
 * Tests focus and input
 * @author Simeon
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    LoginActivity loginActivity;//tested Activity

    //test class constructor
    public LoginActivityTest(){

        super(LoginActivity.class);
    }


    //Not Null Check
    public void testActivityExists(){
        LoginActivity registrationActivity = getActivity();
        assertNotNull(registrationActivity);
    }


    //Request focus on Input Email Text Field
    public void testEditText_inputEmail(){

        loginActivity = getActivity();
        final EditText _inputEmail = (EditText)loginActivity.findViewById(R.id.txtEmailLogin);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                _inputEmail.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("100");
    }


    //Request focus on Input Password Text Field
    public void testEditText_inputPassword(){

        loginActivity = getActivity();
        final EditText _inputPassword = (EditText)loginActivity.findViewById(R.id.txtPasswordLogin);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                _inputPassword.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("100");
    }

    //Request focus on Enter Button
    public void testButtonEnter(){

        loginActivity = getActivity();
        final Button btnEnter = (Button)loginActivity.findViewById(R.id.btnLogin);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                btnEnter.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
    }
}
