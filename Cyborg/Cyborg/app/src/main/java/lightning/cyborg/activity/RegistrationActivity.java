package lightning.cyborg.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lightning.cyborg.R;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.app.VolleyQueue;
import lightning.cyborg.avator.Avator_Logo;

public class RegistrationActivity extends AppCompatActivity {

    //Info for register
    EditText emailET;
    ImageView avatorIcon;
    int avator_id;

    EditText emailConfirmET;
    Button chooseAvator;
    EditText nameET;
    EditText passwordET;
    EditText dobET;
    Spinner eduSpin;
    RadioButton maleRadioRegister;
    RadioButton femaleRadioRegister;
    RadioGroup radioGroup;
    ImageView backButton, forwardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        avator_id = 5;

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_register_main_menu);

        //USER INPUT
        backButton = (ImageView) findViewById(R.id.registerBackButton);
        forwardButton = (ImageView) findViewById(R.id.nextPageButton);
        emailET = (EditText) findViewById(R.id.txtEmail);
        emailConfirmET = (EditText) findViewById(R.id.txtConfirmEmail);
        nameET = (EditText) findViewById(R.id.txtName);

        passwordET = (EditText) findViewById(R.id.txtPassword);
        dobET = (EditText) findViewById(R.id.txtdob);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupRegister);
        maleRadioRegister = (RadioButton) findViewById(R.id.rbMale);
        avatorIcon = (ImageView) findViewById(R.id.avatorIcon);

        femaleRadioRegister = (RadioButton) findViewById(R.id.rbFemal);
        maleRadioRegister.setChecked(true);

        ArrayAdapter<CharSequence> eduAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.education_array_not_any, android.R.layout.simple_spinner_item);
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eduSpin = (Spinner) findViewById(R.id.eduSpin);
        eduSpin.setAdapter(eduAdapter);

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertUser(v);
                Log.d("Updating User info", "OnClick is called");
                Toast.makeText(v.getContext(),
                        "Registering...",
                        Toast.LENGTH_LONG).show();

                goToMainMenuFromRegistration(v);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                backToLoginPage(v);

            }


        });
    }




    //TODO Make a button to connect this
    public void insertUser(View view){
        String fname = nameET.getText().toString().substring(0,nameET.getText().toString().indexOf(' '));
        String lname = nameET.getText().toString().substring(fname.length() + 1);
        Log.d(fname, lname);

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();

        if (emailET.getText().toString().equals(emailConfirmET.getText().toString())){
            if (isValidEmail(emailET.getText().toString())){
                params.put("email", emailET.getText().toString());
            }
            else{
                emailET.setError("Please enter a valid email");
            }
        }
        else{
            emailET.setError("Please make sure emails match");
        }

        if(isValidName(fname)){
            params.put("fname", fname);
        }
        else{
            nameET.setError("Please enter a valid name");
        }

        if(isValidName(lname)){
            params.put("lname", lname);
        }
        else{
            nameET.setError("Please enter a valid name");
        }

        if(isValidPassword(passwordET.getText().toString())){
            params.put("password", passwordET.getText().toString());
        }
        else{
            passwordET.setError("Please enter a valid password");
        }

        if(isValidateDOB(dobET.getText().toString())){
            params.put("dob", dobET.getText().toString().replaceAll("-", ""));
        }
        else{
            dobET.setError("Please a valid date yyyy-mm-dd");
        }

        if(maleRadioRegister.isChecked()){
            params.put("gender", "M");
        }
        else{
            params.put("gender", "F");
        }

        params.put("avatar", Integer.toString(avator_id));

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.USERS_ADD,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getString("success").equals("1");
                            Log.d("Success", String.valueOf(success));

                            String message = jsonResponse.getString("message");
                            Log.d("Message is", message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", EndPoints.USERS_ADD);
            }
        }
        ){
            //Parameters inserted
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };
        //put the request in the static queue
        MyApplication.getInstance().addToRequestQueue(request);
    }

    private void goToMainMenuFromRegistration(View view){
        Intent intent = new Intent(this, UserHomepage.class);
        startActivity(intent);
    }

    //Intent for interests registration
    private void goToInterestsPage() {
        Intent intent = new Intent(this, interestsRegistration.class);
        startActivity(intent);
    }

    /*
    Intent for login
     */
    private void changeToMainScreen(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //go back to login Page
    private void backToLoginPage(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    //validating users details
    public boolean isValidPassword(String password) {
        if (password.length() > 5) {
            return true;
        }
        return false;
    }
    public boolean isValidName(String x){

        if(x.equals("")){
            nameET.setError("Please Enter Name");
            return false;
        }

        return true;
    }
    public boolean isValidEmail(String email){
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
    public boolean isValidateDOB(String date) {
        if (date.length() == 10){
            String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            int curDate = Integer.parseInt(strDate);
            int intDate = 0;
            try {
                intDate = Integer.parseInt(date.replace("-", ""));
                if(1110000 > (curDate - intDate) && (curDate - intDate) >= 180000){
                    return true;
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.user_info_menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }


    //creating avator background when user registers....

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Bitmap b = BitmapFactory.decodeByteArray(data.getByteArrayExtra("Bitmap"), 0, data.getByteArrayExtra("Bitmap").length);
                avator_id = data.getExtras().getInt("imageID");
                Log.d("Avatar id", avator_id + "");
                avatorIcon.setImageBitmap(b);
            }
        }
    }

    public void chooseAvator(View view){
        chooseAvator = (Button) findViewById(R.id.btnSelectAvator);
        chooseAvator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(RegistrationActivity.this, Avator_Logo.class);
                startActivityForResult(intent,1);
            }

        });

    }
}
