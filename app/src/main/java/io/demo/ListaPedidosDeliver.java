package io.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.demo.Models.Pedido;

public class ListaPedidosDeliver extends ListActivity {
    // Firebase Instance
    private final FirebaseDatabase firebase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_deliver);



        //Firebase: Pedidos
        final DatabaseReference nodoPedidos = firebase.getReference().child("pedidos");
        nodoPedidos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pedidos) {
                ArrayList<Pedido> pPedidos = new ArrayList<>();
                for (DataSnapshot objPedido: pedidos.getChildren()){
                    Pedido nuevo = objPedido.getValue(Pedido.class);
                    nuevo.setId( objPedido.getKey() );  // Este paso es importante para el evento del boton
                    pPedidos.add( nuevo );

                }
                finishSetupActivity(pPedidos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void finishSetupActivity(ArrayList<Pedido> pedidos){
        MyAdapter myAdapter = new MyAdapter(pedidos);
        //ArrayAdapter<Pedido> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pedidos);
        setListAdapter(myAdapter);
    }

    //@Override
    //protected void onListItemClick(ListView l, View v, int position, long id) {
    //    eventoSuscribir(pPedidos.get(position));
    //}

    private class MyAdapter extends BaseAdapter{
        // Data
        ArrayList<Pedido> pedidos;



        public MyAdapter(ArrayList<Pedido> pedidos){
            this.pedidos = pedidos;
        }

        @Override
        public int getCount() {
            return pedidos.size();
        }

        @Override
        public Object getItem(int i) {
            return pedidos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return pedidos.get(i).hashCode(); // A ver
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            // Inflate Layout Item
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_pedidos4deliver, null);
            }

            // Item
            Pedido current = pedidos.get(i);

            //Widgets
            TextView txtDescripcion = convertView.findViewById(R.id.itemdeliver_txtdescripcion);
            Button suscribir = convertView.findViewById(R.id.itemdeliver_btnaceptar);
            Button verMapa = convertView.findViewById(R.id.itemdeliver_vermapa);

             //Funcionalidad
            suscribir.setOnClickListener(evento->{
                // TODO: Actualizar el estado del pedido xD
                eventoSuscribir(pedidos.get(i));
                txtDescripcion.setBackgroundColor(Color.YELLOW);  // Para diferenciar los aceptados xD
            });
            verMapa.setOnClickListener(view->{
                eventoAbrirDestino(pedidos.get(i));
            });

            //Info
            txtDescripcion.setText( current.getDescripcion() );

            return convertView;
        }
    }


    private void eventoAbrirDestino(Pedido p){
        Intent intent = new Intent(this, MapsDestinosActivity.class);
        intent.putExtra("pedido_key",p.getId() );
        startActivity(intent);
    }

    private void eventoSuscribir(Pedido pedido) {
        // Cambio de Status
        pedido.setDeliver_uid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        pedido.setStatus(Pedido.EN_CAMINO);
        // Firebase: Actualizar Pedido a Suscrito
        DatabaseReference nodoPedidoX = firebase.getReference().child("pedidos").child(pedido.getId());
        nodoPedidoX.setValue(pedido);
        nodoPedidoX.onDisconnect();
        // Log
        Toast.makeText(this, "Activaste un boton j3j3", Toast.LENGTH_LONG).show();
    }



}
