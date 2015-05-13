package com.fcano.tpv.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fcano.tpv.modelos.Mesas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 11/05/2014.
 */
public class MiDataBase extends SQLiteOpenHelper {

    private static final int VERSION_BASEDATOS = 1;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "MESAS";

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_MESAS = "CREATE TABLE \"MESA\" (\n" +
            "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "    \"nombre_mesa\" TEXT\n" +
            ")";


    // CONSTRUCTOR de la clase
    public MiDataBase(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);

    }

    private void dropDATABASE() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BASEDATOS);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dropDATABASE();
        db.execSQL(TABLA_MESAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BASEDATOS);
        onCreate(db);
    }

    public void insertarCONTACTO(int id, String nom) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("id", id);
            valores.put("nombre_mesa", nom);
            db.insert("MESA", null, valores);
            db.close();
        }
    }

    public void modificarCONTACTO(int id, String nom) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id", id);
        valores.put("nombre_mesa", nom);
        db.update("MESA", valores, "id=" + id, null);
        db.close();
    }

    public void borrarCONTACTO(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("MESA", "id=" + id, null);
        db.close();
    }

    public Mesas recuperarCONTACTO(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"id", "nombre_mesa"};
        Cursor c = db.query("MESA", valores_recuperar, "id=" + id,
                null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Mesas mesa = new Mesas(c.getInt(0), c.getString(1));
        db.close();
        c.close();
        return mesa;
    }

    public List<Mesas> recuperarCONTACTOS() {
        SQLiteDatabase db = getReadableDatabase();
        List<Mesas> lista_mesas = new ArrayList<Mesas>();
        String[] valores_recuperar = {"id", "nombre_mesa"};
        Cursor c = db.query("MESA", valores_recuperar,
                null, null, null, null, null, null);
        c.moveToFirst();
        do {
            Mesas mesa = new Mesas(c.getInt(0), c.getString(1));
            lista_mesas.add(mesa);
        } while (c.moveToNext());
        db.close();
        c.close();
        return lista_mesas;
    }

    public void insertarCONTACTOIncio() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("id", 1);
            valores.put("nombre_mesa", "MESA 1");
            db.insert("MESA", null, valores);
            valores.put("id", 2);
            valores.put("nombre_mesa", "MESA 2");
            db.insert("MESA", null, valores);
            valores.put("id", 3);
            valores.put("nombre_mesa", "MESA 3");
            db.insert("MESA", null, valores);
            valores.put("id", 4);
            valores.put("nombre_mesa", "MESA 4");
            db.insert("MESA", null, valores);
            valores.put("id", 5);
            valores.put("nombre_mesa", "MESA 5");
            db.insert("MESA", null, valores);
            db.close();
        }
    }

    /*private void copiarBaseDatos() {
        String ruta = "/data/data/com.example.sqlite/databases/";
        String archivo = "contactos.db";
        File archivoDB = new File(ruta + archivo);
        if (!archivoDB.exists()) {
            try {
                InputStream IS = getApplicationContext().getAssets().open(archivo);
                OutputStream OS = new FileOutputStream(archivoDB);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = IS.read(buffer)) > 0) {
                    OS.write(buffer, 0, length);
                }
                OS.flush();
                OS.close();
                IS.close();
            } catch (FileNotFoundException e) {
                Log.e("ERROR", "Archivo no encontrado, " + e.toString());
            } catch (IOException e) {
                Log.e("ERROR", "Error al copiar la Base de Datos, " + e.toString());
            }
        }

          "insert into mesas values (1,'MESA 1');\n" +
            "insert into mesas values (2,'MESA 2');\n" +
            "insert into mesas values (3,'MESA 3') ;\n" +
            "insert into mesas values (4,'MESA 4') ;\n" +
            "insert into mesas values (5,\"MESA 5\") ;\n" +
            "insert into mesas values (6,\"MESA 6\") ;\n" +
            "insert into mesas values (7,\"MESA 7\");\n" +
            "insert into mesas values (8,\"MESA 8\");\n" +
            "insert into mesas values (9,\"MESA 9\") ;\n" +
            "insert into mesas values (10,\"MESA 10\");";
    }*/
}



