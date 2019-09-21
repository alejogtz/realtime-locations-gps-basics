package io.demo.DummyData;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.demo.Models.Pedido;
import io.demo.Models.Usuario;

public class FakeUsers {

    public void insertar() {
        List<String> listaUsersUidsFromFirebase = Arrays.asList(
                "Y3ek0g45c7fMV1xvZKRr6ky85Kv2",
                "0o62gzeIuafLE3q8tUhl37uQXgE2",
                "uWdFnLNp0tMtBwr7LnhHK4SgYTJ3",
                "06bqqktDhBNKHSIWXiRKVPrz8ZF3",
                "3E9pV7fRMfZ40VbYMoupeh4N9gI3",
                "L77TxXHSrDTHGwFcJ2hVRRqO8Gn1",
                "Ty0usDew5kWn4x7iT1TsPraWo5A3",
                "h3D6yOMahcfNkOF8czorM73vsJr2",
                "zfAw20rNMxVUQqw2BIXO3orlN983",
                "C8x6IldcNScD9AXKoB3MF26IRYb2",
                "GoNm3ZdW3ufr7ELV9bCKstoMP6e2",
                "WXE4TeYjhJV0txhMN2nmIaFeL1i1",
                "iw8QKcMu3ITrLbLxSBkonjcHbwG3");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference refUsers = firebaseDatabase.getReference().child("users");

        for (int i = 0; i < listaUsersUidsFromFirebase.size(); i++){
            if ( i < 5)
                // User 0 + 1
                refUsers.child(listaUsersUidsFromFirebase.get(i)).setValue( new Usuario("Usuario" + i, 17.1018958, -96.7388011, "Deliver") );
            else {
                refUsers.child(listaUsersUidsFromFirebase.get(i)).setValue(new Usuario("Usuario" + i, 17.1018958, -96.7388011, "Cliente"));
                // Log.v("Contador", String.valueOf(i));
            }
        }

        //firebaseDatabase.goOffline();
        //refUsers.onDisconnect();
    }

    public void insertarDummyPedidos(){



    }

}
