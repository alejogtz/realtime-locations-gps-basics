package io.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import io.demo.DummyData.FakeUsers;

public class MenuDeliverActivity extends AppCompatActivity {

    //Firebase
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference;

    //GPS
    FusedLocationProviderClient mFusedLocationProviderClient;

    // Widgets
    Button btn_mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deliver);
        //Iniciar el GPS
        //      iniciarGPS();

        // Otras cosas
        btn_mapa= (Button)findViewById(R.id.btn_pedidos_disponibles);
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity();
            }
        });
    }

    private void abrirActivity() {
        Intent intent = new Intent(this, MapaDestinos.class);
        startActivity(intent);
    }

//    public void iniciarGPS(){
//        //Map
//        Map<String, Object> map = new HashMap();
//
//        // Firebase
//        mDatabaseReference = mFirebaseDatabase.getReference();
//
//        // GPS
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location!=null){
//                    map.put("Latitude", location.getLatitude());
//                    map.put("Longitude", location.getLongitude());
//                    mDatabaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);
//                    map.clear(); // Para el siguiente cambio de localizacion no se encime v:
//
//                    Log.v("Cambio de Localizacion", location.getLatitude() + " | " + location.getLongitude());
//
//                }else {
//                    Log.e("Location error : ", "'location' es Null");
//                }
//            }
//        });
//
//    }


    @Override
    protected void onDestroy() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MenuDeliverActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                    }
                });
        super.onDestroy();
    }

}
