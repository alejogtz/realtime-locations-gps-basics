package io.demo.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.demo.Models.Pedido;
import io.demo.Models.Punto;

public class FirebaseHelper {



    public static  void enviarPedidoAFirebase(String status, double monto) {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference nodoPedidos = firebaseDatabase.getReference().child("pedidos");

        final Pedido p = new Pedido();
        p.setCliente_uid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        p.setStatus(Pedido.SIN_PROCESAR);
        p.setLocation(new Punto(17.0797948,-96.7248254)); // TODO: Aqui será un valor default ALV :v (REforma ALV zD)
        p.setCosto(monto);
        p.setDescripcion(status);

        nodoPedidos.push().setValue(p);

    }
}
