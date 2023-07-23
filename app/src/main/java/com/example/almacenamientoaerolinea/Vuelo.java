package com.example.almacenamientoaerolinea;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Vuelo extends SQLiteOpenHelper {
    public Vuelo(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase BDVuelos) {
        BDVuelos.execSQL("create table vuelos(id int primary key, aerolinea text, origen text, destino text, fecha text, hora text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }

}