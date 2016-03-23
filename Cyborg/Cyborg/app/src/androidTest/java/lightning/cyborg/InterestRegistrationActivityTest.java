package lightning.cyborg;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;

import lightning.cyborg.activity.LoginActivity;
import lightning.cyborg.activity.interestsRegistration;

/**
 * Created by Name on 22/03/2016.
 */

/**
 * Team CyborgLightning 2016 - King's College London - Project Run
 * InterestRegistrationActivityTest implements the Instrumental testing for the Interest Activity UI Components.
 * Test Focus
 * @author Simeon
 */

public class InterestRegistrationActivityTest extends ActivityInstrumentationTestCase2<interestsRegistration> {

    interestsRegistration interestsRegistrationObject; //tests Activity

    //constructor - test file
    public InterestRegistrationActivityTest(){
        super(interestsRegistration.class);
    }

    //not null test
    public void testActivityExists(){
        interestsRegistrationObject = getActivity();
        assertNotNull(interestsRegistrationObject);
    }

    //Request focus on Input Password TextField
    public void testEditText_inputPassword(){

        interestsRegistrationObject = getActivity();
        final EditText searchInterests = (EditText)interestsRegistrationObject.findViewById(R.id.searchInterests);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                searchInterests.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_DEL);
        getInstrumentation().sendStringSync("100");
    }

}
