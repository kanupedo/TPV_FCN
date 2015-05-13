package com.fcano.tpv.fragment;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.fcano.tpv.R;
import com.fcano.tpv.activities.MesasActivity;
import com.fcano.tpv.modelos.Detalle;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Fernando on 07/04/2015.
 */
public class PedidosFragment extends Fragment {
    //Etiqueta Fragment
    public static final String pedTag = "PEDIDOS";

    //Parametros iniciales
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Context ctx;
    public int numLista = 0; //elemento actual de la lista
    private static int COD_RTN_ACT = 0;
    public static String KEY_LIST = "MESAS";
    public static List<NameValuePair> nameValuePairList;
    public static Detalle detalle;


    //Constructores
    public static PedidosFragment newInstance(String param1, String param2) {
        PedidosFragment fragment = new PedidosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PedidosFragment() {
    }

    //El activity que contiene el Fragment se ha creado
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    //El Fragment va a cargar su layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pedidos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ctx = getActivity();
        //Float Button
        final int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        final ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.fab_1);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

              /*  getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MesasFragment())
                        .commit();
                imageButton.setVisibility(view.INVISIBLE);*/
                Intent intent = new Intent(ctx, MesasActivity.class);
                intent.putExtra(KEY_LIST, numLista); //mandamos el numero del libro a visualizar
                startActivityForResult(intent, COD_RTN_ACT); //abrimos y esperamos resultado*/
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
