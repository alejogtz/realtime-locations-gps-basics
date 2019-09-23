package io.demo;

//TODO: Esta clase va a quedar sin uso. >>Borrar<<

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
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

    // Firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference nodoPedidos;

    // ListView
    // private ListView ListViewParaPedidos;
    private ArrayAdapter<Pedido> pedidos_cliente_adapter;

    // Data Alive
    private ArrayList<Pedido> pedidosDelCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        // Widgets: Sólo instanciacion. <<No se requiere si desprendes de un List Activity>>


        // Iniciar Componentes y configuracion
        setupConfigurationThisActivity();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Abrir otro activity
        String uidDeliver = pedidosDelCliente.get(position).getDeliver_uid();
        if (uidDeliver != null && !uidDeliver.equals("") ) {
            Intent abrirMapaIntent = new Intent(ListaPedidos.this, GoogleMapsActivity.class);
            abrirMapaIntent.putExtra("deliver_uid", uidDeliver);
            Log.v("Log...", uidDeliver);
            startActivity(abrirMapaIntent);
        }else
            Toast.makeText(this, "Sorry, Este pedido aun esta por procesar xD", Toast.LENGTH_LONG).show();
    }

    // Cargas las listas de >>Data Alive<<
    private void setupConfigurationThisActivity() {
        // Se necesita saber la lista de pedidos realizados
        pedidosDelCliente = new ArrayList<>();
        // Firebase
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        nodoPedidos = firebaseDatabase.getReference().child("pedidos");
        nodoPedidos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot p : dataSnapshot.getChildren()) {
                    if (p.child("cliente_uid").getValue(String.class).equals(uid))
                        pedidosDelCliente.add(p.getValue(Pedido.class));
                }
                cargarListViewDePedidos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void cargarListViewDePedidos() {
        if (pedidosDelCliente.size() != 0) {
            // TODO: Crear Adapter Personalizado para los items de Pedido.
            pedidos_cliente_adapter = new ArrayAdapter<Pedido>(this,
                    R.layout.item_cliente_pedido, R.id.pedidocliente_descripcionpedido, pedidosDelCliente);
            this.setListAdapter(pedidos_cliente_adapter);
        } else {
            Toast.makeText(this, "Sorry, No tienes pedidos registrados xD", Toast.LENGTH_LONG).show();
        }
    }
}
