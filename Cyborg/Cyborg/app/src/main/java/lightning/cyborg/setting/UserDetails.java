package lightning.cyborg.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lightning.cyborg.R;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;

/**
 * Created by Amos Madalin Neculau on 28/02/2016.
 */

public class UserDetails extends Activity {

    EditText etchangeName;
    EditText etLastName;
    EditText etLocation;
    Spinner spEducation;
    String [] educationArray;
    ArrayAdapter<String> educationAdapter;
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
        etLastName = (EditText) findViewById(R.id.etLastname);
        etnewPasword = (EditText) findViewById(R.id.etNewPassword);
        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);
        etLocation = (EditText) findViewById(R.id.etLocation);


        //creating array adapter for education List...
        spEducation = (Spinner) findViewById(R.id.spinnerEducation);
        educationArray = getResources().getStringArray(R.array.education_array);
        educationAdapter = new ArrayAdapter<String>(UserDetails.this,R.layout.support_simple_spinner_dropdown_item,educationArray);
        spEducation.setAdapter(educationAdapter);
        spEducation.setSelection(9);


        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Updating user details...", Toast.LENGTH_LONG).show();
                try {
                    btnDone.setEnabled(false);
                    updateProfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("updateProf", "Failed to parse Json");
                }
            }
        });
    }

    private void updateProfile() throws JSONException {
        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userID", MyApplication.getInstance().getPrefManager().getUser().getId());

        if(!etLocation.getText().toString().equals("")) {
            updateLocation();
        }
        if(!etchangeName.getText().toString().replaceAll(", ", ",").replaceAll(" ,", ",").equals("")){
            params.put("fname", etchangeName.getText().toString());
        }
        if(!etLastName.getText().toString().replaceAll(", ", ",").replaceAll(" ,", ",").equals("")){
            params.put("lname", etLastName.getText().toString());
        }
        if(!etnewPasword.getText().toString().replaceAll(", ", ",").replaceAll(" ,", ",").equals("")){
            params.put("password", etnewPasword.getText().toString());
        }
        if(spEducation.getSelectedItemPosition() == 9){
            params.put("edu_level", Integer.toString(spEducation.getSelectedItemPosition()));
        }
        if(maleRadioButton.isChecked()){
            params.put("gender", "M");
        }
        if(femaleRadioButton.isChecked()){
            params.put("gender", "F");
        }

        //TODO add edit location, edit email



        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.UPDATE_USERS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            if (success){
                                backToMain();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                        btnDone.setEnabled(true);
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", EndPoints.UPDATE_USERS);
                btnDone.setEnabled(true);
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

    private void backToMain(){
        finish();
    }

    public void backToSetting(View view){
        finish();
    }

    private void updateLocation() throws JSONException {
        final String url = "https://maps.googleapis.com/maps/api/geocode/json?address=uk%20";
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]*$");
        Matcher matcher = pattern.matcher(etLocation.getText().toString().trim());

        if(!matcher.find()){
            Toast.makeText(getApplicationContext(), "Invalid postcode entered", Toast.LENGTH_SHORT).show();
            return;
        }

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject results = jsonResponse.getJSONArray("results").getJSONObject(0);
                            JSONObject geometry = results.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");

                            String lat = location.getString("lat");
                            Log.d("geoRes", lat.toString());
                            String lng = location.getString("lng");
                            Log.d("geoRes", lng.toString());


                            //parameters to post to php file
                            final Map<String, String> params = new HashMap<String, String>();
                            params.put("userID", MyApplication.getInstance().getPrefManager().getUser().getId());
                            params.put("lat", lat);
                            params.put("lon", lng);

                            StringRequest request = new StringRequest(Request.Method.POST, EndPoints.UPDATE_USERS,
                                    new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                //boolean success = jsonResponse.getString("success").equals("1");
                                                String message = jsonResponse.getString("message");
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                            }
                                            catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.d("JSON failed to parse: ", response);
                                            }
                                        }
                                    }, new Response.ErrorListener(){

                                @Override
                                public void onErrorResponse(VolleyError error){
                                    Log.d("VolleyError at url ", EndPoints.UPDATE_USERS);
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
                        catch (JSONException e) {
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
        );
        //put the request in the static queue
        MyApplication.getInstance().addToRequestQueue(request);

    }

}
