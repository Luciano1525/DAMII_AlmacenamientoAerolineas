package com.example.almacenamientoaerolinea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsuario;
    private Button btnVuelos, btnRegistroV, btnRegistroA, btnReservacion, btnFinalizar;
    private View separador1, separador2, separador3, separador4;
    private EditText etIDAero, etNombreAero;
    private ImageView ibtnMePop;

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
        setContentView(R.layout.activity_main);

        tvUsuario = (TextView) findViewById(R.id.tvUsuario);

        btnVuelos = (Button) findViewById(R.id.btnVuelos);
        btnRegistroV = (Button) findViewById(R.id.btnRegistroV);
        btnRegistroA = (Button) findViewById(R.id.btnRegistroA);
        btnReservacion = (Button) findViewById(R.id.btnReservacion);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);

        separador1 = (View) findViewById(R.id.separador1);
        separador2 = (View) findViewById(R.id.separador2);
        separador3 = (View) findViewById(R.id.separador3);
        separador4 = (View) findViewById(R.id.separador4);

        SharedPreferences USU = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String UsuRec = USU.getString("UsuarioR", " No Seleccionado");

        if (UsuRec.equals(" Operador")){
            btnVuelos.setVisibility(View.VISIBLE);
            btnRegistroV.setVisibility(View.VISIBLE);
            btnRegistroA.setVisibility(View.VISIBLE);
            btnReservacion.setVisibility(View.VISIBLE);
            btnFinalizar.setVisibility(View.VISIBLE);

        } else if (UsuRec.equals(" Cliente")){
            separador3.setVisibility(View.GONE);
            separador4.setVisibility(View.GONE);
            btnVuelos.setVisibility(View.VISIBLE);
            btnRegistroV.setVisibility(View.GONE);
            btnRegistroA.setVisibility(View.GONE);
            btnReservacion.setVisibility(View.VISIBLE);
            btnFinalizar.setVisibility(View.VISIBLE);

        }
        tvUsuario.setText(UsuRec);

        //Boton para Consultar Vuelos con sus Aerolineas y registrar reservaciones con clientes
        btnVuelos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "VuelosReservaciones");
                Intent intent = new Intent(MainActivity.this, MostrarVuelos.class);
                startActivity(intent);

            }
        });

        //Boton para registrar Vuelos con sus Aerolineas
        btnRegistroV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "RegistroVuelos");
                Intent intent = new Intent(MainActivity.this, RegistrarVuelo.class);
                startActivity(intent);
            }
        });

        //Boton para registrar Aerolineas
        btnRegistroA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder res = new AlertDialog.Builder(MainActivity.this);
                res.setTitle("Registro Aerolineas");
                View select = getLayoutInflater().inflate(R.layout.registro, null);
                res.setView(select);
                res.setCancelable(false);
                etIDAero = (EditText) select.findViewById(R.id.etIDAero);
                etNombreAero = (EditText) select.findViewById(R.id.etNombreAero);

                //Boton para registrar aerolineas
                res.setPositiveButton("Registrar Aerolinea", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Aerolinea operador = new Aerolinea(MainActivity.this, "operacion", null, 1);
                        SQLiteDatabase BDAerolinea = operador.getWritableDatabase();

                        String idAero = etIDAero.getText().toString();
                        String NombreAero = etNombreAero.getText().toString();

                        if (!idAero.isEmpty() && !NombreAero.isEmpty()){
                            ContentValues registro = new ContentValues();
                            registro.put("id", idAero);
                            registro.put("nombre", NombreAero);

                            BDAerolinea.insert("aerolinea", null, registro);
                            BDAerolinea.close();

                            etIDAero.setText("000");
                            etNombreAero.setText("Aerolinea CUFCP");
                            Toast.makeText(getApplicationContext(), "Aerolinea Registrada Exitosamente!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "No puedes guardar datos vacios", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                //Boton para cancelar funcion de registro
                res.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = res.create();
                dialog.show();

            }
        });

        //Boton para Consultar Reservaciones
        btnReservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "ConsultaReser");
                Intent intent = new Intent(MainActivity.this, MostrarReservaciones.class);
                startActivity(intent);
            }
        });


        //Boton para Finalizar el Programa
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO:", "Finalizar");
                finishAffinity();
            }
        });



    }

    //Metodo para mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Metodo de seleccion de opciones
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Operador) {
            SharedPreferences USU = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            String Op = " Operador";
            SharedPreferences.Editor UsaR = USU.edit();
            UsaR.putString("UsuarioR", Op.toString());
            UsaR.commit();
            Toast.makeText(getApplicationContext(), "Bienvenido " + Op, Toast.LENGTH_SHORT).show();


        } else if (id == R.id.Cliente) {
            SharedPreferences USU = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            String Cle = " Cliente";
            SharedPreferences.Editor UsaR = USU.edit();
            UsaR.putString("UsuarioR", Cle.toString());
            UsaR.commit();
            Toast.makeText(getApplicationContext(), "Bienvenido " + Cle, Toast.LENGTH_SHORT).show();
        }

        Log.i("INFO:", "Perfil");
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    //Cambiar de Tema
    public void MenuPop(View v){
        ibtnMePop = (ImageView) findViewById(R.id.ibtnMePop);
        PopupMenu mp = new PopupMenu(this, ibtnMePop);
        mp.getMenuInflater().inflate(R.menu.menu_pop_up, mp.getMenu());
        mp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.MIC) {
                    SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
                    String TemaSeleccionado1 = "Claro";
                    SharedPreferences.Editor TC = TS.edit();
                    TC.putString("TemaSeleccionado2", TemaSeleccionado1.toString());
                    TC.commit();
                    Toast.makeText(getApplicationContext(), "Tema Claro Aplicado", Toast.LENGTH_SHORT).show();

                } else if(item.getItemId()==R.id.MIO) {
                    SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
                    String TemaSeleccionado1 = "Oscuro";
                    SharedPreferences.Editor TO = TS.edit();
                    TO.putString("TemaSeleccionado2", TemaSeleccionado1.toString());
                    TO.commit();
                    Toast.makeText(getApplicationContext(), "Tema Oscuro Aplicado", Toast.LENGTH_SHORT).show();

                }  else if(item.getItemId()==R.id.MIP1) {
                    SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
                    String TemaSeleccionado1 = "Personalizado1";
                    SharedPreferences.Editor TP1 = TS.edit();
                    TP1.putString("TemaSeleccionado2", TemaSeleccionado1.toString());
                    TP1.commit();
                    Toast.makeText(getApplicationContext(), "Tema Personalizado Aplicado", Toast.LENGTH_SHORT).show();

                } else if(item.getItemId()==R.id.MIP2) {
                    SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
                    String TemaSeleccionado1 = "Personalizado2";
                    SharedPreferences.Editor TP2 = TS.edit();
                    TP2.putString("TemaSeleccionado2", TemaSeleccionado1.toString());
                    TP2.commit();
                    Toast.makeText(getApplicationContext(), "Tema Personalizado Aplicado", Toast.LENGTH_SHORT).show();

                } else if(item.getItemId()==R.id.MIP3) {
                    SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
                    String TemaSeleccionado1 = "Personalizado3";
                    SharedPreferences.Editor TP3 = TS.edit();
                    TP3.putString("TemaSeleccionado2", TemaSeleccionado1.toString());
                    TP3.commit();
                    Toast.makeText(getApplicationContext(), "Tema Personalizado Aplicado", Toast.LENGTH_SHORT).show();

                } else if(item.getItemId()==R.id.MIP4) {
                    SharedPreferences TS = getSharedPreferences("Tema", Context.MODE_PRIVATE);
                    String TemaSeleccionado1 = "Personalizado4";
                    SharedPreferences.Editor TP4 = TS.edit();
                    TP4.putString("TemaSeleccionado2", TemaSeleccionado1.toString());
                    TP4.commit();
                    Toast.makeText(getApplicationContext(), "Tema Personalizado Aplicado", Toast.LENGTH_SHORT).show();

                }

                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                return false;

            }
        });

        mp.show();

    }

}