package com.fcano.tpv.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fcano.tpv.R;
import com.fcano.tpv.fragment.ProdFragment;
import com.fcano.tpv.modelos.Detalle;
import com.fcano.tpv.modelos.Producto;
import com.fcano.tpv.utils.JSON_Manager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductosActivity extends ActionBarActivity implements ProdFragment.OnFragmentInteractionListener {
    private String titulo;
    JSON_Manager json_manager;
    Context ctx;
    ListView lstProductos;
    public int numLista = 0; //elemento actual de la lista
    public static String KEY_LIST = "PRODUCTOS";
    private static int COD_RTN_ACT = 0;
    private static int linea = 0;
    private static int cantidad = 1;
    public ArrayList<String> list;
    public static ArrayList<Producto> pedido;
    public static HashMap<String, Detalle> listaDetalle = new HashMap<>();
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Intent intent = getIntent(); //recoge el intent que ha originado el lanzamiento
        numLista = intent.getIntExtra(FamiliasActivity.KEY_LIST, 0); //los datos que nos pasan;
        titulo = intent.getStringExtra("TITLE");
        TextView textView = (TextView) findViewById(R.id.txFamilia);
        String cad = intent.getStringExtra("PRODUCTO");
        textView.setText(cad);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(titulo);
        list = new ArrayList<String>();
        pedido = new ArrayList<Producto>();

        ctx = this;

       /* json_manager = new JSON_Manager(null, this, 3);
        json_manager.setUrl(numLista);
        Log.i("URL: ", json_manager.getUrl());
        json_manager.accessWebService();
        pedido = json_manager.getList();*/
        Log.i("NumLista:", String.valueOf(numLista));

        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.content, ProdFragment.newInstance("PRODUCTOS", 3, numLista))
                    .commit();
            ;
        }


        //lstProductos = (ListView) findViewById(R.id.pedido_prod);
       /* lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() { //gestionamos la pulsacion sobre la lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (numLista == position + 1) {
                    cantidad++;
                } else {
                    cantidad = 1;
                    numLista = position + 1; //la posicion es el indice del array
                    //lanzar otra actividad desde un intent
                    linea++;
                }
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String text = textView.getText().toString();

                Toast.makeText(ctx, String.valueOf(cantidad) + " " + text, Toast.LENGTH_SHORT).show();

                Detalle detail = comprobarDetalle(position);
                detail.setCOD_PED(MainActivity.detalle.getCOD_PED());
                detail.setLinea(linea);
                detail.setCOD_PROD(MainActivity.detalle.getCOD_PROD());
                detail.setDetalle(text);
                detail.setCantidad(cantidad);
                pedido.add(detail);
                /*MainActivity.detalle.setLinea(linea);
                MainActivity.nameValuePairList.add(new BasicNameValuePair("cc",String.valueOf(MainActivity.detalle.getCOD_PED())));
                MainActivity.nameValuePairList.add(new BasicNameValuePair("linea",String.valueOf(linea)));
                MainActivity.nameValuePairList.add(new BasicNameValuePair("cod_prod",String.valueOf(MainActivity.detalle.getCOD_PROD())));
                MainActivity.nameValuePairList.add(new BasicNameValuePair("cantidad",String.valueOf(cantidad)));*/
                //MainActivity.nameValuePairList.
                //json_manager = new JSON_Manager(ctx);
                //json_manager.insertar();

                //  Intent intent = new Intent(ctx,MesasActivity.class);
                //   intent.putExtra(KEY_LIST,numLista); //mandamos el numero del libro a visualizar
                //    startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado*/
                //  Log.i("pedido", pedido.get(position).getDetalle()+": "+cantidad);
        // }
        //   });


        //  json_manager = new JSON_Manager((ListView) findViewById(R.id.listaProducto), this, 3);
        //  json_manager.setUrl(numLista);
        //Log.i("URL: ",json_manager.getUrl());
        //  json_manager.accessWebService();
//        pedido = json_manager.getList();
        // Log.i(pedido.size());


    }

  /*  private Detalle comprobarDetalle(int pos) {
        Detalle d = new Detalle();
        for (Detalle detalle : pedido) {
            for (String nombre : list) {
                if (detalle.getDetalle().equals(nombre)) {
                    d = detalle;
                }
            }
        }
        return d;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate((R.menu.pedidos), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if ((id == R.id.action_toggle) && (listaDetalle.size() > 0)) {
            // Toast.makeText(ctx, "TIRITI", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ctx, DetalleActivity.class);
            intent.putExtra(KEY_LIST, numLista); //mandamos el numero del libro a visualizar
            startActivityForResult(intent, COD_RTN_ACT);
            return true;
        } else if ((id == R.id.action_toggle) && (listaDetalle.size() > 0)) {
            Toast.makeText(ctx, "Debe seleccionar al menos un producto", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreActionBar() {
        // Intent intent = getIntent(); //recoge el intent que ha originado el lanzamiento
        //  numLista = intent.getIntExtra(ProductosActivity.KEY_LIST, 0); //los datos que nos pasan;
        //   titulo = intent.getStringExtra("TITLE");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(titulo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = new Intent(ctx, FamiliasActivity.class);
        intent.putExtra(KEY_LIST, numLista); //mandamos el numero del libro a visualizar
        startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado
        return true;
    }


    @Override
    public void onFragmentInteraction(Context context, int position, int cant) {
        // if (this.ctx.equals(context)) {
            if (numLista == position + 1) {
                cantidad++;
            } else {
                cantidad = 1;
                numLista = position + 1; //la posicion es el indice del array
                //lanzar otra actividad desde un intent
                linea++;
            }


        Toast.makeText(ctx, String.valueOf(cantidad) + " ", Toast.LENGTH_SHORT).show();

          /*  Detalle detail = comprobarDetalle(position);
            detail.setCOD_PED(MainActivity.detalle.getCOD_PED());
            detail.setLinea(linea);
            detail.setCOD_PROD(MainActivity.detalle.getCOD_PROD());
            detail.setDetalle(list.get(position));
            detail.setCantidad(cantidad);
            pedido.add(detail);*/

    }


}
