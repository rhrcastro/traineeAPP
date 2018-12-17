package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.infra.database.Database;
import bsi.mpoo.traineeufrpe.dominio.Notifications;

public class NotificationsDAO {
    private Database bancoDados;

    public NotificationsDAO(Context context) {
        bancoDados = new Database(context);
    }

    public long inserirNotificacao(Notifications notif) {
        SQLiteDatabase escreverBranco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id_remetente", notif.getIdRemetente());
        valores.put("id_destinatario", notif.getIdDestinatario());
        valores.put("id_vaga", notif.getIdVaga());
        valores.put("mensagem", notif.getMensagem());
        if (notif.getIsEmpregador()) {
            valores.put("is_empregador", 1);
        } else {
            valores.put("is_empregador", 0);
        }
        long resultado = escreverBranco.insert("notificacao", null, valores);
        escreverBranco.close();
        return resultado;
    }

    public Cursor getAllNotificationsForEmpregador(long id){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM notificacao " +
                "WHERE id_destinatario = ? AND is_empregador = 1";
        String[] args = {String.valueOf(id)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public Cursor getAllNotificationsForEstagiario(long id){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM notificacao " +
                "WHERE id_destinatario = ? AND is_empregador = 0";
        String[] args = {String.valueOf(id)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public void deletarNotificacao(long idVaga, long idRemetente){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM notificacao " +
                "WHERE id_vaga = " + String.valueOf(idVaga) + " AND id_remetente = " + String.valueOf(idRemetente);
        db.execSQL(query);
    }
}
