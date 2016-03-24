package lightning.cyborg.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.adapter.BlockedListAdapter;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.helper.SimpleDividerItemDecoration;
import lightning.cyborg.helper.ViewProfileDialog;
import lightning.cyborg.model.User;

public class ViewBlockedUsers extends AppCompatActivity {

    private String TAG = ViewBlockedUsers.class.getSimpleName();
    private ArrayList<User> blockedUserArrayList;
   private BlockedListAdapter blockedListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blocked_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blocked Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        blockedUserArrayList = new ArrayList<>();
         blockedListAdapter = new BlockedListAdapter(this, blockedUserArrayList);
        recyclerView = (RecyclerView) findViewById(R.id.listBlocked);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(blockedListAdapter);
        recyclerView.addOnItemTouchListener(new BlockedListAdapter.RecyclerTouchListener(this, recyclerView, new BlockedListAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                ViewProfileDialog dialog = new ViewProfileDialog(ViewBlockedUsers.this,blockedUserArrayList.get(position));
                dialog.show();
            }
        }));
            getBlockedList();
        }

    public void getBlockedList(){

        final Map<String, String> params = new HashMap<>();
        params.put("user_id", MyApplication.getInstance().getPrefManager().getUser().getId());



        StringRequest strReq = new StringRequest(Request.Method.POST,
                EndPoints.BLOCK_LIST, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error
                    if (obj.getBoolean("error") == false) {

                        blockedUserArrayList.clear();

                        JSONArray userArray = obj.getJSONArray("users");

                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userObj = (JSONObject) userArray.get(i);
                            User user = new User();
                            user.setId(userObj.getString("user_id"));
                            user.setName(userObj.getString("fname"));
                            user.setAvatar(userObj.getString("avatar"));
                            blockedUserArrayList.add(user);
                            Log.d("list info", blockedUserArrayList.get(i).getId()+"id"+blockedUserArrayList.get(i).getName()+"is name");
                        }

                        blockedListAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

    }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            //Parameters inserted
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };


        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    public void getUser(String userID){

        final Map<String, String> params = new HashMap<>();
        params.put("user_id", userID);



        StringRequest strReq = new StringRequest(Request.Method.POST,
                EndPoints.BLOCK_LIST, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error
                    if (obj.getBoolean("error") == false) {

                        JSONArray userArray = obj.getJSONArray("users");

                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userObj = (JSONObject) userArray.get(i);
                            User user = new User();
                            user.setId(userObj.getString("user_id"));
                            user.setName(userObj.getString("fname"));

                        }

                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            //Parameters inserted
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };


        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }






}
