package lightning.cyborg.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.activity.ChatRoomActivity;
import lightning.cyborg.activity.LoginActivity;
import lightning.cyborg.activity.UserHomepage;
import lightning.cyborg.adapter.ChatRoomsAdapter;
import lightning.cyborg.app.Config;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.gcm.GcmIntentService;
import lightning.cyborg.gcm.NotificationUtils;
import lightning.cyborg.helper.SimpleDividerItemDecoration;
import lightning.cyborg.model.ChatRoom;
import lightning.cyborg.model.Message;

/**
 * Created by Lewis on 21/02/2016.
 */
public class chatRoomFragment extends Fragment{
    private View inflatedview;
    private String TAG = "chatRoomFragment";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ArrayList<ChatRoom> chatRoomArrayList;
    private ChatRoomsAdapter mAdapter;
    private RecyclerView recyclerView;

    public chatRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflatedview = inflater.inflate(R.layout.fragment_chat_room, container, false);

        recyclerView = (RecyclerView) inflatedview.findViewById(R.id.recycler_view12);
        Bundle b= getArguments();
        chatRoomArrayList = new ArrayList<>();

        chatRoomArrayList = (ArrayList<ChatRoom>) b.getSerializable("data");

        Log.d("chatRoomArrayList", chatRoomArrayList.toString());
//

        mAdapter= b.getParcelable("adapter");

        LinearLayoutManager layoutManager = new LinearLayoutManager(inflatedview.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity().getApplicationContext()
        ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new ChatRoomsAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ChatRoomsAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // when chat is clicked, launch full chat thread activity
                ChatRoom chatRoom = chatRoomArrayList.get(position);

                //if chatroom is not activiated
                if(!chatRoom.getPermission().equals("n"))
                {
                    ((UserHomepage) getActivity()).chatRoomActivityIntent(chatRoom.getId(), chatRoom.getName(),"n",chatRoom.getPermission());
                }
                else{
                    if(!chatRoom.isChatRoomExists()){
                        recyclerView.removeView(view);
                        mAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onLongClick(View view, int position) {
               ChatRoom chatRoom = chatRoomArrayList.get(position);
            }
        }));
        // Inflate the layout for this fragment
        return inflatedview;

    }



}



