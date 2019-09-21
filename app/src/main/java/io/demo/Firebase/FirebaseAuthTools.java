package io.demo.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.demo.Models.Pedido;

public class FirebaseAuthTools {

    // Firebase Vars
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    /// Enviar Pedido
    public void enviarPedido(Integer monto, String descripcion){
        DatabaseReference nodoPedido = firebaseDatabase.getReference();
        Pedido datos = new Pedido();
        datos.setCliente_uid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        // TODO: Escribe el monto
        // datos.setCosto();
        // datos.setDescripcion();
        datos.setStatus(Pedido.SIN_PROCESAR);

        // Push to Firebase
        nodoPedido.push().setValue(datos); // Nuevo nodo
    }



}
