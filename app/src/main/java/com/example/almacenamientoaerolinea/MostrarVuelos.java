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

public class MostrarVuelos extends AppCompatActivity {
    private Button btnSalir2, btnBuscar2, btnModificar2, btnEliminar2, btnReservarVu;
    private EditText etIdVuelo1, etNomOrigen1, etNomDestino1, etNomAero1, etFecha1, etHora1, etIdCliente, etNomClien, etIdReserva1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_vuelos);

        etIdReserva1 = (EditText) findViewById(R.id.etIdReserva1);
        etIdVuelo1 = (EditText) findViewById(R.id.etIdVuelo1);
        etNomOrigen1 = (EditText) findViewById(R.id.etNomOrigen1);
        etNomDestino1 = (EditText) findViewById(R.id.etNomDestino1);
        etNomAero1 = (EditText) findViewById(R.id.etNomAero1);
        etFecha1 = (EditText) findViewById(R.id.etFecha1);
        etHora1 = (EditText) findViewById(R.id.etHora1);
        etIdCliente = (EditText) findViewById(R.id.etIdCliente);
        etNomClien = (EditText) findViewById(R.id.etNomClien);

        //Creacion de objetos de enlaces a las bases de datos
        Vuelo oper = new Vuelo(this, "operacion1", null, 1);
        Reservacion oper1 = new Reservacion(this, "operacion2", null, 1);

        //Boton para consultar los vuelos
        btnBuscar2 = (Button) findViewById(R.id.btnBuscar2);
        btnBuscar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDVuelos = oper.getWritableDatabase();
                String idVue = etIdVuelo1.getText().toString();

                if(!idVue.isEmpty()) {
                    Cursor fila = BDVuelos.rawQuery("select aerolinea, origen, destino, fecha, hora from vuelos where id = " + idVue, null);

                    if (fila.moveToFirst()){
                        etNomAero1.setText(fila.getString(0));
                        etNomOrigen1.setText(fila.getString(1));
                        etNomDestino1.setText(fila.getString(2));
                        etFecha1.setText(fila.getString(3));
                        etHora1.setText(fila.getString(4));
                        Toast.makeText(getApplicationContext(), "Aerolinea Encontrada", Toast.LENGTH_SHORT).show();
                        BDVuelos.close();

                    } else {
                        Toast.makeText(getApplicationContext(), "Aerolinea no Existe", Toast.LENGTH_SHORT).show();
                        BDVuelos.close();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id de la Aerolinea", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Boton para Modificar Vuelos
        btnModificar2 = (Button) findViewById(R.id.btnModificar2);
        btnModificar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDVuelos = oper.getWritableDatabase();
                String idVue = etIdVuelo1.getText().toString();
                String Origen = etNomOrigen1.getText().toString();
                String Destino = etNomDestino1.getText().toString();
                String Aerolinea = etNomAero1.getText().toString();
                String Fecha = etFecha1.getText().toString();
                String Hora = etHora1.getText().toString();

                if(!idVue.isEmpty() && !Origen.isEmpty() && !Destino.isEmpty() && !Aerolinea.isEmpty()
                        && !Fecha.isEmpty() && !Hora.isEmpty()) {
                    ContentValues mod = new ContentValues();
                    mod.put("id", idVue);
                    mod.put("aerolinea", Origen);
                    mod.put("origen", Destino);
                    mod.put("destino", Aerolinea);
                    mod.put("fecha", Fecha);
                    mod.put("hora", Hora);

                    int cantidadmod = BDVuelos.update("vuelos", mod, "id = " + idVue, null);
                    BDVuelos.close();

                    etNomOrigen1.setText("");
                    etNomDestino1.setText("");
                    etNomAero1.setText("");
                    etFecha1.setText("");
                    etHora1.setText("");

                    etNomOrigen1.setHint("Origen");
                    etNomDestino1.setHint("Destino");
                    etNomAero1.setHint("Aerolinea");
                    etFecha1.setHint("Fecha");
                    etHora1.setHint("Hora");

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
        btnEliminar2 = (Button) findViewById(R.id.btnEliminar2);
        btnEliminar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDVuelos = oper.getWritableDatabase();
                String idVue = etIdVuelo1.getText().toString();

                if(!idVue.isEmpty()) {
                    int cantidad = BDVuelos.delete("vuelos", "id = " + idVue, null);
                    BDVuelos.close();

                    etIdVuelo1.setText("");
                    etNomOrigen1.setText("");
                    etNomDestino1.setText("");
                    etNomAero1.setText("");
                    etFecha1.setText("");
                    etHora1.setText("");

                    etIdVuelo1.setHint("ID Vuelo");
                    etNomOrigen1.setHint("Origen");
                    etNomDestino1.setHint("Destino");
                    etNomAero1.setHint("Aerolinea");
                    etFecha1.setHint("Fecha");
                    etHora1.setHint("Hora");

                    if (cantidad == 1){
                        Toast.makeText(getApplicationContext(), "Vuelo Eliminado Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Vuelo no Existe", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Introduzca el id del Vuelo", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Boton para registrar Reservaciones
        btnReservarVu = (Button) findViewById(R.id.btnReservarVu);
        btnReservarVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase BDReservaciones = oper1.getWritableDatabase();

                String idReser = etIdReserva1.getText().toString();
                String Origen = etNomOrigen1.getText().toString();
                String Destino = etNomDestino1.getText().toString();
                String Aerolinea = etNomAero1.getText().toString();
                String Fecha = etFecha1.getText().toString();
                String Hora = etHora1.getText().toString();
                String idClien = etIdCliente.getText().toString();
                String NombClien = etNomClien.getText().toString();


                if(!idReser.isEmpty() && !Origen.isEmpty() && !Destino.isEmpty() && !Aerolinea.isEmpty()
                        && !Fecha.isEmpty() && !Hora.isEmpty() && !idClien.isEmpty() && !NombClien.isEmpty()){
                    ContentValues registroR = new ContentValues();
                    registroR.put("id", idReser);
                    registroR.put("cliente", NombClien);
                    registroR.put("aerolinea", Aerolinea);
                    registroR.put("origen", Origen);
                    registroR.put("destino", Destino);
                    registroR.put("fecha", Fecha);
                    registroR.put("hora", Hora);

                    BDReservaciones.insert("reservacion", null, registroR);
                    BDReservaciones.close();


                    etIdReserva1.setText("");
                    etIdVuelo1.setText("");
                    etNomOrigen1.setText("");
                    etNomDestino1.setText("");
                    etNomAero1.setText("");
                    etFecha1.setText("");
                    etHora1.setText("");
                    etIdCliente.setText("");
                    etNomClien.setText("");

                    etIdReserva1.setHint("ID Reserva");
                    etIdVuelo1.setHint("ID Vuelo");
                    etNomOrigen1.setHint("Origen");
                    etNomDestino1.setHint("Destino");
                    etNomAero1.setHint("Aerolinea");
                    etFecha1.setHint("Fecha");
                    etHora1.setHint("Hora");
                    etIdCliente.setHint("ID Cliente");
                    etNomClien.setHint("Nombre Cliente");

                    Toast.makeText(getApplicationContext(), "Reservacion Registrada Exitosamente!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "No puedes guardar datos vacios", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Boton para salir y regresar al menu principal
        btnSalir2 = (Button) findViewById(R.id.btnSalir2);
        btnSalir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "SalirMenu");
                Intent intent = new Intent(MostrarVuelos.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}