package lightning.cyborg.activity;

/**
 * Created by Ahadxo on 3/12/16.
 */
import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;

    public ImageItem(Bitmap image) {
        super();
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
