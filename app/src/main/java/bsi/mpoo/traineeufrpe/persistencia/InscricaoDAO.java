package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class InscricaoDAO {
    private Database bancoDados;

    public InscricaoDAO(Context context){
        bancoDados = new Database(context);
    }

    public long inserirInscricao(ControladorVaga inscricao){
        SQLiteDatabase escreverBranco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id_vaga", inscricao.getVaga().getId());
        valores.put("id_empregador", inscricao.getEmpregador().getId());
        valores.put("id_estagiario", inscricao.getEstagiario().getId());
        valores.put("data_inscricao", System.currentTimeMillis());
        long resultado = escreverBranco.insert("controlador_vaga", null, valores);
        escreverBranco.close();
        return resultado;
    }

    public ControladorVaga criarObjetoInscricao(Cursor cursor, Context context){
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexIdVaga = cursor.getColumnIndex("id_vaga");
        long id_vaga = cursor.getLong(indexIdVaga);
        int indexIdEmpregador = cursor.getColumnIndex("id_empregador");
        long id_empregador = cursor.getLong(indexIdEmpregador);
        int indexIdEstagiario = cursor.getColumnIndex("id_estagiario");
        long id_estagiario = cursor.getLong(indexIdEstagiario);
        int indexIdHora = cursor.getColumnIndex("hora_inscricao");
        long horaInscricao = cursor.getLong(indexIdHora);
        int indexDataIns = cursor.getColumnIndex("data_inscricao");
        long data = cursor.getLong(indexDataIns);
        ControladorVaga inscricao = new ControladorVaga();
        VagaDAO vagaDAO = new VagaDAO(context);
        EmpregadorDAO empregadorDAO = new EmpregadorDAO(context);
        EstagiarioDAO estagiarioDAO = new EstagiarioDAO(context);
        inscricao.setVaga(vagaDAO.getId(id_vaga, context));
        inscricao.setEmpregador(empregadorDAO.getEmpregadorById(id_empregador, context));
        inscricao.setEstagiario(estagiarioDAO.getEstagiarioById(id_estagiario, context));
        inscricao.setHoraInscricao(data);
        return inscricao;
    }

    public Cursor getInscricaoByEmpregador(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_empregador = ?";
        String[] args = {String.valueOf(id)};
        Cursor data = bancoLeitura.rawQuery(query, args);
        return data;
    }

    public Cursor getInscricaoByEstagiario(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_estagiario = ?";
        String[] args = {String.valueOf(id)};
        Cursor data = bancoLeitura.rawQuery(query, args);
        return data;
    }

    public Cursor getInscricaoByEstagiarioAndVaga(long idEstagiario, long idVaga){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_estagiario = ? AND id_vaga = ?";
        String[] args = {String.valueOf(idEstagiario), String.valueOf(idVaga)};
        Cursor data = bancoLeitura.rawQuery(query, args);
        return data;
    }

    public void deletarInscricao(long idVaga, long idRemetente){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM controlador_vaga " +
                "WHERE id_vaga = "+ String.valueOf(idVaga) + " AND id_estagiario = " + String.valueOf(idRemetente);
        db.execSQL(query);
    }
}
