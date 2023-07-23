package com.example.almacenamientoaerolinea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarVuelo extends AppCompatActivity {
    private EditText etIdAero, etNomAero;
    private Button btnBuscar1, btnModificar1, btnEliminar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vuelo);

        etIdAero = (EditText) findViewById(R.id.etIdAero);
        etNomAero = (EditText) findViewById(R.id.etNomAero);
        Aerolinea oper = new Aerolinea(this, "operacion", null, 1);

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


    }


}