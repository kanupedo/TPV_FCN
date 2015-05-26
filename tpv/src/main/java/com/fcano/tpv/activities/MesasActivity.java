package com.fcano.tpv.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.transition.Scene;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.fcano.tpv.R;
import com.fcano.tpv.fragment.ItemFragment;
import com.fcano.tpv.modelos.Detalle;
import com.fcano.tpv.utils.JSON_Manager;
import com.fcano.tpv.utils.MiDataBase;

import org.apache.http.NameValuePair;

import java.util.List;

public class MesasActivity extends ActionBarActivity implements ItemFragment.OnFragmentInteractionListener {
    private static String titulo;
    private CharSequence mTitle;
    MiDataBase MIDB;
    ListView lvCustomList;
    GridView gvCustumGridView;
    JSON_Manager json_manager;
    Context ctx;
    public int numLista = 0; //elemento actual de la lista
    private static int COD_RTN_ACT = 0;
    public static String KEY_LIST = "MESAS";
    public static List<NameValuePair> nameValuePairList;
    public static Detalle detalle;
    //public AdapterTransition adapterTransition;
    private FrameLayout mCover;
    private FrameLayout mContent;
    private AbsListView mAbsListView;
    private static final String STATE_IS_LISTVIEW = "is_listview";
    private static final int ROOT_ID = 1;
    boolean isListView;
    private Scene mScene1;
    private Scene mScene2;
    ViewGroup mSceneRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        ctx = this;
        // lvCustomList = (ListView) findViewById(R.id.list);
      /*  lvCustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //gestionamos la pulsacion sobre la lista
            public void onItemClick(AdapterView<?>parent, View view, int position, long id){
                //nos quedamos con el id plsado para recrear
                numLista=position +1; //la posicion es el indice del array
                //detalle = new Detalle();
                MainActivity.detalle.setCOD_PED(numLista);
                //lanzar otra actiidad desde un intent
                Intent intent = new Intent(ctx,FamiliasActivity.class);
                intent.putExtra(KEY_LIST,numLista); //mandamos el numero del libro a visualizar
                startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado
            }
        });*/
       /* nameValuePairList = new ArrayList<NameValuePair>();
        json_manager = new JSON_Manager(lvCustomList, this,1);
        json_manager.accessWebService();*/
        //lvCustomList = (ListView)findViewById(R.id.list);
        // json_manager = new JSON_Manager(lvCustomList, ctx,1);
        //  json_manager.accessWebService();
        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.content, ItemFragment.newInstance("MESAS", 1))
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putBoolean(STATE_IS_LISTVIEW, mAbsListView instanceof ListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // MenuItem item = menu.findItem(R.id.action_toggle);
        // getMenuInflater().inflate(R.menu.pedidos_main, menu);

        return true;
    }

    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            //int id = item.getItemId();
           if (id == R.id.action_toggle) {

                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // We change the look of the icon every time the user toggles between list and grid.
       /* MenuItem item = menu.findItem(R.id.action_toggle);
        if (null != item) {
            if (mAbsListView instanceof ListView) {
                item.setIcon(R.drawable.ic_action_grid_light);
                item.setTitle(R.string.show_as_grid);
                isListView = true;
            } else if (mAbsListView instanceof GridView) {
                isListView = false;
                item.setIcon(R.drawable.ic_action_list_light);
                item.setTitle(R.string.show_as_list);
            }
        }*/
        return true;
    }

    public void restoreActionBar() {
        //Intent intent = getIntent(); //recoge el intent que ha originado el lanzamiento
        // numLista = intent.getIntExtra(MesasActivity.KEY_LIST, 0); //los datos que nos pasan;
        // titulo = intent.getStringExtra("TITLE");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(titulo);
    }


    @Override
    public void onFragmentInteraction(Context context, int position, String campo) {
        if (this.ctx.equals(context)) {
            //nos quedamos con el id pulsado para recrear
            numLista = position + 1; //la posicion es el indice del array
            //detalle = new Detalle();
            MainActivity.detalle.setCOD_PED(numLista);
            //lanzar otra actiidad desde un intent
            Intent intent = new Intent(ctx, FamiliasActivity.class);
            intent.putExtra(KEY_LIST, numLista); //mandamos el numero del libro a visualizar
            intent.putExtra(KEY_LIST, campo);
            startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado*/
        }
    }
}
