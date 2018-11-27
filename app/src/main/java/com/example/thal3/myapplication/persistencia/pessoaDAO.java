package com.example.thal3.myapplication.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.infra.Database;

public class pessoaDAO {
    private Database bancodados;
    private estagiarioDAO estagiarioDAO;
    public pessoaDAO() {
        bancodados = new Database();
        estagiarioDAO = new estagiarioDAO();
    }
    public void inserirPessoa(Pessoa pessoa) {
        SQLiteDatabase escreverBanco = bancodados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id", pessoa.getId());
        valores.put("nome", pessoa.getNome());
        valores.put("cpf", pessoa.getCpf());
        escreverBanco.insert("pessoa", null, valores);


    }
    private Pessoa criarPessoa(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        int id = cursor.getInt(indexId);
        int indexNome = cursor.getColumnIndex("nome");
        int indexCpf = cursor.getColumnIndex("cpf");
        String nome = cursor.getString(indexNome);
        String cpf = cursor.getString(indexCpf);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        return pessoa;
    }
}
