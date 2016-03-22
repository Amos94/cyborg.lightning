package lightning.cyborg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lightning.cyborg.R;
import lightning.cyborg.adapter.ChatRoomsAdapter;
import lightning.cyborg.app.Config;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.fragment.DiscoveryFragment;
import lightning.cyborg.fragment.FriendsListFragment;
import lightning.cyborg.fragment.UserProfileFragment;
import lightning.cyborg.fragment.chatRoomFragment;
import lightning.cyborg.gcm.GcmIntentService;
import lightning.cyborg.gcm.NotificationUtils;
import lightning.cyborg.model.ChatRoom;
import lightning.cyborg.model.Message;
import lightning.cyborg.setting.Setting;

public class UserHomepage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout testerLayout;
    private String TAG = UserHomepage.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ArrayList<ChatRoom> normalChatRoomArrayList;
    private ArrayList<ChatRoom> freindsChatRoomArrayList;
    private ChatRoomsAdapter normalChatAdapter;
    private ChatRoomsAdapter freindChatAdapter;
    private RecyclerView recyclerView;
    private int onChatFragment =0;

    private Button settingsButton;



    private static final long RIPPLE_DURATION = 500;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.root)
    FrameLayout root;
    @InjectView(R.id.content_hamburger)
    View contentHamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        ButterKnife.inject(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        /**
         * Check for login session. If not logged in launch
         * login activity
         * */
        if (MyApplication.getInstance().getPrefManager().getUser() == null) {
            launchLoginActivity();
        }

        normalChatRoomArrayList = new ArrayList<>();
        freindsChatRoomArrayList= new ArrayList<>();

        normalChatAdapter = new ChatRoomsAdapter(this, normalChatRoomArrayList,"n");
        freindChatAdapter =new ChatRoomsAdapter(this,freindsChatRoomArrayList,"f");
        /**
         * Broadcast receiver calls in two scenarios
         * 1. gcm registration is completed
         * 2. when new push notification is received
         * */
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                 //   subscribeToGlobalTopic();

                } else if (intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)) {
                    // gcm registration id is stored in our server's MySQL
                    Log.e(TAG, "GCM registration id is sent to our server");

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    handlePushNotification(intent);
                }
            }
        };

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /**
         * Always check for google play services availability before
         * proceeding further with GCM
         * */
        if (checkPlayServices()){
            registerGCM();
        }
        try {
            Bundle b = getIntent().getExtras();
            String key = b.getString("FragmentNum");
            viewPager.setCurrentItem(Integer.parseInt(key));


        }catch (Exception e){
            Log.d(TAG,"no bundle was attached");
        }

        fetchChatRooms("n");
        fetchChatRooms("f");

    }
    /**
     * Get ChatRooms of a specific type
     * @param type if chatRooms are normal or friends
     */
    private void fetchChatRooms(String type) {
        final String TYPE =type;

        //request to be sent
        StringRequest strReq = new StringRequest(Request.Method.POST,
                EndPoints.CHAT_ROOMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {
                        JSONArray chatRoomsArray = obj.getJSONArray("chat_rooms");

                        //add chat room to the appropriate arrayList
                        for (int i = 0; i < chatRoomsArray.length(); i++) {
                            JSONObject chatRoomsObj = (JSONObject) chatRoomsArray.get(i);

                            //new chatroom object created.
                            ChatRoom cr = new ChatRoom();
                            cr.setId(chatRoomsObj.getString("chat_room_id"));
                            cr.setName(chatRoomsObj.getString("name"));
                            cr.setPermission(chatRoomsObj.getString("permission"));
                            cr.setVisibility(chatRoomsObj.getString("visibility"));
                            cr.setUnreadCount(Integer.parseInt(chatRoomsObj.getString("unread_count")));
                            cr.setTimestamp(chatRoomsObj.getString("created_at"));

                            //if chatRoom doesn't have any messages
                            if(chatRoomsObj.getString("last_message").equals("null")){
                                cr.setLastMessage("");
                            }
                            else{
                                //set message to LatMessage;
                                cr.setLastMessage(chatRoomsObj.getString("last_message"));
                            }

                            if(!cr.getPermission().equals("n")&&cr.getVisibility().equals("y")) {
                                //add to appropriate ArrayList
                                if (TYPE.equals("n")) {
                                    normalChatRoomArrayList.add(cr);
                                } else if (TYPE.equals("f")) {
                                    freindsChatRoomArrayList.add(cr);
                                }
                            }
                        }

                    } else {
                        // error in fetching chat rooms
                        //Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    //Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                //update the GUI
                if(TYPE.equals("n")) {
                    normalChatAdapter.notifyDataSetChanged();
                }
                else if(TYPE.equals("f")){
                    freindChatAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                //params to be sent
                Map<String, String> params = new HashMap<>();
                params.put("type", TYPE);
                params.put("user_id",  MyApplication.getInstance().getPrefManager().getUser().getId());

                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }


    /**
     * Handles new push notification
     */
    private void handlePushNotification(Intent intent) {
        int type = intent.getIntExtra("type", -1);

        // if the push is of chat room message
        if (type == Config.PUSH_TYPE_CHATROOM) {
            Message message = (Message) intent.getSerializableExtra("message");
            String chatRoomId = intent.getStringExtra("chat_room_id");
            String chatType =intent.getStringExtra("chat_type");

            //update chatRoom GUI
            if (message != null && chatRoomId != null) {
                if(chatType.equals("n"))
                    updateRow(chatRoomId,message, normalChatRoomArrayList,normalChatAdapter);
                else {
                    updateRow(chatRoomId, message, freindsChatRoomArrayList, freindChatAdapter);
                }
            }
        }
        else if(type == Config.PUSH_TYPE_CHAT_REQUEST){
            Log.d("AAAAAPUSH_TYPE_CHAT", "recieved it");
            normalChatRoomArrayList.clear();
            fetchChatRooms("n");
        }
    }

    /**
     * Updates the chat list unread count and the last message
     */
    private void updateRow(String chatRoomId, Message message, ArrayList<ChatRoom> normalChatRoomArrayList, ChatRoomsAdapter normalChatAdapter) {
        for (ChatRoom cr :normalChatRoomArrayList) {
            if (cr.getId().equals(chatRoomId)) {
                int index = normalChatRoomArrayList.indexOf(cr);
                cr.setLastMessage(message.getMessage());
                cr.setUnreadCount(cr.getUnreadCount() + 1);
                normalChatRoomArrayList.remove(index);
                normalChatRoomArrayList.add(index, cr);
                break;
            }
        }
        normalChatAdapter.notifyDataSetChanged();
    }


    /**
     * Go to LoginActivity
     */
    private void launchLoginActivity() {
        Intent intent = new Intent(UserHomepage.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Go to the chat room
     * @param chatRoomid  the id of the chat room
     * @param chatRoomName the name of the chat room
     * @param type  the type of chatroom e.g freinds or normal
     */
    public void chatRoomActivityIntent(String chatRoomid,String chatRoomName, String type,String permission) {
        Intent intent = new Intent(UserHomepage.this, ChatRoomActivity.class);
        intent.putExtra("chat_room_id", chatRoomid);
        intent.putExtra("name", chatRoomName);
        intent.putExtra("type",type);
        intent.putExtra("permission",permission);
        for (ChatRoom cr : normalChatRoomArrayList) {
            if (cr.getId().equals(chatRoomid)) {
                cr.setUnreadCount(0);
                break;
            }
        }
        normalChatAdapter.notifyDataSetChanged();

        startActivity(intent);
    }


    /**
     * Fragments are setup
     * @param viewPager the pageViewer the fragments are added to
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter PageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        PageAdapter.addFragment(new UserProfileFragment(), "Profile");
        PageAdapter.addFragment(new DiscoveryFragment(), "Discovery");
        PageAdapter.addFragment(new FriendsListFragment(), "Friends");
        PageAdapter.addFragment(new chatRoomFragment(), "ChatRoom");
        viewPager.setAdapter(PageAdapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> FragmentList = new ArrayList<>();
        private final List<String> FragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==3){
                onChatFragment=1;
                Fragment f = FragmentList.get(position);
                Bundle b = new Bundle();
                b.putSerializable("data", normalChatRoomArrayList);
                b.putParcelable("adapter", normalChatAdapter);
                Log.d("dta", normalChatRoomArrayList.toArray().toString());
                f.setArguments(b);
                return f;
            }
            if(position==2){
                onChatFragment=5;
                Fragment f = FragmentList.get(position);
                Bundle b = new Bundle();
                b.putSerializable("data", freindsChatRoomArrayList);
                b.putParcelable("adapter", freindChatAdapter);
                Log.d("dta", freindsChatRoomArrayList.toArray().toString());
                f.setArguments(b);
                return f;
            }
            else{
                onChatFragment=0;
            }
            Log.d("onChatFragment", onChatFragment + "");
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return FragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            FragmentList.add(fragment);
            FragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clearing the notification tray
        NotificationUtils.clearNotifications();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    // starting the service to register with GCM
    public void registerGCM() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    public boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    public void changeToBlockedUsers(){
        Intent intent = new Intent(this, ViewBlockedUsers.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_logout:
                MyApplication.getInstance().logout();
                break;
            case R.id.action_viewBlockedList:
                changeToBlockedUsers();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    //navigating to settings...
    public void clickSetting(View view){

        Intent intent = new Intent(UserHomepage.this, Setting.class);
        startActivity(intent);


    }





}
