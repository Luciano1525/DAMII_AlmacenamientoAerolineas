package com.example.almacenamientoaerolinea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarReservas extends AppCompatActivity {
    private EditText etIdReserva, etNomClien1, etNomAero2, etNomOrigen2, etNomDestino2, etFecha2, etHora2;
    private Button btnBuscar3, btnModificar3, btnEliminar3, btnSalir4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recuperar Tema y Aplicarlo
        SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
        String TemaSeleccionado = TS.getString("TemaSeleccionado2", "No Hay Tema Aplicado");
        if (TemaSeleccionado != null) {
            if (TemaSeleccionado.equals("Claro")) {
                setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_DarkActionBar);
            } else if (TemaSeleccionado.equals("Oscuro")) {
                setTheme(R.style.Oscuro);
            } else if (TemaSeleccionado.equals("Personalizado1")) {
                setTheme(R.style.MiTema1);
            } else if (TemaSeleccionado.equals("Personalizado2")) {
                setTheme(R.style.MiTema2);
            } else if (TemaSeleccionado.equals("Personalizado3")) {
                setTheme(R.style.MiTema3);
            } else if (TemaSeleccionado.equals("Personalizado4")) {
                setTheme(R.style.MiTema4);
            }
        }
        setContentView(R.layout.activity_consultar_reservas);

        etIdReserva = (EditText) findViewById(R.id.etIdReserva);
        etNomOrigen2 = (EditText) findViewById(R.id.etNomOrigen2);
        etNomDestino2 = (EditText) findViewById(R.id.etNomDestino2);
        etNomAero2 = (EditText) findViewById(R.id.etNomAero2);
        etFecha2 = (EditText) findViewById(R.id.etFecha2);
        etHora2 = (EditText) findViewById(R.id.etHora2);
        etNomClien1 = (EditText) findViewById(R.id.etNomClien1);


        //Creacion de objetos de enlaces a las bases de datos
        Reservacion oper1 = new Reservacion(this, "operacion2", null, 1);

        //Boton para consultar las reservaciones
        btnBuscar3 = (Button) findViewById(R.id.btnBuscar3);
        btnBuscar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BBReservacion = oper1.getWritableDatabase();
                String idReser = etIdReserva.getText().toString();

                if(!idReser.isEmpty()) {
                    Cursor fila = BBReservacion.rawQuery("select cliente, aerolinea, origen, destino, fecha, hora from reservacion where id = " + idReser, null);

                    if (fila.moveToFirst()){
                        etNomClien1.setText(fila.getString(0));
                        etNomAero2.setText(fila.getString(1));
                        etNomOrigen2.setText(fila.getString(2));
                        etNomDestino2.setText(fila.getString(3));
                        etFecha2.setText(fila.getString(4));
                        etHora2.setText(fila.getString(5));
                        Toast.makeText(getApplicationContext(), "Reservacion Encontrada", Toast.LENGTH_SHORT).show();
                        BBReservacion.close();

                    } else {
                        Toast.makeText(getApplicationContext(), "La Reservacion no Existe", Toast.LENGTH_SHORT).show();
                        BBReservacion.close();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Reservacion", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Boton para Modificar Reservaciones
        btnModificar3 = (Button) findViewById(R.id.btnModificar3);
        btnModificar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BBReservacion = oper1.getWritableDatabase();
                String idRese = etIdReserva.getText().toString();
                String Cliente = etNomClien1.getText().toString();
                String Origen = etNomOrigen2.getText().toString();
                String Destino = etNomDestino2.getText().toString();
                String Aerolinea = etNomAero2.getText().toString();
                String Fecha = etFecha2.getText().toString();
                String Hora = etHora2.getText().toString();

                if(!idRese.isEmpty() && !Cliente.isEmpty()  && !Origen.isEmpty() && !Destino.isEmpty()
                        && !Aerolinea.isEmpty() && !Fecha.isEmpty() && !Hora.isEmpty()) {
                    ContentValues mod = new ContentValues();
                    mod.put("id", idRese);
                    mod.put("cliente", Cliente);
                    mod.put("aerolinea", Aerolinea);
                    mod.put("origen", Origen);
                    mod.put("destino", Destino);
                    mod.put("fecha", Fecha);
                    mod.put("hora", Hora);

                    int cantidadmod = BBReservacion.update("reservacion", mod, "id = " + idRese, null);
                    BBReservacion.close();

                    etNomOrigen2.setText("");
                    etNomDestino2.setText("");
                    etNomAero2.setText("");
                    etFecha2.setText("");
                    etHora2.setText("");
                    etNomClien1.setText("");

                    etNomOrigen2.setHint("Origen");
                    etNomDestino2.setHint("Destino");
                    etNomAero2.setHint("Aerolinea");
                    etFecha2.setHint("Fecha");
                    etHora2.setHint("Hora");
                    etNomClien1.setHint("Nombre del Cliente");

                    if (cantidadmod == 1){
                        Toast.makeText(getApplicationContext(), "Reservacion Modificado Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Reservacion no Existe", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Reservacion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Boton para Eliminar Reservaciones
        btnEliminar3 = (Button) findViewById(R.id.btnEliminar3);
        btnEliminar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BBReservacion = oper1.getWritableDatabase();
                String idRese = etIdReserva.getText().toString();

                if(!idRese.isEmpty()) {
                    int cantidad = BBReservacion.delete("reservacion", "id = " + idRese, null);
                    BBReservacion.close();

                    etIdReserva.setText("");
                    etNomOrigen2.setText("");
                    etNomDestino2.setText("");
                    etNomAero2.setText("");
                    etFecha2.setText("");
                    etHora2.setText("");
                    etNomClien1.setText("");

                    etIdReserva.setHint("ID Reserva");
                    etNomOrigen2.setHint("Origen");
                    etNomDestino2.setHint("Destino");
                    etNomAero2.setHint("Aerolinea");
                    etFecha2.setHint("Fecha");
                    etHora2.setHint("Hora");
                    etNomClien1.setHint("Nombre del Cliente");

                    if (cantidad == 1){
                        Toast.makeText(getApplicationContext(), "Reservacion Eliminado Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Reservacion no Existe", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Reservacion", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Boton para salir y regresar a las Reservas
        btnSalir4 = (Button) findViewById(R.id.btnSalir4);
        btnSalir4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "SalirReservas");
                Intent intent = new Intent(ConsultarReservas.this, MostrarReservaciones.class);
                startActivity(intent);
            }
        });


    }
}