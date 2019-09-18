package io.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

public class MapaDestinos extends AppCompatActivity {

    // Vars
    MapView mapView;
    MapboxMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MapBox
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        //
        setContentView(R.layout.activity_mapa_destinos);

        mapView = (MapView)findViewById(R.id.mapView2);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                map = mapboxMap;
                Point destinationPoint = Point.fromLngLat(17.0594169, -96.7216219);

                GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
                if (source != null) {
                    source.setGeoJson(Feature.fromGeometry(destinationPoint));
                }
            }
        });
        Log.v("Msje", "AWEBO pbots");
    }
}
