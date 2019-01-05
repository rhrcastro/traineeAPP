package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class EmpregadorDAO {

    private Database bancoDados;

    public EmpregadorDAO(Context context) {
        bancoDados = new Database(context);
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
        Cursor data = bancoLeitura.rawQuery(query, args);
        return data;
    }

    public Cursor getEmpregadorByEmail(String email) {
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM empregador " +
                "WHERE email = ?";
        String[] args = {email};
        Cursor data = bancoLeitura.rawQuery(query, args);
        return data;
    }

    public Cursor getEmpregadorByEmaileSenha(String email, String senha) {
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM empregador " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        Cursor data = bancoLeitura.rawQuery(query, args);
        return data;
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
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getId(String name){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "SELECT id FROM empregador " +
                "WHERE nome = ?";
        String[] args = {String.valueOf(name)};
        Cursor data = db.rawQuery(query, args);
        return data;
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
}
