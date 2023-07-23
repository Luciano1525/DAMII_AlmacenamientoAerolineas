package com.example.almacenamientoaerolinea;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Aerolinea extends  SQLiteOpenHelper {
    public Aerolinea(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BDAerolinea) {
        BDAerolinea.execSQL("create table aerolinea(id int primary key, nombre text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }

}
