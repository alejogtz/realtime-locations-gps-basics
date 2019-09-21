package io.demo.DummyData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.demo.Models.Pedido;
import io.demo.Models.Punto;

public class FakePedidos {
    // Firebase
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference nodoPedidos;

    public static void insertar(){
        Pedido fakeNuevoPedido = null;
        String uidClient = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Firebase
        nodoPedidos = firebaseDatabase.getReference().child("pedidos");

        for (int i = 0; i < 10; i++){
            fakeNuevoPedido= new Pedido();
            fakeNuevoPedido.setId(String.valueOf(i));
            fakeNuevoPedido.setCliente_uid(uidClient);
            fakeNuevoPedido.setDeliver_uid("06bqqktDhBNKHSIWXiRKVPrz8ZF3"); // Pruebas
            fakeNuevoPedido.setCosto(100);
            fakeNuevoPedido.setLocation(new Punto(17.0669,-96.7203)); // Destino.
            fakeNuevoPedido.setDescripcion("Pedido"+i);
            // No mostrar entregados
            fakeNuevoPedido.setStatus(Pedido.EN_CAMINO); // Considerar en la logica

            nodoPedidos.push().setValue(fakeNuevoPedido);
        }

        nodoPedidos.onDisconnect();
    }
}
