package lightning.cyborg.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import lightning.cyborg.R;
import lightning.cyborg.activity.UserHomepage;

public class AboutUs extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
    }

    public void backToSetting(View view) {
        Intent intent = new Intent(AboutUs.this, UserHomepage.class);
        startActivity(intent);
    }
}
