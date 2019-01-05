package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
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
        valores.put("id_estagiario", inscricao.getPessoa().getId());
        valores.put("data_inscricao", System.currentTimeMillis());
        valores.put("status", inscricao.getStatus());
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
        int indexStatus = cursor.getColumnIndex("status");
        String status = cursor.getString(indexStatus);
        ControladorVaga inscricao = new ControladorVaga();
        VagaDAO vagaDAO = new VagaDAO(context);
        EmpregadorDAO empregadorDAO = new EmpregadorDAO(context);
        PessoaDAO pessoaDAO = new PessoaDAO(context);
        CurriculoDAO curriculoDAO = new CurriculoDAO(context);
        EstagiarioDAO estagiarioDAO = new EstagiarioDAO(context);
        inscricao.setVaga(vagaDAO.getVagaById(id_vaga, context));
        Pessoa pessoa = pessoaDAO.getPessoaByIdEstagiario(id_estagiario);
        Estagiario estagiario = estagiarioDAO.getEstagiarioById(id_estagiario, context);
        estagiario.setCurriculo(curriculoDAO.getIdCurriculo(estagiario.getId(), context));
        pessoa.setEstagiario(estagiario);
        inscricao.setPessoa(pessoa);
        inscricao.setEmpregador(empregadorDAO.getEmpregadorById(id_empregador, context));
        inscricao.setHoraInscricao(data);
        inscricao.setStatus(status);
        return inscricao;
    }

    public Cursor getInscricaoByEmpregador(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_empregador = ?" +
                " ORDER BY id_vaga ASC";
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

    public Cursor getInscricaoByVaga(long id){
        SQLiteDatabase bancoLeitura = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM controlador_vaga " +
                "WHERE id_vaga = ?";
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

    public void mudarStatusInscricao(ControladorVaga controladorVaga) {
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("status", controladorVaga.getStatus());
        db.update("controlador_vaga", valores,"id = ?", new String[]{String.valueOf(controladorVaga.getId())});
        db.close();
    }
}
