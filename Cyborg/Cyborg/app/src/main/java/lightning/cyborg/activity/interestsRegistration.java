package lightning.cyborg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lightning.cyborg.R;
import lightning.cyborg.enums.InterestTypes;

public class interestsRegistration extends AppCompatActivity {

    private InterestTypes[] InterestList;
    private ListView interestsListView;
    private ListView newinterestsListView;
    private ArrayAdapter adapter;
    private ArrayAdapter adapter2;
    private EditText searchInterests;
    private List<InterestTypes> aa;
    private ArrayList<InterestTypes> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests_registration);


        InterestList = InterestTypes.values();
        aa = new LinkedList<InterestTypes>(Arrays.asList(InterestList));

        newList = new ArrayList<>();

        interestsListView = (ListView) findViewById(R.id.InterestsListView);
        searchInterests = (EditText) findViewById(R.id.searchInterests);
        newinterestsListView = (ListView) findViewById(R.id.newInterestsListView);

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, aa);
        Log.d("test", aa.toString());
        interestsListView.setAdapter(adapter);

        searchInterests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);

            }
        });

        adapter2 = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, newList);
        Log.d("test1", newList.toString());
        newinterestsListView.setAdapter(adapter2);

        interestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                InterestTypes temp = aa.remove(position);
                adapter.notifyDataSetChanged();
                temp.toString();
                newList.add(temp);
                adapter2.notifyDataSetChanged();

            }
        });
    }


}
