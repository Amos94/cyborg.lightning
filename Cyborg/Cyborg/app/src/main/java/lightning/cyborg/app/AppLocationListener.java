package lightning.cyborg.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by nashwan on 12/03/2016.
 */
public class AppLocationListener implements LocationListener {
    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static String stringUpdateGPS;
    private static double longitude;
    private static double latidude;
    private LocationManager tempoLocationManager;

    @SuppressWarnings("ResourceType")
    public AppLocationListener(LocationManager refLocationManager) {
        tempoLocationManager = refLocationManager;
        tempoLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 0, this);
    }


    @SuppressWarnings("ResourceType")
    @Override
    public void onLocationChanged(Location location) {
        latidude=location.getLatitude();
        longitude =location.getLongitude();
        stringUpdateGPS = location.getLatitude() + ", " + location.getLongitude();
        Log.d("AppLocationListener", "onLocationChanged: " + stringUpdateGPS);
        tempoLocationManager.removeUpdates(this);
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
    @SuppressWarnings("ResourceType")
    public void setUp(LocationManager refLocationManager){
        locationManager = refLocationManager;
        tempoLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 40, 0, this);
    }
    @SuppressWarnings("ResourceType")
    public  void requestLocationUpdates()
    {
        tempoLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 40, 0, this);
    }

    public static double getLongitude() {
        return longitude;
    }

    public static double getLatidude() {
        return latidude;
    }
}
