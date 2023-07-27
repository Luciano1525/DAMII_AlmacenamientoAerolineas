package com.example.almacenamientoaerolinea;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Reservacion extends SQLiteOpenHelper {
    public Reservacion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BDReservaciones) {
        BDReservaciones.execSQL("create table reservacion(id int primary key, cliente text, aerolinea text, origen text, destino text, fecha text, hora text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }


}
