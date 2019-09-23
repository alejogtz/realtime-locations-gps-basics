package io.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private final static int LOGIN_PERMISSION = 1000;

    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        btnLogin = (Button) findViewById(R.id.btn_login);
        //        btnLogin.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), LOGIN_PERMISSION);
        //
        //            }
        //        });
                // [END]

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_PERMISSION) {
            startNewActivity(requestCode, resultCode, data);
        } else {
            Toast.makeText(MainActivity.this, "Login Failed!!!", Toast.LENGTH_SHORT);
        }
    }

    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private void startNewActivity(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String uid = null;

            // Firebase
            user = FirebaseAuth.getInstance().getCurrentUser();
            uid = user.getUid();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(uid);
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String rol = (String) dataSnapshot.child("rol").getValue();
                    // Launch new Activity
                    launchActivity(rol);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    public void launchActivity(String rol) {
        Intent intent = null;
        if (rol.equals("Cliente")) {
            intent = new Intent(MainActivity.this, MenuClienteActivity.class);
        } else {
            intent = new Intent(MainActivity.this, MenuDeliverActivity.class);
        }

        startActivity(intent);

        finish();
    }
}
