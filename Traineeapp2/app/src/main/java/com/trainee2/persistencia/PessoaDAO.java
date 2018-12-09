package com.trainee2.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trainee2.dominio.Pessoa;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.TraineeApp;

public class PessoaDAO {
    private BancoDados bancoDados;
    private EstagiarioDAO estagiarioDAO;
    public PessoaDAO(Context context) {
        bancoDados = new BancoDados(context);
        estagiarioDAO = new EstagiarioDAO(context);
    }
    public long inserirPessoa(Pessoa pessoa) {
        SQLiteDatabase escreverBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", pessoa.getNome());
        valores.put("cpf", pessoa.getCpf());
        valores.put("id_estagiario", pessoa.getEstagiario().getId());
        long result = escreverBanco.insert("pessoa", null, valores);
        escreverBanco.close();
        if (result > -1){
            pessoa.setId(result);
        }
        return result;

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
