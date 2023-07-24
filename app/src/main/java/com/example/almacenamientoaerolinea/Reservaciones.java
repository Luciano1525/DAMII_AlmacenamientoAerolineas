package com.example.almacenamientoaerolinea;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Reservaciones extends SQLiteOpenHelper  {
    public Reservaciones(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase BDReservaciones) {
        BDReservaciones.execSQL("create table reservaciones(id int primary key, cliente text, aerolinea text, origen text, destino text, fecha text, hora text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }

}
