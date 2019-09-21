package io.demo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

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

    public GpsTracking() {
        super("GpsTracking");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
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
    private void handleActionGpsTracking() {
        // TODO: Codifica las acciones para que el Usuario empiece a Dar su ubicacion


    }

}
