package lightning.cyborg.helper;

import android.app.Dialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.model.User;

/**
 * Created by nashwan on 21/03/2016.
 */
public class ViewProfileDialog extends Dialog {

    private User user;
    private ImageView avatar;
    private ListView interests;
    private TextView name;

    public ViewProfileDialog(Context context, User user) {
        super(context);
        this.user =user;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_profile);
        name = (TextView) findViewById(R.id.vp_UserName);
        avatar=(ImageView) findViewById(R.id.vp_Avatar);
        interests =(ListView) findViewById(R.id.vp_InterestList);
        name.setText(user.getName());
        try {
            populateDiscovery(user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateDiscovery(String userID) throws JSONException {

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userID);

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_USER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // check for error flag
                            if (jsonResponse.getString("error").equals("false")) {
                             Log.d("viewprofileDialog" ,"inside if");
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", "volley error");
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
}
