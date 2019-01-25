package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class EmpregadorDAO {

    private final Database bancoDados;

    public EmpregadorDAO(Context context) {
        bancoDados = new Database(context);
    }

    private Empregador criarEmpregador(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexNome = cursor.getColumnIndex("nome");
        String nome = cursor.getString(indexNome);
        int indexEmail = cursor.getColumnIndex("email");
        String email = cursor.getString(indexEmail);
        int indexCnpj = cursor.getColumnIndex("cnpj");
        String cnpj = cursor.getString(indexCnpj);
        int indexSenha = cursor.getColumnIndex("senha");
        String senha = cursor.getString(indexSenha);
        int indexCidade = cursor.getColumnIndex("cidade");
        String cidade = cursor.getString(indexCidade);
        int indexFoto = cursor.getColumnIndex("fotoperfil");
        byte[] fotoEmpregador  = cursor.getBlob(indexFoto);
        Empregador empregador = new Empregador();
        empregador.setId(id);
        empregador.setNome(nome);
        empregador.setEmail(email);
        empregador.setCnpj(cnpj);
        empregador.setSenha(senha);
        empregador.setCidade(cidade);
        empregador.setFoto(fotoEmpregador);
        return empregador;
    }


    public long inserirEmpregador(Empregador empregador) {
        SQLiteDatabase escritorBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", empregador.getNome());
        valores.put("email", empregador.getEmail());
        valores.put("cnpj", empregador.getCnpj());
        valores.put("senha", empregador.getSenha());
        valores.put("cidade", empregador.getCidade());
        valores.put("fotoperfil", empregador.getFoto());
        long  id = escritorBanco.insert("empregador", null, valores);
        escritorBanco.close();
        return id;
    }

    public Cursor getEmpregadorById(long id) {
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM empregador " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return bancoLeitura.rawQuery(query, args);
    }

    private Empregador load(String query, String[] args) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Empregador empregador = null;
        if (cursor.moveToNext()) {
            empregador = criarEmpregador(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return empregador;
    }
    public Empregador getEmpregadorByEmail(String email,Context context) {
        String query = "SELECT * FROM empregador " +
                "WHERE email = ?";
        String[] args = {email};
        return this.load(query, args);
    }

    public Cursor getEmpregadorByEmail(String email) {
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM empregador " +
                "WHERE email = ?";
        String[] args = {email};
        return bancoLeitura.rawQuery(query, args);
    }

    public Cursor getEmpregadorByEmaileSenha(String email, String senha) {
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM empregador " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        return bancoLeitura.rawQuery(query, args);
    }

    public void deletarEmpregador(int id2){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM empregador " +
                "WHERE id = " + id2;
        db.execSQL(query);
    }

    public Cursor getAllEmpregador(){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM empregador";
        return db.rawQuery(query, null);
    }

    public Cursor getId(String name){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "SELECT id FROM empregador " +
                "WHERE nome = ?";
        String[] args = {String.valueOf(name)};
        return db.rawQuery(query, args);
    }

    public void mudarFoto(Empregador empregador) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("fotoperfil", empregador.getFoto());
        db.update("empregador", valores,"id = ?", new String[]{String.valueOf(empregador.getId())});
        db.close();
    }

    public void mudarNomeEmpregador(Empregador empregador) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", empregador.getNome());
        db.update("empregador", valores,"id = ?", new String[]{String.valueOf(empregador.getId())});
        db.close();
    }

    public void mudarEmailEmpregador(Empregador empregador) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("email", empregador.getEmail());
        db.update("empregador", valores,"id = ?", new String[]{String.valueOf(empregador.getId())});
        db.close();
    }

    public void mudarSenhaEmpregador(Empregador empregador) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("senha", empregador.getSenha());
        db.update("empregador", valores,"id = ?", new String[]{String.valueOf(empregador.getId())});
        db.close();
    }
}