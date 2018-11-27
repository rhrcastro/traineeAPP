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
    public void inserirEstagiario(Estagiario estagiario) {
        SQLiteDatabase escritorBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id", estagiario.getId());
        valores.put("email", estagiario.getEmail());
        valores.put("senha", estagiario.getSenha());
        valores.put("id_pessoa", estagiario.getPessoa().getId());

    }
}
