package io.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallesPagoActivity extends AppCompatActivity {

    // Widgets
    TextView idTextView, statusTextView, montoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pago);

        // Instanciar Widgets
        idTextView = findViewById(R.id.txt_id);
        statusTextView = findViewById(R.id.txt_status);
        montoTextView = findViewById(R.id.txt_monto);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            verDetalles(jsonObject, intent.getStringExtra("PaymentAmount"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void verDetalles(JSONObject response, String amount) {
        try {
            idTextView.setText(String.valueOf(response.getInt("id")));
            statusTextView.setText(response.getString("status"));
            montoTextView.setText("$" + amount);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
