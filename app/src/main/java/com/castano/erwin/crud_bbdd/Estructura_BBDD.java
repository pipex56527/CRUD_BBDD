package com.castano.erwin.crud_bbdd;

import android.provider.BaseColumns;

public class Estructura_BBDD {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Estructura_BBDD() {
    }

    /* Inner class that defines the table contents */

    public static final String TABLE_NAME = "datosPersonales";
    public static final String NOMBRE_COLUMNA1 = "id";
    public static final String NOMBRE_COLUMNA2 = "nombre";
    public static final String NOMBRE_COLUMNA3 = "apellido";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY," +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + " TEXT," +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;


}
