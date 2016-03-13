package lightning.cyborg.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.activity.UserHomepage;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;
import lightning.cyborg.model.ChatRoom;


public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ViewHolder> implements Parcelable {

    private Context mContext;
    private ArrayList<ChatRoom> chatRoomArrayList;
    private static String today;
    private boolean buttonPressed=false;
    public static String TAG = "ChatRoomAdapter";

    protected ChatRoomsAdapter(Parcel in) {
    }

    public static final Creator<ChatRoomsAdapter> CREATOR = new Creator<ChatRoomsAdapter>() {
        @Override
        public ChatRoomsAdapter createFromParcel(Parcel in) {
            return new ChatRoomsAdapter(in);
        }

        @Override
        public ChatRoomsAdapter[] newArray(int size) {
            return new ChatRoomsAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, message, timestamp, count;
        public Button accept, decline;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            message = (TextView) view.findViewById(R.id.message);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            count = (TextView) view.findViewById(R.id.count);

            accept = (Button) view.findViewById(R.id.acceptbutton);
            decline = (Button) view.findViewById(R.id.rejectbutton);
        }
    }


    public ChatRoomsAdapter(Context mContext, ArrayList<ChatRoom> chatRoomArrayList) {
        this.mContext = mContext;
        this.chatRoomArrayList = chatRoomArrayList;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_rooms_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ChatRoom chatRoom = chatRoomArrayList.get(position);
        holder.name.setText(chatRoom.getName());


        if(chatRoom.getAccess_type().equals("y")){

            holder.message.setText(chatRoom.getLastMessage());
            //Buttons are removed
            holder.accept.setVisibility(View.GONE);
            holder.accept.setOnClickListener(null);
            holder.decline.setVisibility(View.GONE);
            holder.decline.setOnClickListener(null);


            if (chatRoom.getUnreadCount() > 0) {
            holder.count.setText(String.valueOf(chatRoom.getUnreadCount()));
            holder.count.setVisibility(View.VISIBLE);

            }
            else {
            holder.count.setVisibility(View.GONE);

            }
        }

        else{
            if(chatRoom.getPermission().equals("s")){

                holder.message.setText("pending request");


                //Buttons are removed
                holder.accept.setVisibility(View.GONE);
                holder.accept.setOnClickListener(null);
                holder.decline.setVisibility(View.GONE);
                holder.decline.setOnClickListener(null);

            }
            else if(chatRoom.getPermission().equals("r")){

                //buttons are shown
                holder.accept.setVisibility(View.VISIBLE);
                holder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        serverHandler("accept", chatRoom.getId());
                       ((UserHomepage) mContext).chatRoomActivityIntent(chatRoom.getId(), chatRoom.getName());
                        //notifyDataSetChanged();

                    }
                });

                holder.decline.setVisibility(View.VISIBLE);
                holder.decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serverHandler("decline", chatRoom.getId());
                        chatRoom.setChatRoomExists(false);
                        chatRoomArrayList.remove(chatRoom);
                        notifyDataSetChanged();
                    }
                });
            }
            holder.count.setVisibility(View.GONE);
        }

        Log.d("after", chatRoom.getId() + "per::" + chatRoom.getPermission());

        holder.timestamp.setText(getTimeStamp(chatRoom.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return chatRoomArrayList.size();
    }

    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public void serverHandler(String reply,String chatRoomId){
        final String choice = reply;
        final String cR_Id =chatRoomId;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                EndPoints.REQUEST_RESPONSE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                String resp = response.substring(1,response.length());
                try {
                    JSONObject obj = new JSONObject(resp);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {

                        if(choice.equals("accept")){
                            notifyDataSetChanged();
                        }
                    } else {

                        // error in fetching chat rooms
                        Toast.makeText(mContext, "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(mContext, "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                // subscribing to all chat room topics
                // subscribeToAllTopics();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(mContext, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("choice", choice);
                params.put("chat_room_id",  cR_Id);

                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

}
