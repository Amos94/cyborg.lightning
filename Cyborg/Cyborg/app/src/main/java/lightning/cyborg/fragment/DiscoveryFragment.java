package lightning.cyborg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import lightning.cyborg.R;

/**
 * Created by Lewis on 21/02/2016.
 */

public class DiscoveryFragment extends Fragment {

    private View inflatedview;
    private EditText search;
    private ListView friendlist;
    private ArrayAdapter adapter;
    private Button searchButton;

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
        //searchButton = (Button) inflatedview.findViewById(R.id.searchButton);

//        Button searchButton = (Button) inflatedview.findViewById(R.id.searchButton);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, lists);
//                friendlist.setAdapter(adapter);
//            }
//        });

        adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, lists);
        friendlist.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
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

        adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, lists);
        friendlist.setAdapter(adapter);

        return inflatedview;

    }

    //@Override
    //public void onDestroy() {
        //super.onDestroy();
        //search.removeTextChangedListener();
    ///
    //}
}
