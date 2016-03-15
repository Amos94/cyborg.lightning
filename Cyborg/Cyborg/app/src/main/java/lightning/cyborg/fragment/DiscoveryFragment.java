package lightning.cyborg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.app.MyApplication;

public class DiscoveryFragment extends Fragment {

    private View inflatedview;
    private EditText search;
    private ListView matchedList;
    private ArrayAdapter adapter;
    private Button searchButton;
    private String[] matchedUsers;
    private ArrayList matchedUserInfo;

    public DiscoveryFragment() {
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
        this.inflatedview = inflater.inflate(R.layout.discovery_fragment, container, false);

        //int [] image= {R.drawable.men1,R.drawable.men1,R.drawable.men1,R.drawable.men1,R.drawable.men1,R.drawable.men1};

        matchedList = (ListView) inflatedview.findViewById(R.id.listMatched);
        search = (EditText) inflatedview.findViewById(R.id.searchMatches);

//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                adapter.getFilter().filter(s);
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                adapter.getFilter().filter(s);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                adapter.getFilter().filter(s);
//
//            }
//        });

        searchButton = (Button)inflatedview.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover(v);
            }
        });

        return inflatedview;

    }

    private void discover(View v){
        final String url = "http://nashdomain.esy.es/interests_get_matched.php";
        final String filtered = search.getText().toString().replaceAll(", ",",").replaceAll(" ,",",").toLowerCase();
        matchedUserInfo = new ArrayList<JSONObject>();
        matchedUsers = new String[0];

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("interests", filtered);
        params.put("userID", "69");

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            //clips the brackets off of the php array
                            String users = jsonResponse.getString("users").replaceAll("\"","");
                            users = users.substring(1, users.length() - 1);
                            matchedUsers = users.split(",");

                            populateDiscovery(5);

                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");
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

    private void populateDiscovery(int inc) throws JSONException {
        final String url = "http://nashdomain.esy.es/users_get.php";

        if (inc + matchedUserInfo.size() > matchedUsers.length){
            inc = matchedUsers.length - matchedUserInfo.size();
        }

        //TODO  change php to take in array of user ids
        for(int i = matchedUserInfo.size(); i < inc; i++){
            Log.d("looking for user ", matchedUsers[i]);

            //parameters to post to php file
            final Map<String, String> params = new HashMap<String, String>();
            params.put("userID", matchedUsers[i]);

            //request to insert the user into the mysql database using php
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                JSONObject user = jsonResponse.getJSONObject("user");
                                matchedUserInfo.add(user);
                                Log.d("popdis user", user.toString());

                                boolean success = jsonResponse.getString("success").equals("1");
                                Log.d("popdis suc", ""+success);

                                String message = jsonResponse.getString("message");
                                Log.d("popdis mes", message);

                                populateList();
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

    private void populateList() {
        String[] users = new String[matchedUserInfo.size()];

        Log.d("matcheduserinfo size", ""+matchedUserInfo.size());

        for(int i = 0; i < matchedUserInfo.size(); i++){
            try {
                JSONObject user = (JSONObject) matchedUserInfo.get(i);

                users[i] = user.getString("gender") + " - " + user.getString("fname");
                //TODO add level of education later add avatar

                Log.d("popList",users[i]);
            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.d("Json error parsing ", users.toString());
            }
        }

        adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, users);
        matchedList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //@Override
    //public void onDestroy() {
        //super.onDestroy();
        //search.removeTextChangedListener();
    ///
    //}
}
