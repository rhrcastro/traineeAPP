package com.trainee2.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public  class BancoDados extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 26;
    private static final String DATABASE_NAME = "traineeapp.bd";
    public BancoDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pessoa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL, " + "cpf text NOT NULL," + "id_estagiario integer NOT NULL);");

        db.execSQL("CREATE TABLE estagiario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email text NOT NULL," +
                "senha text NOT NULL);");

        db.execSQL("CREATE TABLE curriculo(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + "curso text NOT NULL," +
                "instituicao text NOT NULL," + "areaAtuacao text NOT NULL);");

        db.execSQL("CREATE TABLE empregador(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL,"+
                "email text NOT NULL," +
                "cnpj text NOT NULL,"+
                "senha text NOT NULL," +
                "cidade NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE pessoa");
        db.execSQL("DROP TABLE estagiario");
        db.execSQL("DROP TABLE curriculo");
        db.execSQL("DROP TABLE IF EXISTS empregador");
        onCreate(db);
    }
    public SQLiteDatabase getBancoLeitura(Context context){
        SQLiteDatabase bancoDados = this.getReadableDatabase();
        return bancoDados;
    }

    public SQLiteDatabase getBancoEscrita(Context context) {
        SQLiteDatabase bancoDados = this.getWritableDatabase();
        return bancoDados;
    }



}
