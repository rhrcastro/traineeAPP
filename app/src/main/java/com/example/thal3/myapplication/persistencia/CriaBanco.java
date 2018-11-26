package com.example.thal3.myapplication.persistencia;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public  class CriaBanco extends SQLiteOpenHelper {
    private static final String NomeBanco  = "trainee.db";
    private static final String Tabela = "pessoa";
    private static final String Id = "_id";
    private static final String NomePessoa = "nome pessoa";
    private static final String Cpf = "cpf pessoa";
    private static final int Versao = 1;
    public CriaBanco(Context context) {
        super(context, NomeBanco, null, Versao);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Tabela + "("+ Id + "integer primary key autoincrement,"
                + NomePessoa + ",text"
                + Cpf + ",text" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tabela);
        onCreate(db);
    }
}
