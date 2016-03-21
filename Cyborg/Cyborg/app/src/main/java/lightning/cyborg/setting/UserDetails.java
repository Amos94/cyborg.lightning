package lightning.cyborg.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import lightning.cyborg.R;


public class UserDetails extends Activity {

    EditText etchangeName;
    EditText etcurrentPassword;
    EditText etnewPasword;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    Button btnDone;



    int backgroundColor = Color.parseColor("#1E88E5");

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);

        //creating variable names;
        etchangeName = (EditText) findViewById(R.id.etchangeName);
        etcurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etnewPasword = (EditText) findViewById(R.id.etNewPassword);
        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);

        btnDone = (Button) findViewById(R.id.btnDone);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDetails.this,Setting.class);
                startActivity(intent);
            }
        });









    }
    

}
