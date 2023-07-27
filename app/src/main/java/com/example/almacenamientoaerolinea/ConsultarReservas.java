package com.example.almacenamientoaerolinea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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