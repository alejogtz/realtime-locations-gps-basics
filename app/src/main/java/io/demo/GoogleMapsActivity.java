package io.demo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Google Maps
    private GoogleMap mMap;

    // Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference nodoUserDeliver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         firebaseDatabase = FirebaseDatabase.getInstance();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Intent
        String uid_deliver = getIntent().getExtras().getString("deliver_uid");

        // Add Marker in realTime
        iniciarRealtimeMarker(uid_deliver);
    }

    private void iniciarRealtimeMarker(String uid_deliver) {
        // Firebase
        nodoUserDeliver = firebaseDatabase.getReference().child("users").child(uid_deliver);
        nodoUserDeliver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot objDeliver) {
                // Marcar el punto en la posicion actual del Repartidor.
                LatLng actualPosisionDelRepartidor = new LatLng(objDeliver.child("latitud").getValue(Double.class), objDeliver.child("longitud").getValue(Double.class));
                mMap.addMarker(new MarkerOptions().position(actualPosisionDelRepartidor).title("Tú Repartidor está aquí j3j3"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(actualPosisionDelRepartidor));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nodoUserDeliver.onDisconnect();
        mMap.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
