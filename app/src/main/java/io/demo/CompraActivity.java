package io.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import io.demo.PayPal.Config;


public class CompraActivity extends AppCompatActivity {

    // Widgets
    private Button btn_procesar_pago;
    private Spinner choose_producto, choose_cantidad;

    // PayPal
    private static final int PAYPAL_REQUEST_CODE = 7171;

    // Logic Bussines
    private  int monto;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        // PayPal : iniciar Servicio
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        choose_producto = findViewById(R.id.opciones_producto);
        choose_cantidad = findViewById(R.id.opciones_cantidad);

        btn_procesar_pago = findViewById(R.id.btn_procesar_pago);
        btn_procesar_pago.setOnClickListener((view)->{procesarPago();}); // Implementacion Lambda de una 'Interface Funcional'

        initWidgets();
    }


    private void procesarPago(){
        int cantidad = Integer.parseInt( choose_cantidad.getSelectedItem().toString() );
        monto = cantidad * 100;

        PayPalPayment payPalPayment =
                new PayPalPayment( new BigDecimal(String.valueOf(monto)),
                        "MXN", "Pedido por el Cliente", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    private void initWidgets(){
        String [] productos = {"($100) Torta de Jamon + Refresco",
                "($100) Sushi + Awa de mar",
                "($100) Pescado + Sal",
                "($5.00) tortilla + awa",
                "($20) Tamal de Elote + Awa"};
        String [] cantidades = {"1", "2", "3", "4", "5"};

        ArrayAdapter<String> adapterProductos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productos);
        ArrayAdapter<String> adapterCantidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cantidades);

        choose_producto.setAdapter(adapterProductos);;
        choose_cantidad.setAdapter(adapterCantidades);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode ==  RESULT_OK){
                PaymentConfirmation confirmation =  data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation!=null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, DetallesPagoActivity.class).putExtra("PaymentDetails", paymentDetails)
                        .putExtra("PaymentAmount", monto));
                    }catch (JSONException ex){
                        ex.printStackTrace();
                    }
                }
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Cancelada", Toast.LENGTH_LONG).show();
            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Invalida", Toast.LENGTH_LONG).show();
            }
        }else Toast.makeText(this, "Nel prro", Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode,resultCode, data);
    }



}
