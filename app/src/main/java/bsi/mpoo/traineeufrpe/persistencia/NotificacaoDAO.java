package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class NotificacaoDAO {

    private final Database bancoDados;

    public NotificacaoDAO(Context context) {
        bancoDados = new Database(context);
    }

    public void enviarNotificacao4Empregador(Notificacao notificacao) {
        SQLiteDatabase escreverBranco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("mensagem", notificacao.getMensagem());
        valores.put("id_pessoa_envia", notificacao.getPessoaEnvia().getId());
        valores.put("id_empregador_recebe", notificacao.getEmpregadorRecebe().getId());
        if (notificacao.getVagaRelacionada() != null){
            valores.put("id_vaga", notificacao.getVagaRelacionada().getId());
        }
        long resultado = escreverBranco.insert("notificacoes", null, valores);
        escreverBranco.close();
    }

    public void enviarNotificacao4Estagiario(Notificacao notificacao) {
        SQLiteDatabase escreverBranco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("mensagem", notificacao.getMensagem());
        valores.put("id_empregador_envia", notificacao.getEmpregadorEnvia().getId());
        valores.put("id_pessoa_recebe", notificacao.getPessoaRecebe().getId());
        if (notificacao.getVagaRelacionada() != null){
            valores.put("id_vaga", notificacao.getVagaRelacionada().getId());
        }
        long resultado = escreverBranco.insert("notificacoes", null, valores);
        escreverBranco.close();
    }

    public Cursor getNotificacoes4Empregador(long idEmpregadorRecebe){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM notificacoes " +
                "WHERE id_empregador_recebe = ?";
        String[] args = {String.valueOf(idEmpregadorRecebe)};
        return db.rawQuery(query, args);
    }

    public Cursor getNotificacoes4Estagiario(long idEstagiarioRecebe){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM notificacoes " +
                "WHERE id_pessoa_recebe = ?";
        String[] args = {String.valueOf(idEstagiarioRecebe)};
        return db.rawQuery(query, args);
    }

    public void deletarNotificacao(long id){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM notificacoes " +
                "WHERE id = " + String.valueOf(id);
        db.execSQL(query);
    }

    public void delNotificacaoVagaParaEstagiario(long idEmpregadorEnvia, long idVaga){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM notificacoes " +
                "WHERE id_vaga = " + String.valueOf(idVaga) +
                " AND id_empregador_envia = " + String.valueOf(idEmpregadorEnvia);
        db.execSQL(query);
    }

    public void delNotificacaoVagaParaEmpregador(long idPessoaEnvia, long idVaga){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM notificacoes " +
                "WHERE id_vaga = " + String.valueOf(idVaga) +
                " AND id_pessoa_envia = " + String.valueOf(idPessoaEnvia);
        db.execSQL(query);
    }
}
