package lightning.cyborg.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gc.materialdesign.views.CheckBox;

import lightning.cyborg.R;


public class Communication extends Activity {


    CheckBox disableCalling;
    CheckBox disableLocation;
    TextView callingTV;
    TextView locationTV;



	int backgroundColor = Color.parseColor("#1E88E5");

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);


        disableCalling = (CheckBox) findViewById(R.id.callingRadio);
        disableLocation = (CheckBox)findViewById(R.id.disableLocationCheckBox);

        callingTV = (TextView) findViewById(R.id.tvCalling);
        locationTV = (TextView) findViewById(R.id.tvDisableLocation);


        disableCalling.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(CheckBox view, boolean check) {

                if (view.isCheck()) {

                    callingTV.setText("Call Disabled");

                } else{
                    callingTV.setText("Enable Call");

                }



            }
        });



        disableLocation.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(CheckBox view, boolean check) {

                if (view.isCheck()) {

                    locationTV.setText("Location Disabled");

                } else{
                    locationTV.setText("Enable Location");

                }



            }
        });





    }


    public void backToSetting(View view){

        Intent intent = new Intent(Communication.this, Setting.class);
        startActivity(intent);



    }


    

}
