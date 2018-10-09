package com.castano.erwin.crud_bbdd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    Button botonInsertar, botonActualizar, botonBorrar, botonBuscar;
    EditText textoId, textoNombre, textoApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInsertar = (Button)findViewById(R.id.button_insertar);
        botonActualizar = (Button)findViewById(R.id.button_actualizar);
        botonBorrar = (Button)findViewById(R.id.button_borrar);
        botonBuscar = (Button)findViewById(R.id.button_buscar);

        textoId = (EditText) findViewById(R.id.editText_id);
        textoNombre = (EditText) findViewById(R.id.editText_nombre);
        textoApellido = (EditText) findViewById(R.id.editText_apellido);

        final BDDD_Helper Helper = new BDDD_Helper(this);

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Gets the data repository in write mode
                SQLiteDatabase db = Helper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA1, textoId.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, textoNombre.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3, textoApellido.getText().toString());

// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

                Toast.makeText(getApplicationContext(), "Se guardó el registro con clave " + newRowId, Toast.LENGTH_LONG).show();
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = Helper.getWritableDatabase();

// New value for one column

                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, textoNombre.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3, textoApellido.getText().toString());

// Which row to update, based on the title
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " LIKE ?";
                String[] selectionArgs = { textoId.getText().toString() };

                int count = db.update(
                        Estructura_BBDD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(), "El registro con id "+ textoId.getText().toString() + " Ha sido actualizado correctamente." , Toast.LENGTH_LONG).show();


            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = Helper.getWritableDatabase();

// Define 'where' part of query.
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " LIKE ?";
// Specify arguments in placeholder order.
                String[] selectionArgs = { textoId.getText().toString() };
// Issue SQL statement.
                int deletedRows = db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs);

                Toast.makeText(getApplicationContext(), "El registro con id "+ textoId.getText().toString() + " Ha sido borrado exitosamente." , Toast.LENGTH_LONG).show();

                textoId.setText("");
                textoNombre.setText("");
                textoApellido.setText("");


            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = Helper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
                String[] projection = {
                        //BaseColumns._ID,
                        Estructura_BBDD.NOMBRE_COLUMNA2,
                        Estructura_BBDD.NOMBRE_COLUMNA3
                };

// Filter results WHERE "title" = 'My Title'
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " = ?";
                String[] selectionArgs = { textoId.getText().toString() };

// How you want the results sorted in the resulting Cursor
                /*String sortOrder =
                        Estructura_BBDD.NOMBRE_COLUMNA1 + " DESC";*/


                try {
                    Cursor cursor = db.query(
                            Estructura_BBDD.TABLE_NAME,   // The table to query
                            projection,             // The array of columns to return (pass null to get all)
                            selection,              // The columns for the WHERE clause
                            selectionArgs,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null               // The sort order
                    );


                    cursor.moveToFirst();

                    textoNombre.setText(cursor.getString(0));
                    textoApellido.setText(cursor.getString(1));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "El registro no existe", Toast.LENGTH_LONG).show();
                    textoId.setText("");
                    textoNombre.setText("");
                    textoApellido.setText("");
                }


            }
        });
    }
}
