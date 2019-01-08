package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.NovaNofificacoes;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.persistencia.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.persistencia.NovaNotificacoesDAO;
import bsi.mpoo.traineeufrpe.persistencia.VagaDAO;

public class NovaNotificacoesServices {

    private final int COLUMN_ID = 0;
    private final int COLUMN_MENSAGEM = 1;
    private final int COLUMN_ID_PESSOA_ENVIA = 2;
    private final int COLUMN_ID_PESSOA_RECEBE = 3;
    private final int COLUMN_ID_EMPREGADOR_ENVIA = 4;
    private final int COLUMN_ID_EMPREGADOR_RECEBE = 5;
    private final int COLUMN_ID_VAGA = 6;

    private Context mContext;
    private NovaNotificacoesDAO novaNotificacoesDAO;
    private VagaDAO vagaDAO;
    private EmpregadorServices empregadorServices;
    private EstagiarioServices estagiarioServices;

    public NovaNotificacoesServices(Context context) {
        this.mContext = context;
        this.novaNotificacoesDAO = new NovaNotificacoesDAO(context);
        this.vagaDAO = new VagaDAO(context);
        this.empregadorServices = new EmpregadorServices(context);
        this.estagiarioServices = new EstagiarioServices(context);
    }

    public boolean enviar4Empregador(NovaNofificacoes notificacao){
        if (notificacao.getMensagem() != null
                && notificacao.getPessoaEnvia() != null
                && notificacao.getEmpregadorRecebe() != null){
            novaNotificacoesDAO.enviarNotificacao4Empregador(notificacao);
            return true;
        } return false;
    }

    public boolean enviar4Estagiario(NovaNofificacoes notificacao){
        if (notificacao.getMensagem() != null
                && notificacao.getEmpregadorEnvia() != null
                && notificacao.getPessoaRecebe() != null){
            novaNotificacoesDAO.enviarNotificacao4Estagiario(notificacao);
            return true;
        } return false;
    }

    public ArrayList<NovaNofificacoes> exibirNotificacoes4Empregador(Empregador empregador){
        ArrayList<NovaNofificacoes> listagem = new ArrayList<>();
        NovaNofificacoes notificacao;
        Cursor data = novaNotificacoesDAO.getNotificacoes4Empregador(empregador.getId());
        while (data.moveToNext()){
            notificacao = new NovaNofificacoes();
            notificacao.setPessoaEnvia(estagiarioServices
                    .getPessoaCompleta(data.getLong(COLUMN_ID_PESSOA_ENVIA)));
            notificacao.setEmpregadorRecebe(empregadorServices.
                    getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR_RECEBE)));
            notificacao.setMensagem(data.getString(COLUMN_MENSAGEM));
            if (!data.isNull(COLUMN_ID_VAGA)){
                notificacao.setVagaRelacionada(vagaDAO.getVagaById(data.getLong(COLUMN_ID_VAGA), mContext));
            }
            listagem.add(notificacao);
        } return listagem;
    }

    public ArrayList<NovaNofificacoes> exibirNotificacoes4Estagiario(Estagiario estagiario){
        ArrayList<NovaNofificacoes> listagem = new ArrayList<>();
        NovaNofificacoes notificacao;
        Cursor data = novaNotificacoesDAO.getNotificacoes4Estagiario(estagiario.getId());
        while (data.moveToNext()){
            notificacao = new NovaNofificacoes();
            notificacao.setEmpregadorEnvia(empregadorServices
                    .getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR_ENVIA)));
            notificacao.setPessoaRecebe(estagiarioServices
                    .getPessoaCompleta(data.getLong(COLUMN_ID_PESSOA_RECEBE)));
            notificacao.setMensagem(data.getString(COLUMN_MENSAGEM));
            if (!data.isNull(COLUMN_ID_VAGA)){
                notificacao.setVagaRelacionada(vagaDAO.getVagaById(data.getLong(COLUMN_ID_VAGA), mContext));
            }
            listagem.add(notificacao);
        } return listagem;
    }

    public void delNotificacoesVagaParaEmpregador(long idVaga, long idEmpregador){
        novaNotificacoesDAO.delNotificacaoVagaParaEmpregador(idEmpregador, idVaga);
    }
}
