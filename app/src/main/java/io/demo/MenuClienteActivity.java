package io.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MenuClienteActivity extends AppCompatActivity {
    /// [Start Codigo de prueba] Enviar y recibir datos de Firebase
    // Declaracion de los componentex
    Button btn_lista_pedidos;
    Button btn_comprar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        // BUTTON Y EVENTOS
        btn_lista_pedidos = (Button) findViewById(R.id.btn_verpedidos);
        btn_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListaPedidosActivity();
            }
        });


    }

    private void startListaPedidosActivity() {
        // Intent intent = new Intent(this, ListaPedidos.class);
        Intent intent = new Intent(this, ListaPedidos.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        /**AuthUI.getInstance()
         .signOut(this)
         .addOnCompleteListener(new OnCompleteListener<Void>() {
         public void onComplete(@NonNull Task<Void> task) {
         // ...
         Toast.makeText(MenuClienteActivity.this, "Log out", Toast.LENGTH_SHORT);
         }
         });*/
        super.onDestroy();
    }


}
