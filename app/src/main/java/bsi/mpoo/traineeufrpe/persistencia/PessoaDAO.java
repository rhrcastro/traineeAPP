package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class PessoaDAO {
    private final Database bancoDados;
    private final EstagiarioDAO estagiarioDAO;
    public PessoaDAO(Context context) {
        bancoDados = new Database(context);
        estagiarioDAO = new EstagiarioDAO(context);
    }
    public long inserirPessoa(Pessoa pessoa) {
        SQLiteDatabase escreverBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", pessoa.getNome());
        valores.put("cpf", pessoa.getCpf());
        valores.put("cidade",pessoa.getCidade());
        valores.put("id_estagiario", pessoa.getEstagiario().getId());
        long resultado = escreverBanco.insert("pessoa", null, valores);
        escreverBanco.close();
        return resultado;
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
    public Pessoa getPessoaByIdEstagiario(long id) {
        String query = "SELECT * FROM pessoa " +
                "WHERE id_estagiario = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args);
    }

    public Pessoa getPessoaById(long id) {
        String query = "SELECT * FROM pessoa " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args);
    }

    public Cursor getIdEstagiarioByPessoa(long idPessoa) {
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM pessoa " +
                "WHERE id = ?";
        String[] args = {String.valueOf(idPessoa)};
        return bancoLeitura.rawQuery(query, args);
    }

    private Pessoa criarPessoa(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        int id = cursor.getInt(indexId);
        int indexNome = cursor.getColumnIndex("nome");
        int indexCpf = cursor.getColumnIndex("cpf");
        int indexCidade = cursor.getColumnIndex("cidade");
        String nome = cursor.getString(indexNome);
        String cpf = cursor.getString(indexCpf);
        String cidade = cursor.getString(indexCidade);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        pessoa.setCidade(cidade);
        return pessoa;
    }

    public void mudarNome(Pessoa pessoa) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", pessoa.getNome());
        db.update("pessoa", valores,"id = ?", new String[]{String.valueOf(pessoa.getId())});
        db.close();
    }

    public void mudarCidade(Pessoa pessoa) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cidade", pessoa.getCidade());
        db.update("pessoa", valores,"id = ?", new String[]{String.valueOf(pessoa.getId())});
        db.close();
    }
}
