package com.example.thal3.myapplication.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.infra.Database;

public class PessoaDAO {
    private Database bancoDados;
    private EstagiarioDAO estagiarioDAO;
    public PessoaDAO(Context context) {
        bancoDados = new Database(context);
        estagiarioDAO = new EstagiarioDAO(context);
    }
    public void inserirPessoa(Pessoa pessoa) {
        SQLiteDatabase escreverBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", pessoa.getNome());
        valores.put("cpf", pessoa.getCpf());
        valores.put("id_estagiario", pessoa.getEstagiario().getId());
        escreverBanco.insert("pessoa", null, valores);
        escreverBanco.close();

    }
    private Pessoa load(String query, String[] args) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Pessoa pessoa = null;
        if (cursor.moveToNext()) {
            pessoa = criarPessoa(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return pessoa;
    }
    public Pessoa getIdEstagiario(long id) {
        String query = "SELECT * FROM pessoa " +
                "WHERE id_estagiario = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args);
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
