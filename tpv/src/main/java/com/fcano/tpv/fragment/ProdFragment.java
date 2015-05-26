package com.fcano.tpv.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fcano.tpv.R;
import com.fcano.tpv.modelos.Detalle;
import com.fcano.tpv.utils.JSON_Manager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProdFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;
    private int mParam3;
    private static ArrayList<Detalle> detalles;
    public ListView listView;
    JSON_Manager json_manager;
    private static boolean flag;
    private static OnFragmentInteractionListener mListener;
    private ViewGroup container;
    private static Context context;
    private int cant = 0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment ProdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProdFragment newInstance(String param1, int param2, int param3) {
        ProdFragment fragment = new ProdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public ProdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            Log.i("Param", String.valueOf(mParam2));
            context = getActivity();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lista_pedidos, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

       /* detalles = ProductosActivity.pedido;
       Log.i("SIZE: ", String.valueOf(detalles.size()));
        listView = (ListView) getActivity().findViewById(R.id.lista_pedidos);
        listView.setAdapter(new Lista_adaptador(getActivity(), R.layout.fragment_prod, detalles) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView textView = (TextView) view.findViewById(R.id.txt_prod);
                if (textView != null) {
                    textView.setText(((Detalle) entrada).getDetalle());
                    Log.i("^^", (String) textView.getText());
                }

                Button button = (Button) view.findViewById(R.id.bt_menos);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag = false;
                    }
                });
                Button button2 = (Button) view.findViewById(R.id.bt_mas);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag = true;
                       /* TextView textView1 = (TextView) v.findViewById(R.id.tx_cant);
                        String text = (String) textView1.getText();
                        int cant = Integer.parseInt(text);
                        if (cant < 10) cant++;
                    }
                });
            }
        });*/


        listView = (ListView) getActivity().findViewById(R.id.lista_pedidos);

        json_manager = new JSON_Manager(listView, this.getActivity(), mParam2);
        //json_manager = new JSON_Manager(null, this.getActivity(), mParam2);
        //  json_manager.accessWebService();
        // detalles=json_manager.getList();
        // Log.i("SIZE!!!:", String.valueOf(detalles.size()));*/
        json_manager.setUrl(mParam3);
        Log.i("cc: ", String.valueOf(mParam3));
        Log.i("URL: ", json_manager.getUrl());
        json_manager.accessWebService();
        detalles = json_manager.getList();
        Log.i("SIZE:", String.valueOf(detalles.size()));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            Log.i("Attach", "Atado");
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param l        The ListView where the click happened
     * @param v        The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id       The row id of the item that was clicked
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.i("LLamo desde: ", String.valueOf(v.getContext()));
        Log.i("Position: ", String.valueOf(position));
        Log.i("PositionParent: ", String.valueOf(l.getItemAtPosition(position)));
        Log.i("ID: ", String.valueOf(id));
        if (position >= 0) {
            TextView textView = (TextView) v.findViewById(android.R.id.text1);
            String text = textView.getText().toString();
            Log.i("Texto: ", text);
            dialogar(text);
            // mListener.onFragmentInteraction(v.getContext(), position, text);
        }
    }

    private void dialogar(String text) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_prod);
        dialog.setTitle(text);

        // TextView texto = (TextView) dialog.findViewById(R.id.tx_cant);
        Button menosButton = (Button) dialog.findViewById(R.id.bt_menos);
        // if button is clicked, close the custom dialog
        menosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cant > 0) {
                    cant--;
                    sumaResta(cant, dialog);
                }
            }
        });

        Button masButton = (Button) dialog.findViewById(R.id.bt_mas);
        // if button is clicked, close the custom dialog
        masButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cant < 10) {
                    cant++;
                    sumaResta(cant, dialog);
                }
            }
        });

        Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void sumaResta(int cant, Dialog dialog) {
        TextView texto = (TextView) dialog.findViewById(R.id.tx_cant);
        texto.setText(Integer.toString(cant));
    }


    public static void Posicionar(int cant, int tag) {
        if (tag >= 0) {
            mListener.onFragmentInteraction(context, tag, cant);
        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Context context, int position, int campo);
    }

}
