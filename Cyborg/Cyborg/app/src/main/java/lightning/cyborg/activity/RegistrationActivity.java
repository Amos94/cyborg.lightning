package lightning.cyborg.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import lightning.cyborg.VOIP.SipAccountRegistration;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.app.VolleyQueue;

public class RegistrationActivity extends AppCompatActivity {

    //Info for register
    EditText emailEditText;
    ImageView btnNext;
    EditText emailConfirmEditText;
    Button loginPage;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    EditText dobEditText;
    boolean isMale;
    boolean isActivated;
    RadioButton maleRadioRegister;
    RadioButton femaleRadioRegister;
    RadioGroup radioGroup;
    int toAddGender;
    int toAddActivated;
    int age;
    ClipData.Item test;

    CheckBox sipAccountCB;

    int numPassChar;
    char[] passChar;


    JSONObject response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_register_main_menu);

        //USER INPUT
        emailEditText = (EditText) findViewById(R.id.txtEmail);
        emailConfirmEditText = (EditText) findViewById(R.id.txtConfirmEmail);
        firstNameEditText = (EditText) findViewById(R.id.textFirstName);
        lastNameEditText = (EditText) findViewById(R.id.textLastName);

        passwordEditText = (EditText) findViewById(R.id.txtPassword);
        confirmPasswordEditText = (EditText) findViewById(R.id.txtConfirmPassword);
        dobEditText = (EditText) findViewById(R.id.txtdob);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupRegister);
        maleRadioRegister = (RadioButton) findViewById(R.id.rbMale);
        femaleRadioRegister = (RadioButton) findViewById(R.id.rbFemal);

        //help for the insertion
        if (maleRadioRegister.isChecked())
            isMale = true;
        if (femaleRadioRegister.isChecked())
            isMale = false;

        if (isMale) {
            toAddGender = 1;
        } else {
            toAddGender = 0;
        }

        isActivated = false;
        toAddActivated = 0;

        numPassChar = 0;


        //DB

        if (toAddActivated == 0) {
            //intent fo to another screen
            //insert the code send by email
        } else {
            //insertData();
            //intent to main menu
        }


        //back to Login Page...

        //loginPage = (Button) findViewById(R.id.btnLoginPage);
        //loginPage.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //backToLoginPage(v);
            //}
        //});

        sipAccountCB = (CheckBox) findViewById(R.id.sipRegBox);

        //vaildating data before.. inserting data into database....

        btnNext = (ImageView) findViewById(R.id.nextPageButton);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               validPassword(passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
               validName(firstNameEditText.getText().toString(), firstNameEditText);
               validName(lastNameEditText.getText().toString(), lastNameEditText);

                if(emailConfirmEditText.getText().toString().equals(emailConfirmEditText.getText().toString())) {
                    if( validEmail(emailEditText.getText().toString()) == false){

                        emailEditText.setError("not valid");

                    } else{

                        insertData(v);
                        System.out.println("Correct");

                    }
                } else {

                    emailEditText.setError("not valid");
                    emailConfirmEditText.setError("not valid");

                }



                try {
                    validateDOB(dobEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                goToMakeSipAccount();

            }
        });


    }

    public void showVoipInfo(View view){

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("VoIP info");

        alertDialog.setMessage("In order to make and receive inApp calls, you have to create an ANTISIP account." +
                "\nIf you want this feature, please make sure you tick the checkbox." +
                "\nIf not, you can skip this for now." +
                "\nHowever, if you change your mind later, you can make an ANTISIP account inApp using the settings.");

        alertDialog.setIcon(R.drawable.info);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    public void insertData(View view){
        final String url = "http://nashdomain.esy.es/users_insert.php";

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", emailEditText.getText().toString());
        params.put("fname", firstNameEditText.getText().toString());
        params.put("lname", lastNameEditText.getText().toString());
        params.put("password", passwordEditText.getText().toString());
        params.put("dob", dobEditText.getText().toString());
        params.put("gender", "Test");
        params.put("avatar", "1");

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, url,
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
                Log.d("VolleyError at url ", url);
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
        VolleyQueue.getInstance(this).addToRequestQueue(request);
    }

    public void sendPassword(View view) {
        final String url = "http://nashdomain.esy.es/get_user.php";

        //parameters to post to php file
        final Map<String, String> params = new HashMap<>();
        params.put("email", emailConfirmEditText.getText().toString());

        //request to get the user from the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, url,
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
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError at url ", url);
            }
        }
        ) {
            //Parameters inserted
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        //put the request in the static queue
        MyApplication.getInstance().addToRequestQueue(request);
    }

    public void insertFriend(View view){
        final String url = "http://nashdomain.esy.es/insert_friend.php";

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("emailA", emailConfirmEditText.getText().toString());
        params.put("emailB", firstNameEditText.getText().toString());

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, url,
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
                Log.d("VolleyError at url ", url);
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

    public void getFriends(View view){
        final String url = "http://nashdomain.esy.es/get_friends.php";

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", emailConfirmEditText.getText().toString());

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getString("success").equals("1");
                            Log.d("Success", String.valueOf(success));

                            String message = jsonResponse.getString("message");
                            Log.d("Message is", message);

                            //clips the brackets off of the php array
                            String friends = jsonResponse.getString("friends");
                            friends = friends.substring(1,friends.length()-1);
                            Log.d("Friends is", friends);

                            //clips the quotation marks off of emails
                            String[] test = friends.split(",");
                            for(int i = 0; i < test.length; i++){
                                test[i] = test[i].substring(1,test[i].length()-1);
                            }
                            Log.d("Test is", test[0]+" "+test[1]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", url);
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

    public void goToMainMenuFromRegistration(View view){
        Intent intent = new Intent(this, UserHomepage.class);
        startActivity(intent);
    }

    //Intent for interests registration
    public void goToInterestsPage() {
        Intent intent = new Intent(this, interestsRegistration.class);
        startActivity(intent);
    }

    public void goToMakeSipAccount(){

        if(sipAccountCB.isChecked()){
            Intent intent = new Intent(this, SipAccountRegistration.class);
            startActivity(intent);
        }
        else{
            goToInterestsPage();
        }

    }

    /*
    Intent for login
     */
    public void changeToMainScreen(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //go back to login Page
    public void backToLoginPage(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    //vaildaing users details
    public boolean validPassword(String password, String comfirmPassword) {

        numPassChar = 0;
        passChar = password.toCharArray();

        numPassChar = passChar.length+1;

        if (password.toString().equals(comfirmPassword.toString()) && (!password.equals("") || !comfirmPassword.equals("")) && numPassChar >=6) {
            return true;
        }
        if(numPassChar <6) {
            Toast.makeText(RegistrationActivity.this, "Password must have more than 6 characters.", Toast.LENGTH_SHORT).show();
            passwordEditText.setError("Password must have more than 6 characters.");
        }
        passwordEditText.setError("Please Enter same password");
        return false;
    }
    public boolean validName(String x, EditText et){

        if(x.equals("")){
            et.setError("Please enter a name");
            return false;
        }

        return true;
    }

    public boolean validEmail(String email){
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



    public boolean validateDOB(String date) throws ParseException {

        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date dateofBirth = df.parse(date.toString());
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateofBirth);

        Calendar todayDate = Calendar.getInstance();
        age = todayDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (todayDate.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        if(age >= 18){
            System.out.println("Above 18: " + age);
            return true;
        } else {

            dobEditText.setError("Please Enter Age Above 18..");
            System.out.println("Above 18: " + age);
            return false;


        }

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

}
