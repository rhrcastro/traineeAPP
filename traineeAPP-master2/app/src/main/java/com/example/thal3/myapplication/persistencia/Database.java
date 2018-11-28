package com.example.thal3.myapplication.persistencia;
import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thal3.myapplication.dominio.Estagiario;
import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.infra.TraineeApp;

public  class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "traineeapp.db";

    private static final String TABELA = "tabela_pessoa";
    private static final String ID = "_id";
    private static final String NOME_PESSOA = "nome";
    private static final String CPF = "cpf";
    private static final String SENHA = "senha";
    private static final String CIDADE = "cidade";

    public static String getTABELA() {
        return TABELA;
    }
    public static String getID() {
        return ID;
    }
    public static String getNomePessoa() {
        return NOME_PESSOA;
    }
    public static String getCPF() {
        return CPF;
    }
    public static String getSENHA() { return SENHA; }
    public static String getCIDADE() { return CIDADE; }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_PESSOA = "CREATE TABLE " + TABELA + "(" +
                                                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                                NOME_PESSOA + " text, " +
                                                                SENHA + " text, " +
                                                                CIDADE + " text, " +
                                                                CPF + " text " +
                                                                                                                ")";
        /*
        String QUERY_ESTAGIARIO ="CREATE TABLE estagiario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "senha text NOT NULL," +
                "email text NOT NULL," + "id_pessoa integer NOT NULL )";
        */
        db.execSQL(QUERY_PESSOA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }


}
