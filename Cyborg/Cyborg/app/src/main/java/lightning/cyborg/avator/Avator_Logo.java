package lightning.cyborg.avator;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import lightning.cyborg.R;

public class Avator_Logo extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avator);


        //inserting the Avator and Name into a custom GridLayout I made in a seperate Class..

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);



        // When you click the Imageview, this will find the position and then compress into Bitmap and than return the Avator
        // back to a its appropirate activitiy.. for example: registration or Homepage...

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bitmap item = (Bitmap) parent.getItemAtPosition(position);
                android.graphics.Bitmap x = item;
                //UserProfileFragment.imageview.setImageBitmap(x);
                // RegistrationActivity.avatorIcon.setImageBitmap(x);
                Intent intent = new Intent();
                ByteArrayOutputStream bs  = new ByteArrayOutputStream();
                x.compress(android.graphics.Bitmap.CompressFormat.PNG,50,bs );

                intent.putExtra("Bitmap", bs.toByteArray());
                intent.putExtra("imageID",position);

                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }

    /**
     * Prepare Avator Image for gridview..
     * Images are stored in an arraylist inside the String xml to add more avator or images.. in the future...
     */




    private ArrayList<Bitmap> getData() {
        final ArrayList<Bitmap> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length()-1; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(bitmap);
        }
        return imageItems;
    }
}