package io.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


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

        // FakePedidos.insertar();
    }


    public void initComponents() {
        // BUTTON Y EVENTOS
        btn_lista_pedidos = (Button) findViewById(R.id.btn_verpedidos);
        btn_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListaPedidosActivity();
            }
        });

        btn_comprar = (Button) findViewById(R.id.btn_comprar);
        btn_comprar.setOnClickListener((view) -> {
            Intent intent = new Intent(MenuClienteActivity.this, HacerCompra.class);
            startActivity(intent);
        });

    }

    private void startListaPedidosActivity() {
        Intent intent = new Intent(this, ListaPedidos.class);
        startActivity(intent);
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MenuClienteActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegador, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
