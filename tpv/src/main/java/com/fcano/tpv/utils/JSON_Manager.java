package com.fcano.tpv.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fcano.tpv.R;
import com.fcano.tpv.activities.MainActivity;
import com.fcano.tpv.activities.ProductosActivity;
import com.fcano.tpv.adapters.Lista_adaptador;
import com.fcano.tpv.modelos.Detalle;
import com.fcano.tpv.modelos.Producto;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.StrictMode.ThreadPolicy;
import static android.os.StrictMode.setThreadPolicy;


public class JSON_Manager {

    private String jsonResult;
    private String php1 = "http://fsolutions.comuf.com/php/getMesas.php";
    private String php2 = "http://fsolutions.comuf.com/php/getFamilias.php";
    private String php3 = "http://fsolutions.comuf.com/php/getProductos.php?cc=";
    private String php4 = "http://fsolutions.comuf.com/php/pedido.php";
    private ListView listView;
    private GridView gridView;
    private Context context;
    private String url;
    private String mainNode, childNode;
    private int cc = 0;
    private ArrayList<String> list;
    public static ArrayList<Detalle> listaNombres;
    private static List<Map<String, String>> List;
    int i = 0;
    private String[] campos = {"COD_FAM", "COD_PROD", "NOMBRE_PROD", "DESCRIPCION", "PRECIO", "IVA", "PVP", "ACTIVA"};

    public JSON_Manager(Context contex) {
        this.context = contex;
    }

    public JSON_Manager(AbsListView listView, Context context, int index) {
        listaNombres = new ArrayList<Detalle>();

        switch (index) {
            case 1:
                url = this.php1;
                mainNode = "mesas_info";
                childNode = "NOMBRE";
                break;
            case 2:
                url = this.php2;
                mainNode = "familias_info";
                childNode = "NOM_FAM";
                break;
            case 3:
                url = this.php3;
                mainNode = "productos_info";
                childNode = "NOMBRE_PROD";
                break;
        }
        if (listView instanceof ListView) {
            this.listView = (ListView) listView;
            Log.i("LISTVIEW", "LISTVIEW");
        } else if (listView instanceof GridView) {
            this.gridView = (GridView) listView;
            Log.i("LISTVIEW", "GRIDVIEW");
        }
        this.context = context;
    }

    public JSON_Manager(ArrayList<String> list) {
        this.list = list;

    }


    public String getUrl() {
        return url;
    }

/* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_custom_list);
        accessWebService();
    }*/


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // e.printStackTrace();
              /*  Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();*/
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url});
    }

    // build hash set for list view
    public void ListDrwaer() {
        List = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray(mainNode);

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                //  Log.i("conexion ", jsonChildNode.toString());
                if (childNode.equals("NOMBRE_PROD")) {
                    final int COD_FAM = jsonChildNode.optInt(campos[0]);
                    final int COD_PROD = jsonChildNode.optInt(campos[1]);
                    String nombre_prod = jsonChildNode.optString(campos[2]);
                    String descripcion = jsonChildNode.optString(campos[3]);
                    float precio = (float) jsonChildNode.optDouble(campos[4]);
                    int iva = jsonChildNode.optInt(campos[5]);
                    float pvp = (float) jsonChildNode.optDouble(campos[6]);
                    boolean activa = jsonChildNode.optBoolean(campos[7]);
                    Producto producto = new Producto(COD_FAM, COD_PROD, nombre_prod, descripcion, precio, iva, pvp, activa);
                    ProductosActivity.pedido.add(producto);
                    // Log.i("DETALLE", listaNombres.get(i).getDetalle());
                } else {
                    String name = jsonChildNode.optString(childNode);
                    //String number = jsonChildNode.optString("employee no");
                    String outPut = name;
                    List.add(createLista("lista", outPut));
                }


            }
        } catch (JSONException e) {
           /* Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();*/
        }
        if (cc == 0) {
            Log.i("cc:", String.valueOf(cc));
            if (listView != null) {
                if (listView instanceof ListView) {
                    setSimpleAdapter(listView);
                } else {
                    setSimpleAdapter(gridView);
                }
            }
        } else {
            Log.i("cc:", String.valueOf(cc));
            setCustomAdapter(listView);
        }
    }

    private void setCustomAdapter(final ListView listView) {


        listView.setAdapter(new Lista_adaptador(context, R.layout.fragment_prod, ProductosActivity.pedido) {
            int pos = 0;

            @Override
            public void onEntrada(Object entrada, View view) {
                pos = ProductosActivity.pedido.indexOf(entrada);
                TextView textView = (TextView) view.findViewById(R.id.txt_prod);
                // pos = listaNombres.indexOf(entrada);
                if (textView != null) {
                    textView.setText(((Producto) entrada).getDescripcion());


                }
                final TextView tx_cant = (TextView) view.findViewById(R.id.tx_cant);

                final Button button_menos = (Button) view.findViewById(R.id.bt_menos);
                button_menos.setTag(pos);
                button_menos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("^^", "CLICK -");
                        View view1 = (View) v.getParent();
                        TextView textView1 = (TextView) view1.findViewById(R.id.tx_cant);
                        String text = (String) textView1.getText();
                        int cant = Integer.parseInt(text);
                        if (cant > 0) {
                            cant--;
                            //      String pos = (String) button.getTag();
                            // ProdFragment.Posicionar(cant,Integer.parseInt(pos));
                            // View view2= view1.findViewById(R.id.tx_cant);
                            //  view1=lista_adaptador.getView( Integer.parseInt((String) button.getTag()),view2,null);
                            TextView textView = (TextView) view1.findViewById(R.id.tx_cant);
                            textView.setText(Integer.toString(cant));
                            if (cant == 0) {
                                TextView textView2 = (TextView) view1.findViewById(R.id.txt_prod);
                                if (ProductosActivity.listaDetalle.containsKey(textView2.getText())) {
                                    ProductosActivity.listaDetalle.remove(textView2.getText());
                                }
                                Toast.makeText(v.getContext(), "Quitado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.i("CANT", String.valueOf(cant));
                        Log.i("POS:", String.valueOf(button_menos.getTag()));
                    }

                });
                final Button button_mas = (Button) view.findViewById(R.id.bt_mas);
                button_mas.setTag(pos);
                button_mas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("^^", "CLICK +");
                        View view1 = (View) v.getParent();
                        TextView textView1 = (TextView) view1.findViewById(R.id.tx_cant);
                        String text = (String) textView1.getText();
                        int cant = Integer.parseInt(text);
                        if (cant < 10) {
                            cant++;
                            //  String pos = (String) button.getTag();
                            // ProdFragment.Posicionar(cant,Integer.parseInt(pos));
                            // View view2= view1.findViewById(R.id.tx_cant);
                            //    view1=lista_adaptador.getView(Integer.parseInt(button.getTag().toString()),view2,null);
                            TextView textView = (TextView) view1.findViewById(R.id.tx_cant);
                            textView.setText(Integer.toString(cant));
                            Toast.makeText(v.getContext(), "Anadido", Toast.LENGTH_SHORT);
                            if (cant > 0) {
                                TextView textView2 = (TextView) view1.findViewById(R.id.txt_prod);
                                Producto producto = ProductosActivity.pedido.get((Integer) button_mas.getTag());
                                Log.i("INDEX:", String.valueOf(button_mas.getTag()));
                                Log.i("SIZE:", String.valueOf(ProductosActivity.pedido.size()));

                                Log.i("PRODUCTO:", producto.getDescripcion());

                                Detalle detalle;
                                if (ProductosActivity.listaDetalle.containsKey(producto.getDescripcion())) {
                                    Log.i("CONTIENE", "SI");
                                    detalle = ProductosActivity.listaDetalle.get(producto.getDescripcion());
                                    detalle.setCantidad(cant);
                                    detalle.setTotal((cant * producto.getPvp()));
                                    ProductosActivity.listaDetalle.put(producto.getDescripcion(), detalle);
                                } else {
                                    Log.i("CONTIENE", "NO");
                                    detalle = new Detalle();
                                    detalle.setCOD_PROD(producto.getCOD_PROD());
                                    detalle.setDetalle(producto.getDescripcion());
                                    detalle.setCantidad(cant);
                                    detalle.setTotal((cant * producto.getPvp()));
                                    ProductosActivity.listaDetalle.put(producto.getDescripcion(), detalle);
                                }

                                Toast.makeText(v.getContext(), "Anadido", Toast.LENGTH_SHORT).show();

                            }
                        }

                        Log.i("CANT:", String.valueOf(cant));
                        Log.i("POS:", String.valueOf(button_mas.getTag()));
                    }
                });
            }

        });
        cc = 0;
    }

    public void setSimpleAdapter(View view) {

        if (view instanceof ListView) {
            Log.i("LISTVIEW", "LISTVIEW2");
            listView = (ListView) view;
            SimpleAdapter simpleAdapter = new SimpleAdapter(this.context, List,
                    android.R.layout.simple_list_item_1,
                    new String[]{"lista"}, new int[]{android.R.id.text1});
            listView.setAdapter(simpleAdapter);
        } else if (view instanceof GridView) {
            gridView = (GridView) view;
            SimpleAdapter simpleAdapter = new SimpleAdapter(this.context, List,
                    android.R.layout.simple_list_item_1,
                    new String[]{"lista"}, new int[]{android.R.id.text1});
            gridView.setAdapter(simpleAdapter);
        }
    }

    public ArrayList<Detalle> getList() {

        return this.listaNombres;
    }


    private HashMap<String, String> createLista(String name, String number) {
        HashMap<String, String> mesaNameNo = new HashMap<String, String>();
        mesaNameNo.put(name, number);
        return mesaNameNo;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public void setUrl(int cc) {
        this.url = this.url + cc;
        setCc(cc);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void insertar() {
        String cc = String.valueOf(MainActivity.detalle.getCOD_PED());
        String linea = String.valueOf(MainActivity.detalle.getLinea());
        String cod_prod = String.valueOf(MainActivity.detalle.getCOD_PROD());
        String desc_prod = MainActivity.detalle.getDetalle();
        String cantidad = String.valueOf(MainActivity.detalle.getCantidad());
        String pvp = String.format("%.2f", MainActivity.detalle.getTotal());
        String mURL = "http://fsolutions.comuf.com/php/pedido.php?cc=" + cc + "&linea=" + linea + "&cod_prod=" + cod_prod + "&desc_prod=" + desc_prod + "&cantidad=" + cantidad + "&pvp=" + pvp;

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpPost = new HttpGet(mURL);
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();

        setThreadPolicy(policy);
        // httpPost.setEntity(new UrlEncodedFormEntity(MainActivity.nameValuePairList));
        String response = "";
        Log.i("LocAndroid Response HTTP Threas", "Ejecutando get 0: " + mURL);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            Log.i("LocAndroid Response HTTP", response);
            response = httpClient.execute(httpPost, responseHandler);

            Toast.makeText(context, "El pedido ha sido enviado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error en el envio de la informacion, verifique su conexion a internet y vuelva a intentarlo.", Toast.LENGTH_SHORT).show();
        }


    }

    public void pedir() {
        try {
            httpGetData("http://fsolutions.comuf.com/php/pedido.php?cc=" + MainActivity.detalle.getCOD_PED() + "&linea=" + MainActivity.detalle.getLinea() + "&cod_prod=" + MainActivity.detalle.getCOD_PROD() + "&cantidad=1");

            Toast.makeText(context, "El dato ha sido enviado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error en el envio de la informacion, verifique su conexion a internet y vuelva a intentarlo.", Toast.LENGTH_SHORT).show();
        }
    }

    public String httpGetData(String mURL) {
        String response = "";
        mURL = mURL.replace(" ", "%20");
        Log.i("Log Response HTTP", "Ejecutando get 0: " + mURL);
        HttpClient httpclient = new DefaultHttpClient();

        Log.i("Log Response HTTP", "Ejecutando get 1");
        HttpGet httppost = new HttpGet(mURL);
        Log.i("Log Response HTTP", "Ejecutando get 2");
        try {


            Log.i("Log Response HTTP", "Ejecutando get");
            // Execute HTTP Post Request
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpclient.execute(httppost, responseHandler);
            Log.i("Log Response HTTP", response);
        } catch (ClientProtocolException e) {
            Log.i("Log Response HTTP", e.getMessage());
            // TODO Auto-generated catch block
        } catch (IOException e) {

            Log.i("Log Response HTTP", e.getMessage());
            // TODO Auto-generated catch block
        }
        return response;

    }
}
