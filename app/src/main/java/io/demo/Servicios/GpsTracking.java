package io.demo.Servicios;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * * helper methods.
 */
public class GpsTracking extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String GPS_TRACKING_ACTION = "io.demo.action.gps_tracking_gps";

    //Rename parameters
    //private static final String EXTRA_PARAM1 = "io.demo.extra.PARAM1";
    // private static final String EXTRA_PARAM2 = "io.demo.extra.PARAM2";


    //GPS
    FusedLocationProviderClient mFusedLocationProviderClient;

    //Firebase
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference nodoDeliverSeleccionado;


    public GpsTracking() {
        super("GpsTracking");
    }


    public static void startActionGPSTracking(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GpsTracking.class);
        intent.setAction(GPS_TRACKING_ACTION);
        //intent.putExtra(EXTRA_PARAM1, param1);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (GPS_TRACKING_ACTION.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionGpsTracking();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    //private void handleActionGpsTracking(String param1, String param2) {
    @SuppressWarnings( {"MissingPermission"})
    private void handleActionGpsTracking() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                // Send Data to Firebase


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);

    }// GPS



}
