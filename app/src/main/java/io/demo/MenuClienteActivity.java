package io.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.demo.DummyData.FakeUsers;


public class MenuClienteActivity extends AppCompatActivity {
    /// [Start Codigo de prueba] Enviar y recibir datos de Firebase
    // Declaracion de los componentex
    Button btn_lista_pedidos;
    Button btn_comprar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        initComponents();
    }


    public void initComponents(){
        // BUTTON Y EVENTOS
        btn_lista_pedidos = (Button) findViewById(R.id.btn_verpedidos);
        btn_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListaPedidosActivity();
            }
        });

        btn_comprar = (Button) findViewById(R.id.btn_comprar);
        btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startListaPedidosActivity();
                Toast.makeText(MenuClienteActivity.this, "Sin Funcionalidad", Toast.LENGTH_SHORT);
            }
        });

    }

    private void startListaPedidosActivity() {
        // Intent intent = new Intent(this, ListaPedidos.class);
        Intent intent = new Intent(this, MapaUbicacionPedido.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        AuthUI.getInstance()
         .signOut(this)
         .addOnCompleteListener(new OnCompleteListener<Void>() {
         public void onComplete(@NonNull Task<Void> task) {
         // ...
         Toast.makeText(MenuClienteActivity.this, "Log out", Toast.LENGTH_SHORT);
         }
         });
        super.onDestroy();
    }


}
