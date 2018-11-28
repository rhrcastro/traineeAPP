package com.example.thal3.myapplication.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ControladorBanco {
    private SQLiteDatabase db;
    private Database banco;

    public ControladorBanco(Context context){
        banco = new Database(context);
    }

    public String insereDado(String nome, String cpf, String senha, String cidade){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Database.getNomePessoa(), nome);
        valores.put(Database.getCPF(), cpf);
        valores.put(Database.getSENHA(), senha);
        valores.put(Database.getCIDADE(), cidade);

        resultado = db.insert(Database.getTABELA(), null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }
}
