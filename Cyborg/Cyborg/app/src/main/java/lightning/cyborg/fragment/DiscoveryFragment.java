package lightning.cyborg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.app.MyApplication;

public class DiscoveryFragment extends Fragment {

    private View inflatedview;
    private EditText search;
    private ListView friendlist;
    private ArrayAdapter adapter;
    private Button searchButton;
    private String[] matchedUsers;
    private String[][] matchedUserInfo;

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

        final String[] lists = {"Ahad","lewis","tom","Nashwan","amos","nishat"};

        //int [] image= {R.drawable.men1,R.drawable.men1,R.drawable.men1,R.drawable.men1,R.drawable.men1,R.drawable.men1};

        friendlist = (ListView) inflatedview.findViewById(R.id.lvFriends);
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

        adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, lists);
        friendlist.setAdapter(adapter);

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

                            populateDiscovery(0, 5);

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

    private void populateDiscovery(int from, int to){
        final String url = "http://nashdomain.esy.es/users_get.php";

        if (from < 0){
            return;
        }
        if (to > matchedUsers.length){
            to = matchedUsers.length;
        }

        for(int i = from; i < to; i++){
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
    }

    //@Override
    //public void onDestroy() {
        //super.onDestroy();
        //search.removeTextChangedListener();
    ///
    //}
}
