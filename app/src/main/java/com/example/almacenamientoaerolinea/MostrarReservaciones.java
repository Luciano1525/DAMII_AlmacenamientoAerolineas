package com.example.almacenamientoaerolinea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MostrarReservaciones extends AppCompatActivity {
    private TableLayout tbReservaciones;
    private Button btnReservas, btnSalir3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_reservaciones);

        tbReservaciones = (TableLayout) findViewById(R.id.tbReservaciones);

        // Crear una instancia de la base de datos y Obtener todos los datos de la tabla
        Reservacion oper1 = new Reservacion(this, "operacion2", null, 1);
        SQLiteDatabase BBReservacion = oper1.getWritableDatabase();
        Cursor fila = BBReservacion.rawQuery("select * from reservacion", null);


        // Recorrer el cursor y crear las filas y columnas en el TableLayout
        if (fila.moveToFirst()) {
            do {
                int id = fila.getInt(fila.getColumnIndex("id"));
                String cliente = fila.getString(fila.getColumnIndex("cliente"));
                String aerolinea = fila.getString(fila.getColumnIndex("aerolinea"));
                String origen = fila.getString(fila.getColumnIndex("origen"));
                String destino = fila.getString(fila.getColumnIndex("destino"));
                String fecha = fila.getString(fila.getColumnIndex("fecha"));
                String hora = fila.getString(fila.getColumnIndex("hora"));
                String espacio = "     ";

                // Crear una nueva fila
                TableRow row = new TableRow(this);

                // Crear textviews para cada columna y agregar los datos
                TextView ID = new TextView(this);
                ID.setText(String.valueOf(id + espacio));
                row.addView(ID);

                TextView Cliente = new TextView(this);
                Cliente.setText(cliente + espacio);
                row.addView(Cliente);

                TextView Aerolinea = new TextView(this);
                Aerolinea.setText(aerolinea + espacio);
                row.addView(Aerolinea);

                TextView Origen = new TextView(this);
                Origen.setText(origen + espacio);
                row.addView(Origen);

                TextView Destino = new TextView(this);
                Destino.setText(destino + espacio);
                row.addView(Destino);

                TextView Fecha = new TextView(this);
                Fecha.setText(fecha + espacio);
                row.addView(Fecha);

                TextView Hora = new TextView(this);
                Hora.setText(hora + espacio);
                row.addView(Hora);


                // Agregar la fila al TableLayout
                tbReservaciones.addView(row);

            } while (fila.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        fila.close();
        BBReservacion.close();

        //Boton para consultar reserva
        btnReservas = (Button) findViewById(R.id.btnReservas);
        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "ConsultarReservas");
                Intent intent = new Intent(MostrarReservaciones.this, ConsultarReservas.class);
                startActivity(intent);
            }
        });





        //Boton para consultar reservas
        btnReservas = (Button) findViewById(R.id.btnReservas);
        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "ConsultaReserva");
                Intent intent = new Intent(MostrarReservaciones.this, ConsultarReservas.class);
                startActivity(intent);
            }
        });



        //Boton para salir y regresar al menu principal
        btnSalir3 = (Button) findViewById(R.id.btnSalir3);
        btnSalir3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "SalirMenu");
                Intent intent = new Intent(MostrarReservaciones.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}