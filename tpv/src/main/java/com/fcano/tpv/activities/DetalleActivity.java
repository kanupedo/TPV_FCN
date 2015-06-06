package com.fcano.tpv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fcano.tpv.R;
import com.fcano.tpv.adapters.Lista_adaptador;
import com.fcano.tpv.modelos.Detalle;
import com.fcano.tpv.utils.JSON_Manager;

import java.util.ArrayList;
import java.util.Iterator;

public class DetalleActivity extends ActionBarActivity {
    public static String nuevalinea = System.getProperty("line.separator");
    private ArrayList<Detalle> detalles;
    JSON_Manager json_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Pedido");
        detalles = new ArrayList<Detalle>();
        ListView listView = (ListView) findViewById(R.id.lista_detalles);
        //TextView txtDetalle = (TextView) findViewById(R.id.txt_detalle);
        String cad_detalle = "";
        double total = 0;

        // Imprimimos el Map con un Iterador
        Iterator it = ProductosActivity.listaDetalle.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Detalle detalle = ProductosActivity.listaDetalle.get(key);
            detalles.add(detalle);
            cad_detalle += detalle.getDetalle() + "   " + detalle.getTotal() + nuevalinea;
            total += detalle.getTotal();
        }
        listView.setAdapter(new Lista_adaptador(this, R.layout.detalle_master, detalles) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView textProd = (TextView) view.findViewById(R.id.txt_prod);
                textProd.setText(((Detalle) entrada).getDetalle());
                TextView textCant = (TextView) view.findViewById(R.id.tx_cant);
                String cant = String.valueOf(((Detalle) entrada).getCantidad());
                textCant.setText(cant);
                TextView textPvp = (TextView) view.findViewById(R.id.txt_pvp);
                String pvp = String.format("%.2f", ((Detalle) entrada).getTotal());
                textPvp.setText(pvp);
            }
        });
        TextView textTotal = (TextView) findViewById(R.id.editTotal);

        String cad = String.format("%.2f", total);
        textTotal.setText(cad);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*MenuItem item = menu.findItem(R.id.pedir);
        item.setIcon(R.drawable.ic_action_send_now);*/
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.pedir) {
            realizarPedido();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void realizarPedido() {
        MainActivity.num_pedido++;
        int linea = 1;
        for (Detalle detalle : detalles) {
            linea++;
            detalle.setCOD_PED(MainActivity.num_pedido);
            detalle.setLinea(linea);
            MainActivity.detalle = detalle;
            json_manager = new JSON_Manager(this);
            json_manager.insertar();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0); //abrimos y esperamos resultado*/

    }


}
