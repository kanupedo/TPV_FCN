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

import com.fcano.tpv.R;
import com.fcano.tpv.fragment.ItemFragment;
import com.fcano.tpv.utils.JSON_Manager;

public class FamiliasActivity extends ActionBarActivity implements ItemFragment.OnFragmentInteractionListener {
    private static String titulo;
    JSON_Manager json_manager;
    Context ctx;
    ListView lstFamilia;
    public int numLista = 0; //elemento actual de la lista
    public static String KEY_LIST = "FAMILIAS";
    private static int COD_RTN_ACT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familias);
        Intent intent = getIntent(); //recoge el intent que ha originado el lanzamiento
        int pos = intent.getIntExtra(MainActivity.KEY_LIST, 0); //los datos que nos pasan;
        if (titulo == null) {
            titulo = intent.getStringExtra(MainActivity.KEY_LIST);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(titulo);

        ctx = this;
        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.content, ItemFragment.newInstance("FAMILIAS", 2))
                    .commit();
            ;
        }
      /*  lstFamilia = (ListView) findViewById(R.id.listaFamilia);
        lstFamilia.setOnItemClickListener(new AdapterView.OnItemClickListener() { //gestionamos la pulsacion sobre la lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //nos quedamos con el id plsado para recrear
                numLista = position + 1; //la posicion es el indice del array
                MainActivity.detalle.setCOD_PROD(numLista);
                Intent intent = new Intent(ctx, ProductosActivity.class);
                intent.putExtra(KEY_LIST, numLista);
                intent.putExtra("TITLE", titulo);
                intent.putExtra("PRODUCTO",String.valueOf(lstFamilia.getItemAtPosition(position)));
                startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado
            }
        });
        json_manager = new JSON_Manager(lstFamilia, this, 2);
        json_manager.accessWebService();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      /*  if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void restoreActionBar() {
        //    Intent intent = getIntent(); //recoge el intent que ha originado el lanzamiento
        //   numLista = intent.getIntExtra(FamiliasActivity.KEY_LIST, 0); //los datos que nos pasan;
        //   titulo = intent.getStringExtra("TITLE");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(titulo);
        Log.i("TITULO", titulo);
    }

    @Override
    public void onFragmentInteraction(Context context, int position, String campo) {
        if (this.ctx.equals(context)) {
            //nos quedamos con el id plsado para recrear
            numLista = position + 1; //la posicion es el indice del array
            MainActivity.detalle.setCOD_PROD(numLista);
            Intent intent = new Intent(ctx, ProductosActivity.class);
            intent.putExtra(KEY_LIST, numLista);
            intent.putExtra("TITLE", titulo);
            intent.putExtra("PRODUCTO", campo);
            startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado
        }
    }
}
