package lightning.cyborg.VOIP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import lightning.cyborg.R;
import lightning.cyborg.activity.SetSipUserInfo;
import lightning.cyborg.activity.UserHomepage;


/**
 * Created by Amos Madalin Neculau on 28/02/2016.
 */
/*
This class allows the user to Register an account with Antisip.
Antisip is a free SIP provider.
As we wanted to make the application fully free, with an unlimited number of users
and an unlimited numbers of calls and messages, we had to find a free SIP provider.
Antisip is one of those.
We choose them because of their quality, and because of their simpistic  register page.
They will provide SIP services for free. So our client won't need to pay after a number of users
or after a number of minutes/ messages as other Companies charge.
Some of NON FREE companies are: Snitch, CallOnSip or videoBlox.
We wanted to improve the users experience and reduce the costs for our client (make everything free of charge).
Furthermore, if the client will want in the future to extend his business nad to buy his own servers and be a sip provider,
the application allow this as well, by changing some parameters in the CallActivity code. (The FINAL ones)
and this WebView won't be needed anymore.

Also this class is meant to allow the user to make an SIP account inside the app, because we try to improve the user friendliness.
 */

/* This class allows the user to Register an account with Antisip. to be used for voip */
public class SipAccountRegistration extends Activity{

    // UI ELEMENTS
    private WebView wv;
    private Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sip_account_registration);

        // Initializig the UI elements
        wv = (WebView) findViewById(R.id.webView);
        doneBtn = (Button) findViewById(R.id.doneBtn);

        //Allow JavaScript in order to have a very simple Captcha, also meant for improving the User friendliness
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //load the URL to the ANTISIP registration page.
        wv.loadUrl("https://www.antisip.com/sip-antisip-com-register");
    }

    /*
    After registration is done we give the intent to another Activity,
    where the users will give the SIP credentials.
    */
    public void doneRegistration(View view){

        Intent i = new Intent(this, SetSipUserInfo.class);
        startActivity(i);
    }

    /*
    Users may have a SIP account and they would like to Skip this from registration.
    They will also be taken to the activity where they will be asked to input their SIP information.
    If they don't have and they don't want to make a SIP account, they also have the chance to Skip that.
    So they will be able just to use messaging. This was one of our client request.
    */
    public void skipSIPRegistration(View view){
        Intent intent = new Intent(this, SetSipUserInfo.class);
        startActivity(intent);
    }

}
