package bsi.mpoo.traineeufrpe.infra.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import bsi.mpoo.traineeufrpe.Persistencia.EmpregadorDAO.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;

public  class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 36;
    private static final String DATABASE_NAME = "traineeapp.bd";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pessoa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL, " +
                "cpf text NOT NULL," +
                "cidade text NOT NULL," +
                "id_estagiario integer NOT NULL);");

        db.execSQL("CREATE TABLE estagiario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email text NOT NULL," +
                "senha text NOT NULL," +
                "id_curriculo integer NOT NULL, fotoestagiario blob NOT NULL);");

        db.execSQL("CREATE TABLE curriculo(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + "curso text NOT NULL," +
                "instituicao text NOT NULL," + "areaAtuacao text NOT NULL);");

        db.execSQL("CREATE TABLE empregador(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL,"+
                "email text NOT NULL," +
                "cnpj text NOT NULL,"+
                "senha text NOT NULL," +
                "cidade NOT NULL, fotoperfil blob NOT NULL);");

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
