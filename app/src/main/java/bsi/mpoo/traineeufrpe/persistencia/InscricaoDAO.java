package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class InscricaoDAO {
    private final Database bancoDados;

    public InscricaoDAO(Context context){
        bancoDados = new Database(context);
    }

    public long inserirInscricao(ControladorVaga inscricao){
        SQLiteDatabase escreverBranco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id_vaga", inscricao.getVaga().getId());
        valores.put("id_empregador", inscricao.getEmpregador().getId());
        valores.put("id_pessoa", inscricao.getPessoa().getId());
        valores.put("data_inscricao", System.currentTimeMillis());
        valores.put("status", inscricao.getStatus());
        long resultado = escreverBranco.insert("controlador_vaga", null, valores);
        escreverBranco.close();
        return resultado;
    }

    public Cursor getInscricaoByEmpregador(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_empregador = ?" +
                " ORDER BY id_vaga ASC";
        String[] args = {String.valueOf(id)};
        return bancoLeitura.rawQuery(query, args);
    }

    public Cursor getInscricaoByPessoa(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_pessoa = ?";
        String[] args = {String.valueOf(id)};
        return bancoLeitura.rawQuery(query, args);
    }

    public Cursor getInscricaoByVaga(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_vaga = ?";
        String[] args = {String.valueOf(id)};
        return bancoLeitura.rawQuery(query, args);
    }



    public Cursor getInscricaoByEstagiarioAndVaga(long idPessoa, long idVaga){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_pessoa = ? AND id_vaga = ?";
        String[] args = {String.valueOf(idPessoa), String.valueOf(idVaga)};
        return bancoLeitura.rawQuery(query, args);
    }

    public void deletarInscricao(long idVaga, long idRemetente){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM controlador_vaga " +
                "WHERE id_vaga = "+ String.valueOf(idVaga) + " AND id_pessoa = " + String.valueOf(idRemetente);
        db.execSQL(query);
    }

    public void mudarStatusInscricao(ControladorVaga controladorVaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("status", controladorVaga.getStatus());
        db.update("controlador_vaga", valores,"id = ?", new String[]{String.valueOf(controladorVaga.getId())});
        db.close();
    }
}
