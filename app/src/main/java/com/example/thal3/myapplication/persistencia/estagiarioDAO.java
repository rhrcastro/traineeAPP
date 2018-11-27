package com.example.thal3.myapplication.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thal3.myapplication.dominio.Estagiario;
import com.example.thal3.myapplication.infra.Database;

public class estagiarioDAO {
    private Database bancoDados;
    public estagiarioDAO() {
        bancoDados = new Database();
    }
    private Estagiario criarEstagiario(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexEmail = cursor.getColumnIndex("email");
        String email = cursor.getString(indexEmail);
        int indexSenha = cursor.getColumnIndex("senha");
        String senha = cursor.getString(indexSenha);
        Estagiario estagiario = new Estagiario();
        estagiario.setId(id);
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        return estagiario;
    }
    public long inserirEstagiario(Estagiario estagiario) {
        SQLiteDatabase escritorBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id", estagiario.getId());
        valores.put("email", estagiario.getEmail());
        valores.put("senha", estagiario.getSenha());
        long id = escritorBanco.insert("id", null, valores);
        escritorBanco.close();
        return id;
    }
    private Estagiario load(String query, String[] args) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Estagiario estagiario = null;
        if (cursor.moveToNext()) {
            estagiario = criarEstagiario(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return estagiario;
    }
    public Estagiario getEstagiarioByEmail(String email) {
        String query =  "SELECT * FROM usuario " +
                "WHERE email = ?";
        String[] args = {email};
        return this.load(query, args);
    }
    public Estagiario getEstagiarioByEmaileSenha(String email, String senha) {
        String query =  "SELECT * FROM usuario " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        return this.load(query, args);
    }
}
