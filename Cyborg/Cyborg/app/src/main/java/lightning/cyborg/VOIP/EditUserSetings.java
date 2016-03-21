package lightning.cyborg.VOIP;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import lightning.cyborg.R;


/**
 * Created by Amos Madalin Neculau on 25/02/2016.
 */

/*
    EditUserSettings allows user to edit his/her SIP account credentials;
 */
public class EditUserSetings extends Activity{

    //UI References
    /*
    user refers to SIP username
     */
    protected TextView user;
    /*
    user refers to SIP password
     */
    protected TextView password;
    //the server will be by default sip.antisip.com
    protected TextView server;
    /*
    the button Done will set the user's credentials
     */
    protected Button done;
    /*
    the button back will allow user to exit the activity without saving the new credentials
     */
    protected Button back;

    //class that parse the info with other classes
    /*
    UserInformation is a helper class;
     It gets anything entered here, and parse to other classes such as: .activity.CallActivity
     its' constructor is (String username, String password, String server)
     */
    public UserInformation ui;

    //Helpers
    //This are String helpers
    //Helps us keep the code clean
    private String usr;
    private String pwd;
    private String svr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_information);

        //Instantiate the variables with UI elements
        user = (TextView) findViewById(R.id.usernameTextField);
        password = (TextView) findViewById(R.id.passwordTextField);
        done = (Button) findViewById(R.id.doneBtn);
        back = (Button) findViewById(R.id.backBtn);
    }

    /*
    EditUserSettings constructor
    Calls the EditInformation method which take the info from UI elements and create a new UserInformation object
     */
    public EditUserSetings(){
        EditInformation();
    }


    /*
     EditInformation method which take the info from UI elements and create a new UserInformation object
    */
    private void EditInformation(){


        usr = user.getText().toString();
        pwd = password.getText().toString();
        svr = server.getText().toString();

        ui = new UserInformation(usr,pwd,svr);
    }

    /*
    returns the object ui.
    helper method
     */
    public UserInformation getUserInformation(){
        return ui;
    }
}
