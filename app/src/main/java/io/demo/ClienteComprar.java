package io.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClienteComprar extends AppCompatActivity {
    // Widgets
    EditText editDescripcion, editCosto, editClientuid, editDeliveruid;
    Button btnEnviar;

    // Conexion con Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_comprar);
    }

    public void initComponents(){
        editDescripcion = findViewById(R.id.edit_compra_descripcion);
        editCosto = findViewById(R.id.edit_compra_costo);
        editClientuid = findViewById(R.id.edit_cliente_uid);
        editDeliveruid = findViewById(R.id.edit_deliver_uid);

        btnEnviar = findViewById(R.id.btn_comprar);
    }

    private void acctionDeBotonComprar(){
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GEt Varuables
            }
        });
    }
}
