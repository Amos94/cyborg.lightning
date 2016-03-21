package lightning.cyborg.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import lightning.cyborg.R;

import java.text.ParseException;

import lightning.cyborg.VOIP.IncomingCallReceiver;
import lightning.cyborg.VOIP.UserInformation;
import lightning.cyborg.VOIP.SipSettings;

/**
 * Created by Amos Madalin Neculau on 25/02/2016.
 *
 */

public class CallActivity extends AppCompatActivity {


    public String sipAddress = null;

    //Provides APIs for SIP tasks, such as initiating SIP connections, and provides access to related SIP services.
    public SipManager manager = null;

    //Defines a SIP profile, including a SIP account, domain and server information.
    public SipProfile me = null;

    //	Handles an Internet audio call over SIP.
    public SipAudioCall call = null;

    //Handles the receiving of a call
    public IncomingCallReceiver callReceiver;

    //Used to determine the conversation length
    public Chronometer chronometer;

    //variables for handle cases
    private static final int CALL_ADDRESS = 1;
    private static final int SET_AUTH_INFO = 2;
    private static final int UPDATE_SETTINGS_DIALOG = 3;
    private static final int HANG_UP = 4;

    //UI elements
    private Button hangUp;
    private ToggleButton mute;
    private ToggleButton speaker;
    private Button makeNewCall;

    /*
    UserInformation instance. Helps to keep the code clean.
    Also this will represent the caller.
    Info from caller will be parsed to SipProfile me
     */
    private UserInformation caller;

    //username of the callee, in order to instantiate a call
    private String callee;

    /*
    These variables will be parsed using intents to other activities
    in order to handle the call and the receiver,
    and may be used also for database connection.

    intentCallerUsername - caller username
    intentCallerPassword - caller password
    intentCalleeUsername - callee username
     */
    private String intentCallerUsername;
    private String intentCallerPassword;
    private String intentCalleeUsername;

    /*
    In order to call someone you need to provide the SIP server which is final.
    We don't handle the calls between more SIP providers.
     */
    private final String SIP_SERVER = "sip.antisip.com";

    /*
    callee id is PREFIX_CALEE+username+POSTFIXCALEE.

    Needed in order to make a call, this is very optimal because we store just the user SIP username.
     */
    private final String PREFIX_CALLEE = "sip:";
    private final String POSTFIX_CALLEE = "@sip.antisip.com";

    //Helper variable for database and push notification for calls.
    private String message;

    //TAG for all logs information
    private String TAG;

    //Constructor for the call activity. This will get the caller and the callee in order to perform a call.
    public CallActivity(String callerUn, String callerPw, String calleeUn){

        caller = new UserInformation(callerUn, callerPw, SIP_SERVER);
        callee = PREFIX_CALLEE + calleeUn + POSTFIX_CALLEE;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Call");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        /*
        Get this info from other activity.
        Helps for push notification and database updates.
         */
        intentCallerUsername = intent.getStringExtra("callerUsername");
        intentCallerPassword = intent.getStringExtra("callerPassword");
        intentCalleeUsername = intent.getStringExtra("calleeUsername");
        message = intent.getStringExtra("type");

        //Initialization of caller.
        caller = new UserInformation(intentCallerUsername, intentCallerPassword, SIP_SERVER);
        //initialization of callee
        callee = PREFIX_CALLEE + intentCalleeUsername + POSTFIX_CALLEE;

        //UI Elements which perform different actions

        /*
        hangUp - Terminates a call
         */
        hangUp = (Button) findViewById(R.id.HangUpBtn);

        /*
        speaker - handles the activation and deactivation of speaker mode
         */
        speaker = (ToggleButton) findViewById(R.id.speakerBtn);

        /*
        The user can mute his/ her microphone.
         */
        mute = (ToggleButton) findViewById(R.id.muteBtn);

        /*
       Instantiate new call.
         */
        makeNewCall = (Button) findViewById(R.id.initiateCall);


        // ToggleButton pushToTalkButton = (ToggleButton) findViewById(R.id.pushToTalk);
        //pushToTalkButton.setOnTouchListener(this);


        // Set up the intent filter.  This will be used to fire an
        // IncomingCallReceiver when someone calls the SIP address used by this
        // application.
        IntentFilter filter = new IntentFilter();

        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingCallReceiver();
        this.registerReceiver(callReceiver, filter);

        //initialize the chronometer.
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        /*
        Listeners for speaker and mute UI toggle Buttons
        Allow the user to perform the mentioned actions in-call.
         */
        speakerListener();
        muteListener();


        //Initialize the Manager
        initializeManager();
        //Initialize the User Local Profile
        initializeLocalProfile();

        //Handles the make and receive calls.
      if(message.equals("makeCall")){
          Log.d("CallActivity", "inside make call if statment");
          initiateCall();
      }
        else if(message.equals("waitCall")){
          Log.d("CallActivity", "inside make call else statment");
      }


        //Initialization of TAG with the current class name for debug purposes
        TAG = ChatRoomActivity.class.getSimpleName();
    }

    //Empty constructor
    public CallActivity() {
        //
    }

    //Handles the receiver.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initiateCall();
    }

    @Override
    public void onStart() {
        super.onStart();
        // When we get back from the preference setting Activity, assume
        // settings have changed, and re-login with new auth info.
        initializeManager();
    }


    //Cleans everything after it.
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.close();
        }

        closeLocalProfile();

        if (callReceiver != null) {
            this.unregisterReceiver(callReceiver);
        }
    }

    //initialization of the manager.
    public void initializeManager() {
        if(manager == null) {
            manager = SipManager.newInstance(this);
        }
        Log.d("Reached here", "initializeManager()");
        initializeLocalProfile();
        Log.d("Reached here", "initializeManager() fin");
    }

    /**
     * Logs you into your SIP provider, registering this device as the location to
     * send SIP calls to for your SIP address.
     */
    public void initializeLocalProfile() {


        //Closes another profile, if needed
        if (me != null) {
            closeLocalProfile();
        }

        //SharedPreferences.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String username = prefs.getString("namePref", "");
        String domain = prefs.getString("domainPref", "");
        String password = prefs.getString("passPref", "");



        try {
            //For debug purposes
            Log.d("caller username: ", caller.getUsername());
            Log.d("caller password: ", caller.getPassword());
            Log.d("caller server: ", caller.getServer());

            /*
            Builds a new Profile. Take all information needed to creates a new Profile.
             */
            SipProfile.Builder builder = new SipProfile.Builder(caller.getUsername(), caller.getServer());
            builder.setPassword(caller.getPassword());
            me = builder.build();

            //Handles an incoming call
            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);


            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.

            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Registration failed.  Please check settings.");
                }
            });
        } catch (ParseException pe) {
            updateStatus("Connection Error.");
        } catch (SipException se) {
            updateStatus("Connection error.");
        }
    }

    /**
     * Closes out your local profile, freeing associated objects into memory
     * and unregistering your device from the server.
     */
    @SuppressLint("LongLogTag")
    public void closeLocalProfile() {
        if (manager == null) {
            return;
        }
        try {
            if (me != null) {
                manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("onDestroy", "Failed to close local profile.", ee);
        }
    }

    /**
     * Make an outgoing call.
     */
    @SuppressLint("LongLogTag")
    public void initiateCall() {

        updateStatus(sipAddress);

        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.

                /*
                Establish a call, update the status and starts the audio and chronometer
                 */
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    Log.d("AAAAAAA", "Beginning of call established");
                    call.startAudio();

                    updateStatus(call);

                    chronometer.start();
                    Log.d("AAAAAAA", "End of call established");
                }

                /*
                Ends a call, update the status and stops chronometer
                 */
                @Override
                public void onCallEnded(SipAudioCall call) {
                    Log.d("AAAAAAA", "Beginning of call ended");
                    updateStatus("Ready.");
                    chronometer.stop();
                    Log.d("AAAAAAA", "End of call");
                }

                //Handles the call on Held
                @Override
                public void onCallHeld(SipAudioCall call){
                    Log.d("AAAAAAA", "Beginning of call held");
                    chronometer.stop();
                    call.toggleMute();
                    Log.d("AAAAAAA", "End of call held");
                }

                /*
                Handles the when the other user is Bussy.(the callee rejected the call)
                 */
                @Override
                public void onCallBusy(SipAudioCall call){
                    try {
                        Log.d("AAAAAAA", "Beginning of call busy");
                        call.endCall();
                        Log.d("AAAAAAA", "End of call busy");
                    } catch (SipException e) {
                        e.printStackTrace();
                    }
                }
            };

            //Instantiate the call, via calling the makeAudioCall method with the manager
            Log.i(TAG, "Before the call");
            call = manager.makeAudioCall(me.getUriString(), callee, listener, 30);
            Log.i(TAG, "After the call");

        }
        catch (Exception e) {
            Toast.makeText(CallActivity.this, "Couldn't make the call.", Toast.LENGTH_SHORT).show();
            Log.i("InitiateCall", "Error when trying to close manager.", e);
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
                    Log.i("InitiateCall",
                            "Error when trying to close manager.", ee);
                    ee.printStackTrace();
                }
            }
            /*
            Allows the user handle just one call
             */
            if (call != null) {
                call.close();
            }
        }
    }

    //Ends the call. Useful method
    public void endCall(View view){
        try {
            call.endCall();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    //Listener that handles the user's mic mute/unmute
    public void muteListener(){


        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    call.toggleMute();
                } else {
                    // The toggle is disabled
                    call.toggleMute();
                }
            }
        });

    }

    //Listener that handles the user's speaker ON/OFF
    public void speakerListener(){

        speaker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    call.setSpeakerMode(true);
                } else {
                    // The toggle is disabled
                    call.setSpeakerMode(false);
                }
            }
        });

    }

    public void makeCall(View view){
        initiateCall();
    }

    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
               /* TextView labelView = (TextView) findViewById(R.id.sipLabel);
                labelView.setText(status);*/
            }
        });
    }

    /**
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    public void updateStatus(SipAudioCall call) {
        String useName = call.getPeerProfile().getDisplayName();
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }

    //Updates the sip settings
    public void updatePreferences() {
        Intent settingsActivity = new Intent(getBaseContext(),
                SipSettings.class);
        startActivity(settingsActivity);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (call == null) {
            return false;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN && call != null && call.isMuted()) {
            call.toggleMute();
        } else if (event.getAction() == MotionEvent.ACTION_UP && !call.isMuted()) {
            call.toggleMute();
        }
        return false;
    }
}
