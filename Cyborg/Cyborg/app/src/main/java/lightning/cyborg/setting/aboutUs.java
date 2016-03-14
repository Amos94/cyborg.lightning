package lightning.cyborg.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import lightning.cyborg.R;

public class aboutUs extends Activity {

	private int backgroundColor = Color.parseColor("#1E88E5");

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);


	}


}
