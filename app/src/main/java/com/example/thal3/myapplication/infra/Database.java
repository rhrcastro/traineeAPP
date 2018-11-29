package com.example.thal3.myapplication.infra;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thal3.myapplication.infra.TraineeApp;

public  class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "traineeapp.bd";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pessoa(" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "nome text NOT NULL, " + "cpf text NOT NULL," + "id_estagiario integer NOT NULL);");
        db.execSQL("CREATE TABLE estagiario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email text NOT NULL," +
                "senha text NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE pessoa;");
        db.execSQL("DROP TABLE estagiario");
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
