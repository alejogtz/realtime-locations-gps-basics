package io.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

import io.demo.Models.Pedido;
import io.demo.Models.Producto;
import io.demo.PayPal.Config;

public class HacerCompra extends ListActivity {

    // Banderas / Flags
    private Integer monto;
    private String descripcion;

    // PayPal
    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.PAYPAL_CLIENT_ID);

    // SuperClass
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_compra);

        // PayPal : iniciar Servicio
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        // ListView
        ArrayList<Producto> productos = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            productos.add(new Producto());
        }
        MyAdapter myAdapter = new MyAdapter(productos);
        setListAdapter(myAdapter);
    }


    // Subclass
    private class MyAdapter extends BaseAdapter {
        // Data
        ArrayList<Producto> productos;


        public MyAdapter(ArrayList<Producto> productos) {
            this.productos = productos;
        }

        @Override
        public int getCount() {
            return productos.size();
        }

        @Override
        public Object getItem(int i) {
            return productos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return productos.get(i).hashCode(); // A ver
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            // Inflate Layout Item
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_producto, null);
            }

            // Item
            Producto currentpedido = productos.get(i);

            //Widgets
            TextView txtDescripcion = convertView.findViewById(R.id.compra_item_nombretextview);
            TextView txtTotal = convertView.findViewById(R.id.compra_item_totaltextview);

            Button comprar = convertView.findViewById(R.id.compra_item_btncomprar);
            Spinner cantidad = convertView.findViewById(R.id.compra_item_cantidadspinner);

            // Configurar Widgets
            String[] cantidades = {"1", "2", "3", "4", "5"};
            ArrayAdapter<String> cantidadesadapter = new ArrayAdapter<String>(HacerCompra.this, android.R.layout.simple_spinner_item, cantidades);
            cantidad.setAdapter(cantidadesadapter);
            txtDescripcion.setText(currentpedido.getDescripcion());
            txtTotal.setText(String.valueOf(currentpedido.getTotal()));


            //Funcionalidad
            comprar.setOnClickListener(view -> {
                // TODO: Actualizar el estado del pedido xD
                // Capturar datos
                String descripcion = txtDescripcion.getText().toString();
                currentpedido.setCantidad( Integer.parseInt(cantidad.getSelectedItem().toString()) );
                Integer total = currentpedido.getTotal();

                procesarPago(descripcion, total);
            });

            cantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    currentpedido.setCantidad(Integer.parseInt(cantidad.getSelectedItem().toString()));
                    txtTotal.setText(String.valueOf(currentpedido.getTotal()));
                    txtDescripcion.setText(currentpedido.getDescripcion());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            return convertView;
        }

    }

    private void procesarPago(String descripcion, int monto) {
        this.descripcion = descripcion;
        this.monto = monto;
        // Cargar producto  y lanzar activity
        PayPalPayment payPalPayment =
                new PayPalPayment(new BigDecimal(String.valueOf(monto)),
                        "MXN", descripcion, PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode ==  RESULT_OK){
                PaymentConfirmation confirmation =  data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation!=null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().getJSONObject("response").toString(); //toString(4);
                        Log.e("ObjJson", paymentDetails);
                        startActivity(new Intent(this, DetallesPagoActivity.class).putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", monto).putExtra("Descripcion", this.descripcion ) );
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
