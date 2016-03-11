package lightning.cyborg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import lightning.cyborg.app.MyApplication;

public class RegistrationActivity extends AppCompatActivity {

    //Info for register
    EditText emailET;
    Button btnNext;
    EditText emailConfirmET;
    Button loginPage;
    EditText nameET;
    EditText passwordET;
    EditText ConfirmpasswordET;
    EditText dobET;
    boolean isMale;
    boolean isActivated;
    RadioButton maleRadioRegister;
    RadioButton femaleRadioRegister;
    RadioGroup radioGroup;
    int toAddGender;
    int toAddActivated;
    int age;


    JSONObject response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        //USER INPUT
        emailET = (EditText) findViewById(R.id.txtEmail);
        emailConfirmET = (EditText) findViewById(R.id.txtConfirmEmail);
        nameET = (EditText) findViewById(R.id.txtName);

        passwordET = (EditText) findViewById(R.id.txtPassword);
        ConfirmpasswordET = (EditText) findViewById(R.id.txtConfirmPassword);
        dobET = (EditText) findViewById(R.id.txtdob);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupRegister);
        maleRadioRegister = (RadioButton) findViewById(R.id.rbMale);
        femaleRadioRegister = (RadioButton) findViewById(R.id.rbFemal);

        //help for the insertion
        if(maleRadioRegister.isChecked())
            isMale = true;
        if(femaleRadioRegister.isChecked())
            isMale = false;

        if(isMale) {
            toAddGender = 1;
        }
        else {
            toAddGender = 0;
        }

        isActivated = false;
        toAddActivated = 0;

        //DB

        if(toAddActivated == 0 ){
            //intent fo to another screen
            //insert the code send by email
        }
        else{
            //insertData();
            //intent to main menu
        }


        //back to Login Page...

        loginPage = (Button) findViewById(R.id.btnLoginPage);
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLoginPage(v);
            }
        });



        //vaildating data before.. inserting data into database....

        btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validPassword(passwordET.getText().toString(), ConfirmpasswordET.getText().toString());
                validName(nameET.getText().toString());



                if(emailET.getText().toString().equals(emailConfirmET.getText().toString())) {
                    if( validEmail(emailET.getText().toString()) == false){

                        emailET.setError("not valid");

                    } else{

                        System.out.println("Correct");

                    }
                } else {

                    emailET.setError("not valid");
                    emailConfirmET.setError("not valid");

                }


                //goToInterestsPage();
                insertData(v);
                backToLoginPage(v);
                //sendPassword(v);
                try {
                    validateDOB(dobET.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }



    public void insertData(View view){
        final String url = "http://nashdomain.esy.es/users_insert.php";

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", emailET.getText().toString());
        params.put("validName", nameET.getText().toString());
        params.put("password", passwordET.getText().toString());
        params.put("dob", dobET.getText().toString());
        params.put("gender", "Test");

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
        params.put("email", emailET.getText().toString());

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
        params.put("emailA", emailET.getText().toString());
        params.put("emailB", nameET.getText().toString());

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
        params.put("email", emailET.getText().toString());

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

    /*
    Intent for login
     */
    public void changeToMainScreen(View view){
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
        if (password.toString().equals(comfirmPassword.toString()) && (!password.equals("") || !comfirmPassword.equals(""))) {
            return true;
        }
        passwordET.setError("Please Enter same password");
        return false;
    }
    public boolean validName(String x){

        if(x.equals("")){
            nameET.setError("Please Enter Name");
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

            dobET.setError("Please Enter Age Above 18..");
            System.out.println("Above 18: " + age);
            return false;


        }

    }




}
