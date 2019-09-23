package io.demo;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MenuDeliverActivity extends AppCompatActivity{

    //Firebase
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference nodoUsuarioActual;

    // Widgets
    Button btn_mapa;
    Button btn_irPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deliver);

        // Widgets
        btn_mapa= (Button)findViewById(R.id.btn_pedidos_disponibles);
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity();
            }
        });
        btn_irPedidos = findViewById(R.id.btn_verpedidos_aceptados);
        btn_irPedidos.setOnClickListener(view->{
            Intent intent = new Intent(MenuDeliverActivity.this, ListaPedidosDeliver.class );
            startActivity(intent);
        });

        // Firebase GPS
        nodoUsuarioActual = mFirebaseDatabase.getReference();;

        // Arrancar GPS Tracking
        iniciarGpsTracking();
    }

    FusedLocationProviderClient mFusedLocationProviderClient;

    private void iniciarGpsTracking() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    nodoUsuarioActual.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("latitud").setValue(location.getLatitude());
                    nodoUsuarioActual.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("latitud").setValue(location.getLongitude());
                    Log.v("Cambio de Localizacion", location.getLatitude() + " | " + location.getLongitude());

                }else {
                    Log.e("Location error : ", "'location' es Null");
                }
            }
        });
    }

    private void abrirActivity() {
        Intent intent = new Intent(this, MapsDestinosActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                        Toast.makeText(MenuDeliverActivity.this, "Log out", Toast.LENGTH_SHORT).show();
//                    }
//                });
        super.onDestroy();
    }



}
