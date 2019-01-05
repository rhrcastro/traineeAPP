package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.Notifications;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.persistencia.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.persistencia.NotificationsDAO;
import bsi.mpoo.traineeufrpe.persistencia.PessoaDAO;

public class NotificationServices {

    private final int COLUMN_ID = 0;
    private final int COLUMN_ID_REMETENTE = 1;
    private final int COLUMN_ID_DESTINATARIO = 2;
    private final int COLUMN_ID_VAGA = 3;
    private final int COLUMN_MENSAGEM = 4;
    private final int COLUMN_IS_EMPREGADOR = 5;
    private final int COLUMN_FOTOREMENTENTE = 6;

    private NotificationsDAO notificationsDAO;
    private EmpregadorDAO empregadorDAO;
    private PessoaDAO pessoaDAO;

    public NotificationServices(Context context){
        notificationsDAO = new NotificationsDAO(context);
        pessoaDAO = new PessoaDAO(context);
        empregadorDAO = new EmpregadorDAO(context);
    }

    public void enviar(Notifications notification) {
        notification.setId(notificationsDAO.inserirNotificacao(notification));
    }

    public ArrayList<Notifications> listaNotificacoes(Empregador empregador, Context context){
        ArrayList<Notifications> lista = new ArrayList<>();
        Cursor data = notificationsDAO.getAllNotificationsForEmpregador(empregador.getId());
        Notifications notif;
        String nomeEstagiario, nomeEmpregador;
        while (data.moveToNext()){
            notif = new Notifications();
            notif.setId(data.getLong(COLUMN_ID));
            notif.setIdRemetente(data.getLong(COLUMN_ID_REMETENTE));
            notif.setIdDestinatario(data.getLong(COLUMN_ID_DESTINATARIO));
            notif.setFotoEstagiario(data.getBlob(COLUMN_FOTOREMENTENTE));
            nomeEstagiario = pessoaDAO.getPessoaByIdEstagiario(data.getLong(COLUMN_ID_REMETENTE)).getNome();
            notif.setNomeRemetente(nomeEstagiario);
            nomeEmpregador = empregadorDAO.getEmpregadorById(data
                    .getLong(COLUMN_ID_DESTINATARIO), context).getNome();
            notif.setNomeDestinatario(nomeEmpregador);
            notif.setMensagem(data.getString(COLUMN_MENSAGEM));
            int isEmpregador = data.getInt(COLUMN_IS_EMPREGADOR);
            if (isEmpregador == 0){
                notif.setIsEmpregador(false);
            } else {
                notif.setIsEmpregador(true);
            }
            lista.add(notif);
        } return lista;
    }

    public ArrayList<Notifications> listaNotificacoes(Estagiario estagiario, Context context){
        ArrayList<Notifications> lista = new ArrayList<>();
        Cursor data = notificationsDAO.getAllNotificationsForEstagiario(estagiario.getId());
        Notifications notif;
        String nomeEstagiario, nomeEmpregador;
        while (data.moveToNext()){
            notif = new Notifications();
            notif.setId(data.getLong(COLUMN_ID));
            notif.setIdRemetente(data.getLong(COLUMN_ID_REMETENTE));
            notif.setIdDestinatario(data.getLong(COLUMN_ID_DESTINATARIO));
            notif.setIdVaga(data.getLong(COLUMN_ID_VAGA));
            nomeEmpregador = empregadorDAO.getEmpregadorById(data
                    .getLong(COLUMN_ID_DESTINATARIO), context).getNome();
            notif.setNomeRemetente(nomeEmpregador);
            nomeEstagiario = pessoaDAO.getPessoaByIdEstagiario(data.getLong(COLUMN_ID_REMETENTE)).getNome();
            notif.setNomeDestinatario(nomeEstagiario);
            notif.setMensagem(data.getString(COLUMN_MENSAGEM));
            lista.add(notif);
        } return lista;
    }

    public void delNotificacao(long idVaga, long idRemetente, Context context){
        NotificationsDAO notificationsDAO = new NotificationsDAO(context);
        notificationsDAO.deletarNotificacao(idVaga, idRemetente);
    }

}
