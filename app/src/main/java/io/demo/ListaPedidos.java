package io.demo;

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

    // Variables
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference refPedidos;
    private DatabaseReference mPrueba;

    ArrayAdapter<String> pedidosAdapter;

    HashMap<String, Pedido> pedidos;
    ArrayList<String> pedidos_descripcion;

    String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        /** Instanciar Variables*/

        pedidos = new HashMap<String, Pedido>();
        pedidos_descripcion = new ArrayList<String>();

        /**     Cargar el uid del current client */
        Intent intent = getIntent();
        usuario = intent.getStringExtra("user_uid");
        /** //////////////////////////////////////////////*/


        setUpSystem();

        // dummyPedidos();
    }

    private void setUpSystem() {

        refPedidos = database.getReference().child("cliente").child(usuario).child("pedidos");
        final DatabaseReference refNodoPedidos = database.getReference().child("pedidos");

        refPedidos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /** Generar un arreglo de los Datos de Firebase*/
                for (DataSnapshot x: dataSnapshot.getChildren()){
                    Log.v("Key:", x.getValue(String.class));
                    pedidos_descripcion.add(x.getValue(String.class));
                }
                /** [Fin ]*/

                /** Cargar la lista ui*/                    //          Contexto        Where
                pedidosAdapter = new ArrayAdapter<String>(ListaPedidos.this, R.layout.row_pedido, R.id.row_txt_pedido, pedidos_descripcion);
                setListAdapter(pedidosAdapter);
                /**[Fin]*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.v("Database Error: ", databaseError.getMessage());
            }
        });

        refNodoPedidos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x: dataSnapshot.getChildren()){
                //    if ( pedidos_descripcion.contains( x.getKey() ) ){

                        pedidos.put(x.getKey(), x.getValue(Pedido.class));

                 //   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("Database Error: ", databaseError.getMessage());
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // logica de posicion / Abrir otro activity / pasar argumentos de posicion /
        String key_string = (String) getListAdapter().getItem(position);
        Pedido p = pedidos.get( key_string );//pedidos_descripcion.get( position ) );
        if (p.getStatus() == Pedido.EN_CAMINO){
            Intent intent = new Intent(this, MapaUbicacionPedido.class);
            intent.putExtra("key_pedido", key_string);
            startActivity(intent);
        }else if (p.getStatus() == Pedido.SIN_PROCESAR){
            Toast.makeText(this, "Sorry, Aun sin procesar", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }


}
