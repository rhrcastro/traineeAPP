package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class EstagiarioDAO {
    private Database bancoDados ;
    public EstagiarioDAO(Context context) { bancoDados = new Database(context); }
    private Estagiario criarEstagiario(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexEmail = cursor.getColumnIndex("email");
        String email = cursor.getString(indexEmail);
        int indexSenha = cursor.getColumnIndex("senha");
        String senha = cursor.getString(indexSenha);
        int indexFoto = cursor.getColumnIndex("fotoestagiario");
        byte[] fotoEstagiario  = cursor.getBlob(indexFoto);
        Estagiario estagiario = new Estagiario();
        estagiario.setId(id);
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        estagiario.setFoto(fotoEstagiario);
        return estagiario;
    }
    public long inserirEstagiario(Estagiario estagiario) {
        SQLiteDatabase escritorBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("email", estagiario.getEmail());
        valores.put("senha", estagiario.getSenha());
        valores.put("id_curriculo", estagiario.getCurriculo().getId());
        valores.put("fotoestagiario", estagiario.getFoto());
        long id = escritorBanco.insert("estagiario", null, valores);
        escritorBanco.close();
        return id;
    }
    private Estagiario load(String query, String[] args,Context context) {
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
    public Estagiario getEstagiarioByEmail(String email,Context context) {
        String query =  "SELECT * FROM estagiario " +
                "WHERE email = ?";
        String[] args = {email};
        return this.load(query, args, context);
    }
    public Estagiario getEstagiarioByEmaileSenha(String email, String senha,Context context) {
        String query =  "SELECT * FROM estagiario " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        return this.load(query, args, context);
    }
    public Estagiario getEstagiarioById(long id, Context context) {
        String query =  "SELECT * FROM estagiario " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args, context);
    }
    public Estagiario getIdCurriculo(long id, Context context) {
        String query = "SELECT * FROM curriculo " +
                "WHERE id_estagiario = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args, context);
    }
    public void mudarFotoEstagiario(Estagiario estagiario) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("fotoestagiario", estagiario.getFoto());
        db.update("estagiario", valores,"id = ?", new String[]{String.valueOf(estagiario.getId())});
        db.close();
    }
    public void mudarEmailEstagiario(Estagiario estagiario) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("email", estagiario.getEmail());
        db.update("estagiario", valores,"id = ?", new String[]{String.valueOf(estagiario.getId())});
        db.close();
    }

}
