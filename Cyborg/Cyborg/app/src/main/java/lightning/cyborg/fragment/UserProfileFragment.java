package lightning.cyborg.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lightning.cyborg.R;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.avator.Avator_Logo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lewis on 21/02/2016.
 */
public class UserProfileFragment extends Fragment {


    public static ImageView  imageview;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter adapter;
    private ListView listview;
    private int avator_id;
    private TextView tvFirstandLast;
    private TextView tvlocation;
    private TextView tvbio;
    private int image_id;
    private EditText etInterest;
    private Button addInterestButt;
    private Button delInterestButt;




    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viewroot = inflater.inflate(R.layout.user_profile_fragment, container, false);

        //avator Changing
        imageview = (ImageView) viewroot.findViewById(R.id.profile_image);

        //TODO on click listener for this is broken
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

                alertDialogBuilder.setPositiveButton("Change Avator", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent =  new Intent(getActivity(), Avator_Logo.class);
                        startActivityForResult(intent, 1);


                    }
                });

                alertDialogBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();



            }
        });

        // creating lists and arrayadapter


        /**
         * Add item into arraylist
         */

        adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),R.layout.list_black , R.id.list_content, items);


        listview = (ListView) viewroot.findViewById(R.id.listInterest);

        listview.setAdapter(adapter);



        //location, user profile name and last and bio

        tvFirstandLast = (TextView) viewroot.findViewById(R.id.tvfirstandLast);

        tvlocation = (TextView) viewroot.findViewById(R.id.tvLocation);
        tvbio = (TextView) viewroot.findViewById(R.id.bioUser);






        //creating function to add more items into the interest

        etInterest = (EditText) viewroot.findViewById(R.id.etAddText);
        addInterestButt = (Button) viewroot.findViewById(R.id.addInterestB);

        //insert values into database... for interest of users
        addInterestButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = etInterest.getText().toString().replaceAll("\\s","").toLowerCase();

                if(temp.length() > 0){
                    try {
                        addInterest(temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Interest cannot be blank", Toast.LENGTH_LONG).show();
                }
            }
        });

        delInterestButt = (Button) viewroot.findViewById(R.id.delInterestB);
        delInterestButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = etInterest.getText().toString().replaceAll("\\s","").toLowerCase();

                if(temp.length() > 0){
                    try {
                        deleteInterests(temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Interest cannot be blank", Toast.LENGTH_LONG).show();
                }
            }
        });

        try {
            loadInterests();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Error loading interests", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return viewroot;
    }

    private void addInterest(final String interests) throws JSONException {
        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userID", MyApplication.getInstance().getPrefManager().getUser().getId());
        params.put("interests", interests);

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.ADD_INTERESTS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");

                            if(success){
                                for(String s : interests.split(",")){
                                    if (!items.contains(s)) {
                                        items.add(s);
                                    }
                                }
                            }

                            etInterest.setText("");
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", EndPoints.ADD_INTERESTS);
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

    private void loadInterests() throws JSONException {
        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userID", MyApplication.getInstance().getPrefManager().getUser().getId());

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_INTERESTS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");
                            JSONArray interests = jsonResponse.getJSONArray("interests");

                            if(success){
                                for (int i = 0; i < interests.length(); i++){
                                    if (!items.contains(interests.getString(i))) {
                                        items.add(interests.getString(i));
                                    }
                                }
                            }
                            else{
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            }

                            adapter.notifyDataSetChanged();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", EndPoints.GET_INTERESTS);
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

    private void deleteInterests(final String interests) throws JSONException {
        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userID", MyApplication.getInstance().getPrefManager().getUser().getId());
        params.put("interests", interests);

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.DEL_INTERESTS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");

                            if(success){
                                for(String s : interests.split(",")){
                                   items.remove(s);
                                }
                            }

                            etInterest.setText("");
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", EndPoints.ADD_INTERESTS);
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

    //creating avator background when user registers....

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Bitmap b = BitmapFactory.decodeByteArray(data.getByteArrayExtra("Bitmap"), 0, data.getByteArrayExtra("Bitmap").length);
                avator_id = data.getExtras().getInt("imageID");
                System.out.println(avator_id );
                imageview.setImageBitmap(b);
            }

        }
    }
}
