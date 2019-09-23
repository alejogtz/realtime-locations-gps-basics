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

public class MapsDestinosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // Firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference nodoPedidoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_destinos);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Extras
        String pedido_key = getIntent().getExtras().getString("pedido_key");

        // Add Marker in realTime
        iniciarMarker(pedido_key);
    }

    private void iniciarMarker(String pedido_key) {
        // Firebase
        nodoPedidoActual = firebaseDatabase.getReference().child("pedidos").child(pedido_key);
        nodoPedidoActual.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot objPedido) {
                // Marcar el punto en la posicion actual del Repartidor.
                LatLng actualPosicionPedido = new LatLng(objPedido.child("location").child("latitud").getValue(Double.class), objPedido.child("location").child("longitud").getValue(Double.class));
                mMap.addMarker(new MarkerOptions().position(actualPosicionPedido).title("Aqui debes ir a dejar el pedido"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(actualPosicionPedido));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
