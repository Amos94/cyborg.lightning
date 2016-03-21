package lightning.cyborg.helper;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import lightning.cyborg.R;
import lightning.cyborg.model.User;

/**
 * Created by nashwan on 21/03/2016.
 */
public class ViewProfileDialog extends Dialog {

    private User user;
    private ImageView avatar;
    private ListView interests;
    private TextView name;

    public ViewProfileDialog(Context context, User user) {
        super(context);
        this.user =user;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void setContentView(){
        setContentView(R.layout.dialog_view_profile);
        name = (TextView) findViewById(R.id.vp_UserName);
        avatar=(ImageView) findViewById(R.id.vp_Avatar);
        interests =(ListView) findViewById(R.id.vp_InterestList);

        //name.setText(user.getName());

    }
}
