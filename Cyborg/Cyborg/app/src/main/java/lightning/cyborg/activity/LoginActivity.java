package lightning.cyborg.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.app.AppLocationListener;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.model.User;


public class LoginActivity extends AppCompatActivity {

    private String TAG = LoginActivity.class.getSimpleName();
    private EditText _inputEmail, _inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail;
    private Button btnEnter;
    public final int MY_PERMISSIONS_REQUEST_FINE_LOCATION =10;
    private static int request;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String stringUpdateGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
        /**
         * Check for login session. It user is already logged in
         * redirect him to main activity
         * */
        if (MyApplication.getInstance().getPrefManager().getUser() != null) {
            // startActivity(new Intent(this, MainActivity.class));
            startActivity(new Intent(this,UserHomepage.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _inputEmail = (EditText) findViewById(R.id.txtEmailLogin);
        _inputPassword = (EditText) findViewById(R.id.txtPasswordLogin);
        btnEnter = (Button) findViewById(R.id.btnLogin);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    /*
     Intent for REGISTER
    */
    public void goToRegisterScreen(View view) {
        MyApplication.getappLocationListner().requestLocationUpdates();
        //Intent intent = new Intent(this, RegistrationActivity.class);
        //startActivity(intent);
    }


    /**
     * logging in user. Will make http post request with name, email
     * as parameters
     */
    private void login() {

        final String email = _inputEmail.getText().toString();
        final String password = _inputPassword.getText().toString();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                EndPoints.LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {
                        // user successfully logged in
                        //id, name, email, created_at
                        JSONObject userObj = obj.getJSONObject("user");
                        User user = new User(userObj.getString("id"),
                                userObj.getString("fname"),
                                userObj.getString("email"));

                        // storing user in shared preferences
                        MyApplication.getInstance().getPrefManager().storeUser(user);

                        // start main activity
                        // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        startActivity(new Intent(getApplicationContext(),UserHomepage.class));

                        finish();

                    } else {
                        // login error - simply toast the message
                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    MyApplication.setAppLocationListener(locationManager);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
