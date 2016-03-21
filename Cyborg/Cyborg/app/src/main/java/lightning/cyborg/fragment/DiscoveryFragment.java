package lightning.cyborg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lightning.cyborg.R;
import lightning.cyborg.app.EndPoints;
import lightning.cyborg.app.MyApplication;

public class DiscoveryFragment extends Fragment {
    private View inflatedview;
    private EditText search;
    private TextView slideTV;
    private CheckBox checkBoxLoc;
    private ListView matchedList;
    private ArrayAdapter adapter;
    private Button searchButton;
    private Button loadButton;
    private String[] matchedUserIDs;
    private ArrayList matchedUserJson;
    private SeekBar seekDist;
    private Spinner genderSpin;
    private Spinner eduSpin;
    private Spinner lowAge, highAge;
    private String[] educationArr;


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

        slideTV = (TextView) inflatedview.findViewById(R.id.sliderTV);
        educationArr = getResources().getStringArray(R.array.education_array);
        matchedList = (ListView) inflatedview.findViewById(R.id.listMatched);
        seekDist = (SeekBar) inflatedview.findViewById(R.id.seekDist);
        checkBoxLoc = (CheckBox)inflatedview.findViewById(R.id.checkBoxLoc);
        search = (EditText) inflatedview.findViewById(R.id.searchMatches);
        search.setText("");
        searchButton = (Button)inflatedview.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover(v);
            }
        });
        loadButton = (Button)inflatedview.findViewById(R.id.loadButton);
        loadButton.setEnabled(false);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    populateDiscovery(5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpin = (Spinner) inflatedview.findViewById(R.id.genderSpin);
        genderSpin.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> eduAdapter = ArrayAdapter.createFromResource(getContext(), R.array.education_array, android.R.layout.simple_spinner_item);
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eduSpin = (Spinner) inflatedview.findViewById(R.id.eduSpin);
        eduSpin.setAdapter(eduAdapter);
        eduSpin.setSelection(9);

        Integer[] age = new Integer[82];
        for(int i = 0;i<age.length;i++){
            int temp = i+18;
            age[i]=temp;
        }
        ArrayAdapter <Integer> ageAdapter = new ArrayAdapter<Integer>( getContext(),android.R.layout.simple_spinner_item, age );
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lowAge = (Spinner) inflatedview.findViewById(R.id.lowAgeSpin);
        highAge = (Spinner) inflatedview.findViewById(R.id.highAgeSpin);
        lowAge.setAdapter(ageAdapter);
        highAge.setAdapter(ageAdapter);
        highAge.setSelection(age.length - 1);

        seekDist.setMax(95);
        seekDist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                slideTV.setText(String.valueOf(progress + 5) + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return inflatedview;
    }

    private void discover(View v){
        final String filtered = search.getText().toString().replaceAll(", ", ",").replaceAll(" ,", ",").toLowerCase();
        String ownID = MyApplication.getInstance().getPrefManager().getUser().getId();
        int radius = seekDist.getProgress() + 5;
        matchedUserJson = new ArrayList<JSONObject>();
        //String year = Calendar.getInstance().get(Calendar.YEAR) + "" + Calendar.getInstance().get(Calendar.MONTH) + "" Calendar.getInstance().get(Calendar.DA);
        String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        int curDate = Integer.parseInt(strDate);
        int minDate = curDate - ((Integer) lowAge.getSelectedItem() * 10000);
        int maxDate = curDate - ((Integer) highAge.getSelectedItem() * 10000);

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userID", ownID);
        params.put("minDate", Integer.toString(minDate));
        params.put("maxDate", Integer.toString(maxDate));

        if (!filtered.equals("")){
            params.put("interests", filtered);
        }
        if (checkBoxLoc.isChecked()){
            params.put("radius", Integer.toString(radius));
        }
        if(!genderSpin.getSelectedItem().equals("Any")){
            params.put("gender", genderSpin.getSelectedItem().toString());
        }
        if(!eduSpin.getSelectedItem().equals("Any")){
            params.put("edu_level", Integer.toString(eduSpin.getSelectedItemPosition()));
        }


        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.DISCOVER_USERS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");
                            JSONArray jsonIDs = jsonResponse.getJSONArray("users");
                            Log.d("jsonIDs", jsonIDs.toString());

                            matchedUserIDs = new String[jsonIDs.length()];
                            for(int i=0; i<jsonIDs.length(); i++){
                                matchedUserIDs[i] = jsonIDs.getString(i);
                            }

                            if(matchedUserIDs.length > 0){
                                populateDiscovery(5);

                                if(matchedUserIDs.length > 5){
                                    loadButton.setEnabled(true);
                                }
                            }
                            else {
                                populateList();
                            }
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            Log.d("disMes", message);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON failed to parse: ", response);
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("VolleyError at url ", EndPoints.DISCOVER_USERS);
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
        final String url = "http://nashdomain.esy.es/users_get_all.php";

        if (matchedUserJson.size() == matchedUserIDs.length){
            loadButton.setEnabled(false);
            Log.d("popDis", "No more users to load");
            Toast.makeText(getActivity(), "No more users to load", Toast.LENGTH_LONG).show();
            return;
        }

        if (inc + matchedUserJson.size() > matchedUserIDs.length){
            inc = matchedUserIDs.length - matchedUserJson.size();
        }

        String idsToGet = "";

        for(int i = matchedUserJson.size(); i < inc + matchedUserJson.size(); i++){
            idsToGet += matchedUserIDs[i] + ",";
        }

        //parameters to post to php file
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userIDs", idsToGet);

        //request to insert the user into the mysql database using php
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getString("success").equals("1");
                            String message = jsonResponse.getString("message");
                            JSONArray temp = jsonResponse.getJSONArray("users");

                            for (int i = 0; i < temp.length(); i++){
                                matchedUserJson.add(temp.getJSONObject(i));
                                Log.d("popDis loading", temp.getJSONObject(i).toString());
                            }

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

    private void populateList() {
        String[] users = new String[matchedUserJson.size()];

        for(int i = 0; i < matchedUserJson.size(); i++){
            try {
                JSONObject user = (JSONObject) matchedUserJson.get(i);
                int age = (Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()))/10000) - (Integer.parseInt(user.getString("dob"))/10000);

                users[i] = user.getString("avatar") + " - " + user.getString("fname") + " - " + user.getString("gender")
                        + " - " + age + " - " + educationArr[Integer.parseInt(user.getString("edu_level"))];
                //TODO add profile avatar at front
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
