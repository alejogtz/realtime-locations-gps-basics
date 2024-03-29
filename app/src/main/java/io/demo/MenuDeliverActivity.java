package io.demo;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuDeliverActivity extends AppCompatActivity{

    //Firebase
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference nodoUsuarioActual;

    private DatabaseReference nodoPedidos;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    // Widgets
    Button btn_mapa;
    Button btn_irPedidos;

    // Notificaciones
    private final static String CHANEL_ID = "NOTIFICACION";
    private final static int NOTIFICATION_ID = 0;

    //-------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deliver);

        // Widgets
        btn_irPedidos = findViewById(R.id.btn_verpedidos_aceptados);
        btn_irPedidos.setOnClickListener(view->{
            Intent intent = new Intent(MenuDeliverActivity.this, ListaPedidosDeliver.class );
            startActivity(intent);
        });

        // Firebase GPS
        nodoUsuarioActual = mFirebaseDatabase.getReference();;

        // Arrancar GPS Tracking
        iniciarGpsTracking();


        // Notificacion: Escuchar eventos de nuevos pedidos
        lanzarUnaNotificacion();
        iniciarEventoNotificacion();


    }//-----------------------------------------------------------------------------------------------------------

    private void lanzarUnaNotificacion(){
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID);
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        builder.setContentTitle("Nuevos Pedidos!!!");
        builder.setContentText("Se han agregado nuevos pedidos, Corre a apuntarte xD");
        builder.setColor(Color.YELLOW);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from( getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void iniciarEventoNotificacion() {
        nodoPedidos = mFirebaseDatabase.getReference().child("pedidos");
        nodoPedidos.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                lanzarUnaNotificacion();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

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

    private void logout(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MenuDeliverActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegador, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
