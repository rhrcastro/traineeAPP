package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.persistencia.NotificacaoDAO;
import bsi.mpoo.traineeufrpe.persistencia.VagaDAO;

public class NotificacaoServices {

    private final int COLUMN_ID = 0;
    private final int COLUMN_MENSAGEM = 1;
    private final int COLUMN_ID_PESSOA_ENVIA = 2;
    private final int COLUMN_ID_PESSOA_RECEBE = 3;
    private final int COLUMN_ID_EMPREGADOR_ENVIA = 4;
    private final int COLUMN_ID_EMPREGADOR_RECEBE = 5;
    private final int COLUMN_ID_VAGA = 6;

    private final Context mContext;
    private final NotificacaoDAO notificacaoDAO;
    private final VagaDAO vagaDAO;
    private final EmpregadorServices empregadorServices;
    private final EstagiarioServices estagiarioServices;

    public NotificacaoServices(Context context) {
        this.mContext = context;
        this.notificacaoDAO = new NotificacaoDAO(context);
        this.vagaDAO = new VagaDAO(context);
        this.empregadorServices = new EmpregadorServices(context);
        this.estagiarioServices = new EstagiarioServices(context);
    }

    public void enviar4Empregador(Notificacao notificacao){
        if (notificacao.getMensagem() != null
                && notificacao.getPessoaEnvia() != null
                && notificacao.getEmpregadorRecebe() != null){
            notificacaoDAO.enviarNotificacao4Empregador(notificacao);
        }
    }

    public void enviar4Estagiario(Notificacao notificacao){
        if (notificacao.getMensagem() != null
                && notificacao.getEmpregadorEnvia() != null
                && notificacao.getPessoaRecebe() != null){
            notificacaoDAO.enviarNotificacao4Estagiario(notificacao);
        }
    }

    public ArrayList<Notificacao> exibirNotificacoes4Empregador(Empregador empregador){
        ArrayList<Notificacao> listagem = new ArrayList<>();
        Notificacao notificacao;
        Cursor data = notificacaoDAO.getNotificacoes4Empregador(empregador.getId());
        while (data.moveToNext()){
            notificacao = new Notificacao();
            notificacao.setPessoaEnvia(estagiarioServices
                    .getPessoaCompleta(data.getLong(COLUMN_ID_PESSOA_ENVIA)));
            notificacao.setEmpregadorRecebe(empregadorServices.
                    getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR_RECEBE)));
            notificacao.setMensagem(data.getString(COLUMN_MENSAGEM));
            if (!data.isNull(COLUMN_ID_VAGA)){
                notificacao.setVagaRelacionada(vagaDAO.getVagaById(data.getLong(COLUMN_ID_VAGA)));
            }
            listagem.add(notificacao);
        } return listagem;
    }

    public ArrayList<Notificacao> exibirNotificacoes4Estagiario(Estagiario estagiario){
        ArrayList<Notificacao> listagem = new ArrayList<>();
        Notificacao notificacao;
        Cursor data = notificacaoDAO.getNotificacoes4Estagiario(estagiario.getId());
        while (data.moveToNext()){
            notificacao = new Notificacao();
            notificacao.setEmpregadorEnvia(empregadorServices
                    .getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR_ENVIA)));
            notificacao.setPessoaRecebe(estagiarioServices
                    .getPessoaCompleta(data.getLong(COLUMN_ID_PESSOA_RECEBE)));
            notificacao.setMensagem(data.getString(COLUMN_MENSAGEM));
            if (!data.isNull(COLUMN_ID_VAGA)){
                notificacao.setVagaRelacionada(vagaDAO.getVagaById(data.getLong(COLUMN_ID_VAGA)));
            }
            listagem.add(notificacao);
        } return listagem;
    }

    public void delNotificacoesVagaParaEmpregador(long idVaga, long idEmpregador){
        notificacaoDAO.delNotificacaoVagaParaEmpregador(idEmpregador, idVaga);
    }
}
