package temp.locationtemp;

/**
 * Created by Amos Madalin Neculau on 11/03/2016.
 */

        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Context;
        import android.view.Menu;

public class GetLocation extends Activity implements LocationListener {


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void distanceMethod(double latitude1,double latitude2,double longitude2,double longitude1){

        double radius = 6371000;
        double lat1 =Math.toRadians(latitude1);
        double lat2 =Math.toRadians(latitude2);
        double difLat = Math.toRadians(lat2-lat1);
        double difLong = Math.toRadians(longitude2-longitude1);

        double aTemp = (Math.sin(difLat)*Math.sin(difLat))+(Math.cos(lat1)*Math.cos(lat2)*Math.sin(difLong/2)*Math.sin(difLong/2));
        double cTemp =Math.atan2(Math.sqrt(aTemp),Math.sqrt(1-aTemp));

        double result = radius*cTemp;
    }
}