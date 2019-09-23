package io.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import io.demo.Firebase.FirebaseHelper;

public class DetallesPagoActivity extends AppCompatActivity {

    // Widgets
    TextView idTextView, statusTextView, montoTextView;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pago);

        // Instanciar Widgets
        idTextView = findViewById(R.id.txt_id);
        statusTextView = findViewById(R.id.txt_status);
        montoTextView = findViewById(R.id.txt_monto);
        btnRegresar = findViewById(R.id.btn_procesar_pago);

        btnRegresar.setOnClickListener(view->{
            finish();
        });

        Intent intent = getIntent();
        try {
            String descripcion = intent.getStringExtra("Descripcion");
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            verDetalles(jsonObject, intent.getIntExtra("PaymentAmount", 0), descripcion);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void verDetalles(JSONObject response, int monto, String descripcion) {
        try {
            idTextView.setText(response.getString("id"));
            statusTextView.setText(response.getString("state"));
            montoTextView.setText("$" + monto);

            FirebaseHelper.enviarPedidoAFirebase(descripcion, monto);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
