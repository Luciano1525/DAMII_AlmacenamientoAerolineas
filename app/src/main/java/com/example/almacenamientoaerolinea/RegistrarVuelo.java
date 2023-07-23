package com.example.almacenamientoaerolinea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarVuelo extends AppCompatActivity {
    private EditText etIdAero, etNomAero, etIdVuelo, etNomOrigen, etNomDestino, etFecha, etHora;
    private Button btnBuscar1, btnModificar1, btnEliminar1, btnRegistroVue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vuelo);

        etIdAero = (EditText) findViewById(R.id.etIdAero);
        etNomAero = (EditText) findViewById(R.id.etNomAero);
        etIdVuelo = (EditText) findViewById(R.id.etIdVuelo);
        etNomOrigen = (EditText) findViewById(R.id.etNomOrigen);
        etNomDestino = (EditText) findViewById(R.id.etNomDestino);
        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora = (EditText) findViewById(R.id.etHora);

        //Creacion de objetos de enlaces a las bases de datos
        Aerolinea oper = new Aerolinea(this, "operacion", null, 1);
        Vuelo oper1 = new Vuelo(this, "operacion1", null, 1);

        //Boton para Consultar Aerolineas
        btnBuscar1 = (Button) findViewById(R.id.btnBuscar1);
        btnBuscar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDAerolinea = oper.getWritableDatabase();
                String idAe = etIdAero.getText().toString();

                if(!idAe.isEmpty()) {
                    Cursor fila = BDAerolinea.rawQuery("select nombre from aerolinea where id = " + idAe, null);

                if (fila.moveToFirst()){
                    etNomAero.setText(fila.getString(0));
                    Toast.makeText(getApplicationContext(), "Aerolinea Encontrada", Toast.LENGTH_SHORT).show();
                    BDAerolinea.close();

                } else {
                    Toast.makeText(getApplicationContext(), "Aerolinea no Existe", Toast.LENGTH_SHORT).show();
                    BDAerolinea.close();
                }

                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Aerolinea", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Boton para Modificar Aerolineas
        btnModificar1 = (Button) findViewById(R.id.btnModificar1);
        btnModificar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDAerolinea = oper.getWritableDatabase();
                String idAe = etIdAero.getText().toString();
                String NombAer = etNomAero.getText().toString();

                if(!idAe.isEmpty() && !NombAer.isEmpty()) {
                    ContentValues mod = new ContentValues();
                    mod.put("id", idAe);
                    mod.put("nombre", NombAer);

                    int cantidadmod = BDAerolinea.update("aerolinea", mod, "id = " + idAe, null);
                    BDAerolinea.close();
                    etNomAero.setText("");
                    etNomAero.setHint("Aerolinea");

                    if (cantidadmod == 1){
                        Toast.makeText(getApplicationContext(), "Aerolinea Modificada Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Aerolinea no Existe", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Aerolinea", Toast.LENGTH_SHORT).show();
                }

            }
        });




        //Boton para Eliminar Aerolineas
        btnEliminar1 = (Button) findViewById(R.id.btnEliminar1);
        btnEliminar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDAerolinea = oper.getWritableDatabase();
                String idAe = etIdAero.getText().toString();

                if(!idAe.isEmpty()) {
                    int cantidad = BDAerolinea.delete("aerolinea", "id = " + idAe, null);
                    BDAerolinea.close();

                    etIdAero.setText("");
                    etNomAero.setText("");
                    etIdAero.setHint("ID Aerolinea");
                    etNomAero.setHint("Aerolinea");

                    if (cantidad == 1){
                        Toast.makeText(getApplicationContext(), "Aerolinea Eliminada Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Aerolinea no Existe", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Aerolinea", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Boton para registrar Vuelos con sus Aerolineas
        btnRegistroVue = (Button) findViewById(R.id.btnRegistroVue);
        btnRegistroVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDVuelos = oper1.getWritableDatabase();

                String IdVuelo = etIdVuelo.getText().toString();
                String NomAero = etNomAero.getText().toString();
                String NomOrigen = etNomOrigen.getText().toString();
                String NomDestino = etNomDestino.getText().toString();
                String Fecha = etFecha.getText().toString();
                String Hora = etHora.getText().toString();

                if (!IdVuelo.isEmpty() && !NomAero.isEmpty() && !NomOrigen.isEmpty() && !NomDestino.isEmpty() && !Fecha.isEmpty() && !Hora.isEmpty()){
                    ContentValues registroV = new ContentValues();
                    registroV.put("id", IdVuelo);
                    registroV.put("aerolinea", NomAero);
                    registroV.put("origen", NomOrigen);
                    registroV.put("destino", NomDestino);
                    registroV.put("fecha", Fecha);
                    registroV.put("hora", Hora);

                    BDVuelos.insert("vuelos", null, registroV);
                    BDVuelos.close();

                    etIdAero.setText("");
                    etIdVuelo.setText("");
                    etNomAero.setText("");
                    etNomOrigen.setText("");
                    etNomDestino.setText("");
                    etFecha.setText("");
                    etHora.setText("");

                    etIdAero.setHint("ID Aerolinea");
                    etIdVuelo.setHint("ID Vuelo");
                    etNomAero.setHint("Aerolinea");
                    etNomOrigen.setHint("Origen");
                    etNomDestino.setHint("Destino");
                    etFecha.setHint("Fecha");
                    etHora.setHint("Hora");

                    Toast.makeText(getApplicationContext(), "Vuelo Registrado Exitosamente!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "No puedes guardar datos vacios", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }


}