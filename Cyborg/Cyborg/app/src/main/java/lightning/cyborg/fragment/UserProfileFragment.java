package lightning.cyborg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lightning.cyborg.R;
import lightning.cyborg.activity.AvatarActivity;
import lightning.cyborg.activity.Avator_Logo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Lewis on 21/02/2016.
 */
public class UserProfileFragment extends Fragment {


    public static ImageView  imageview;
    private ArrayList<String> items;
    private ArrayAdapter adapter;
    private ListView listview;
    private EditText etInterest;
    private Button addInterest;




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


        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

                alertDialogBuilder.setPositiveButton("Change Avator", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getActivity(), AvatarActivity.class);
                        startActivity(intent);


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
        items = new ArrayList<String>();
        items.add("Manish " + "Srivastava");
        items.add("Sachin " + "Tendulker");
        items.add("Ricky" + "Pointing");
        items.add("Manish " + "Srivastava");
        items.add("Sachin " + "Tendulker");
        items.add("Ricky " + "Pointing");
        items.add("Manish " + "Srivastava");
        items.add("Sachin " + "Tendulker");
        items.add("Ricky " + "Pointing");
        items.add("Manish " + "Srivastava");
        items.add("Sachin " + "Tendulker");
        items.add("Ricky " + "Pointing");
        items.add("Manish " + "Srivastava");
        items.add("Sachin " + "Tendulker");
        items.add("Ricky " + "Pointing");

        adapter = new ArrayAdapter(this.getActivity().getApplicationContext(),R.layout.list_black , R.id.list_content, items);


        listview = (ListView) viewroot.findViewById(R.id.listInterest);

        listview.setAdapter(adapter);

        //creating function to add more items into the interest

        etInterest = (EditText) viewroot.findViewById(R.id.etAddText);
        addInterest = (Button) viewroot.findViewById(R.id.button);


        //insert values into database... for interest of users
        addInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = etInterest.getText().toString();
                items.add(temp.toString());
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), temp.toString() + " has been added to Interest", Toast.LENGTH_LONG).show();

            }
        });







        return viewroot;


    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}
