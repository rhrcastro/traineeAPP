package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class EmpregadorDAO {
    private Database bancoDados;
    public EmpregadorDAO(Context context) { bancoDados = new Database(context); }
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
        byte[] fotoPerfil  = cursor.getBlob(indexFoto);
        Empregador empregador = new Empregador();
        empregador.setId(id);
        empregador.setNome(nome);
        empregador.setEmail(email);
        empregador.setCnpj(cnpj);
        empregador.setSenha(senha);
        empregador.setCidade(cidade);
        empregador.setFoto(fotoPerfil);
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
    private Empregador carregarObjeto(String query, String[] args, Context context) {
        SQLiteDatabase leitorBanco = bancoDados.getBancoLeitura(context);
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Empregador empregador = null;
        if (cursor.moveToNext()) {
            empregador = criarEmpregador(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return empregador;
    }
    public Empregador getEmpregadorByEmaileSenha(String email, String senha,Context context) {
        String query =  "SELECT * FROM empregador " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        return this.carregarObjeto(query, args,context);
    }
    public Empregador getId(long id, Context context) {
        String query = "SELECT * FROM empregador " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return this.carregarObjeto(query, args, context);
    }
    public Empregador getEmpregadorByEmail(String email,Context context) {
        String query =  "SELECT * FROM empregador " +
                "WHERE email = ?";
        String[] args = {email};
        return this.carregarObjeto(query, args,context);
    }

    public void mudarNomeEmpregador(Empregador empregador){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", empregador.getNome());
        db.update("empregador", values, "id = ?", new String[]{String.valueOf(empregador.getId())});

    }

    public void deletarEmpregador(int id2){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM empregador " +
                "WHERE id = " + id2;
        db.execSQL(query);

}

    public Cursor getData(){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM empregador";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getId(String name){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "SELECT  id FROM empregador " +
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


}
