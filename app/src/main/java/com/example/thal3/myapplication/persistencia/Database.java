package com.example.thal3.myapplication.persistencia;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thal3.myapplication.infra.TraineeApp;

public  class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "traineeapp.db";
    public Database() {
        super(TraineeApp.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pessoa(" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "nome text NOT NULL, " + "cpf text NOT NULL); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE pessoa;");
        onCreate(db);
    }
}
