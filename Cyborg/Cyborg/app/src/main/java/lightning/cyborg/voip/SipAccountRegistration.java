package lightning.cyborg.voip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import lightning.cyborg.R;
import lightning.cyborg.activity.UserHomepage;


/**
 * Created by Amos Madalin Neculau on 28/02/2016.
 */
public class SipAccountRegistration extends Activity{

    private WebView wv;
    private Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sip_account_registration);

        wv = (WebView) findViewById(R.id.webView);
        doneBtn = (Button) findViewById(R.id.doneBtn);

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wv.loadUrl("https://www.antisip.com/sip-antisip-com-register");
    }

    public void doneRegistration(View view){

        Intent i = new Intent(this, UserHomepage.class);
        startActivity(i);
    }

}
