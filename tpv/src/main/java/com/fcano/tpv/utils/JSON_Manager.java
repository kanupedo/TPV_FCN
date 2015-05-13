package com.fcano.tpv.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fcano.tpv.activities.MainActivity;
import com.fcano.tpv.modelos.Detalle;

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
    private int cc;
    private ArrayList<String> list;
    public static ArrayList<Detalle> listaNombres;
    private static List<Map<String, String>> List;

    public JSON_Manager(Context contex) {
        this.context = contex;
    }

    public JSON_Manager(AbsListView listView, Context context, int index) {
        listaNombres = new ArrayList<>();

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
                String name = jsonChildNode.optString(childNode);
                Detalle detalle = new Detalle();
                detalle.setDetalle(name);
                listaNombres.add(detalle);
                // Log.i("DETALLE", listaNombres.get(i).getDetalle());
                //String number = jsonChildNode.optString("employee no");
                String outPut = name;
                List.add(createLista("lista", outPut));
            }
        } catch (JSONException e) {
           /* Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();*/
        }

        if (listView instanceof ListView) {
            setSimpleAdapter(listView);
        } else {
            setSimpleAdapter(gridView);
        }


    }

    public void setSimpleAdapter(View view) {

        if (view instanceof ListView) {
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
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void insertar() {
        String cc = String.valueOf(MainActivity.detalle.getCOD_PED());
        String linea = String.valueOf(MainActivity.detalle.getLinea());
        String cod_prod = String.valueOf(MainActivity.detalle.getCOD_PROD());
        String mURL = "http://fsolutions.comuf.com/php/pedido.php?cc=" + cc + "&linea=" + linea + "&cod_prod=" + cod_prod + "&cantidad=1";
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
