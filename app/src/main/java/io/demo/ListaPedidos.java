package io.demo;

//TODO: Esta clase va a quedar sin uso. >>Borrar<<

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.demo.Models.Pedido;
import io.demo.Models.Punto;


public class ListaPedidos extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        // Live Data
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // logica de posicion / Abrir otro activity / pasar argumentos de posicion /

    }


}
