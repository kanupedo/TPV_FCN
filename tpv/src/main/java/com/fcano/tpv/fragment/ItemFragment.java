package com.fcano.tpv.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fcano.tpv.R;
import com.fcano.tpv.modelos.Detalle;
import com.fcano.tpv.utils.JSON_Manager;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<NameValuePair> nameValuePairList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;
    private Scene mScene1;
    private Scene mScene2;
    private ViewGroup mSceneRoot;
    private OnFragmentInteractionListener mListener;
    JSON_Manager json_manager;
    ArrayList<Detalle> detalles = new ArrayList<>();
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private ViewGroup container;

    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(String param1, int param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    //El activity que contiene el Fragment se ha creado
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //El Fragment va a cargar su layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            Log.i("Param", String.valueOf(mParam2));
        }
        this.container = container;

        mSceneRoot = container;
        mScene1 = Scene.getSceneForLayout(mSceneRoot, R.layout.lista_pedidos, getActivity());
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.grid_pedidos, getActivity());
        //  return inflater.inflate(R.layout.lista_pedidos, container, false);

        return inflater.inflate(R.layout.lista_pedidos, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mListView = (ListView) getActivity().findViewById(R.id.lista_pedidos);
        mListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        nameValuePairList = new ArrayList<NameValuePair>();
        json_manager = new JSON_Manager(mListView, this.getActivity(), mParam2);
        json_manager.accessWebService();

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pedidos, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // We change the look of the icon every time the user toggles between list and grid.
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (null != item) {
            if (mListView instanceof ListView) {
                item.setIcon(R.drawable.ic_action_grid_light);
            } else {
                item.setIcon(R.drawable.ic_action_list_light);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toggle: {
                ActivityCompat.invalidateOptionsMenu(getActivity());
                cambiaFormato();
                return true;
            }
        }
        return false;
    }

    private void cambiaFormato() {
        // We save the current scrolling position before removing the current list.
        int first = mListView.getFirstVisiblePosition();
        // If the current list is a GridView, we replace it with a ListView. If it is a ListView,
        // a GridView.
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (mListView instanceof GridView) {
            mListView = (ListView) inflater.inflate(
                    R.layout.lista_pedidos, (ViewGroup) mListView.getParent(), false);
            mListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
            json_manager.setSimpleAdapter(mListView);

            Log.i("LISTVIEW", "TOGGLE");
            TransitionManager.go(mScene1);
        } else {
            mListView = (GridView) inflater.inflate(
                    R.layout.grid_pedidos, (ViewGroup) mListView.getParent(), false);
            mListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
            json_manager.setSimpleAdapter(mListView);
            Log.i("GRIDVIEW", "TOGGLE");
            TransitionManager.go(mScene2);
        }
        mListView.setSelection(first);
        // The new list is ready, and we replace the existing one with it.
        container.removeAllViews();
        container.addView(mListView);
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
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("LLamo desde: ", String.valueOf(parent.getContext()) + view.getContext());
        Log.i("Position: ", String.valueOf(position));
        Log.i("PositionParent: ", String.valueOf(parent.getItemAtPosition(position)));
        if (position >= 0) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            String text = textView.getText().toString();
            Log.i("Texto: ", text);
            mListener.onFragmentInteraction(parent.getContext(), position, text);
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
        public void onFragmentInteraction(Context context, int position, String campo);
    }

}
