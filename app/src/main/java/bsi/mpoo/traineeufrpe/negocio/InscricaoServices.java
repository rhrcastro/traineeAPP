package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.app.TraineeApp;
import bsi.mpoo.traineeufrpe.persistencia.CurriculoDAO;
import bsi.mpoo.traineeufrpe.persistencia.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.persistencia.EstagiarioDAO;
import bsi.mpoo.traineeufrpe.persistencia.InscricaoDAO;
import bsi.mpoo.traineeufrpe.persistencia.PessoaDAO;
import bsi.mpoo.traineeufrpe.persistencia.VagaDAO;

public class InscricaoServices {
    private InscricaoDAO inscricaoDAO;
    private VagaDAO vagaDAO;
    private EmpregadorServices empregadorServices;
    private EstagiarioDAO estagiarioDAO;
    private PessoaDAO pessoaDAO;
    private CurriculoDAO curriculoDAO;
    private Context mContext;

    private final int COLUMN_ID = 0;
    private final int COLUMN_ID_VAGA = 1;
    private final int COLUMN_ID_EMPREGADOR = 2;
    private final int COLUMN_ID_ESTAGIARIO = 3;
    private final int COLUMN_DATA_INSCRICAO = 4;
    private final int COLUMN_STATUS = 5;


    public InscricaoServices(Context context) {
        inscricaoDAO = new InscricaoDAO(context);
        vagaDAO = new VagaDAO(context);
        empregadorServices = new EmpregadorServices(context);
        estagiarioDAO = new EstagiarioDAO(context);
        pessoaDAO = new PessoaDAO(context);
        curriculoDAO = new CurriculoDAO(context);
        mContext = context;

    }

    public boolean cadastrarInscricao(ControladorVaga inscricao, Context context) {
        long result = this.inscricaoDAO.inserirInscricao(inscricao);
        inscricao.setId(result);
        return true;
    }

    public ArrayList<ControladorVaga> listarInscricoes(Empregador empregador, Context context){
        ArrayList<ControladorVaga> inscricoes = new ArrayList<>();
        Cursor data = inscricaoDAO.getInscricaoByEmpregador(empregador.getId());
        ControladorVaga inscricao;
        Estagiario estagiario;
        while (data.moveToNext()){
            inscricao = new ControladorVaga();
            inscricao.setId(data.getLong(COLUMN_ID));
            inscricao.setVaga(vagaDAO.getVagaById(data.getLong(COLUMN_ID_VAGA), context));
            inscricao.setEmpregador(empregadorServices
                    .getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR)));
            estagiario = estagiarioDAO
                    .getEstagiarioById(data.getLong(COLUMN_ID_ESTAGIARIO), context);
            if (estagiario != null) {
                Pessoa pessoa = pessoaDAO.getPessoaByIdEstagiario(estagiario.getId());
                estagiario.setCurriculo(this.curriculoDAO.getIdCurriculo(estagiario.getId(), TraineeApp.getContext()));
                pessoa.setEstagiario(estagiario);
                inscricao.setPessoa(pessoa);
            }
            inscricao.setHoraInscricao(data.getLong(COLUMN_DATA_INSCRICAO));
            inscricao.setStatus(data.getString(COLUMN_STATUS));
            if (!inscricao.getStatus().equals("dispensado")) {
                inscricoes.add(inscricao);
            }
        } return inscricoes;
    }

    public boolean isInscrito(long idEstagiario, long idVaga) {
        Cursor data = inscricaoDAO.getInscricaoByEstagiarioAndVaga(idEstagiario, idVaga);
        if (data.getCount() == 0) {
            data.close();
            return false;
        }
        return true;
    }

    public void delInscricao(long idVaga, long idRemetente){
        inscricaoDAO.deletarInscricao(idVaga, idRemetente);
    }

    public long getNumInscritosByVaga(long idVaga){
        Cursor data = inscricaoDAO.getInscricaoByVaga(idVaga);
        long num = data.getCount();
        data.close();
        return num;
    }

    public ArrayList<ControladorVaga> getInscritosByVaga(long idVaga, Context context){
        ArrayList<ControladorVaga> inscricoes = new ArrayList<>();
        Cursor data = inscricaoDAO.getInscricaoByVaga(idVaga);
        Estagiario estagiario;
        ControladorVaga inscricao;
        while (data.moveToNext()){
            inscricao = new ControladorVaga();
            inscricao.setId(data.getLong(COLUMN_ID));
            inscricao.setVaga(vagaDAO.getVagaById(data.getLong(COLUMN_ID_VAGA), context));
            inscricao.setEmpregador(empregadorServices
                    .getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR)));
            estagiario = estagiarioDAO
                    .getEstagiarioById(data.getLong(COLUMN_ID_ESTAGIARIO), context);
            if (estagiario != null) {
                Pessoa pessoa = pessoaDAO.getPessoaByIdEstagiario(estagiario.getId());
                estagiario.setCurriculo(this.curriculoDAO.getIdCurriculo(estagiario.getId(), TraineeApp.getContext()));
                pessoa.setEstagiario(estagiario);
                inscricao.setPessoa(pessoa);
            }
            inscricao.setHoraInscricao(data.getLong(COLUMN_DATA_INSCRICAO));
            inscricao.setStatus(data.getString(COLUMN_STATUS));
            if (!inscricao.getStatus().equals("dispensado")) {
                inscricoes.add(inscricao);
            }
        } return inscricoes;
    }

    public String getStatusInscricaoByEstagiarioAndVaga(Pessoa pessoa, Vaga vaga){
        Cursor data = inscricaoDAO.getInscricaoByEstagiarioAndVaga(pessoa.getId(), vaga.getId());
        if (data != null && data.moveToFirst()) {
            String status = data.getString(COLUMN_STATUS);
            data.close();
            return status;
        }
        return "aberta";
    }

    public ControladorVaga getInscricaoByPessoaAndVaga(Pessoa pessoa, Vaga vaga){
        Cursor data = inscricaoDAO.getInscricaoByEstagiarioAndVaga(pessoa.getId(), vaga.getId());
        ControladorVaga inscricao;
        if (data != null && data.moveToFirst()) {
            inscricao = new ControladorVaga();
            inscricao.setId(data.getLong(COLUMN_ID));
            inscricao.setVaga(vaga);
            inscricao.setEmpregador(empregadorServices
                    .getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR)));
            inscricao.setPessoa(pessoa);
            inscricao.setHoraInscricao(data.getLong(COLUMN_DATA_INSCRICAO));
            inscricao.setStatus(data.getString(COLUMN_STATUS));
            return inscricao;
        }
        return null;
    }

    public ArrayList<Empregador> getEmpresasByPessoa(Pessoa pessoa){
        ArrayList<Empregador> empresas = new ArrayList<>();
        Cursor data = inscricaoDAO.getInscricaoByEstagiario(pessoa.getId());
        Empregador empregador;
        while (data.moveToNext()) {
            empregador = empregadorServices
                    .getEmpregadorById(data.getLong(COLUMN_ID_EMPREGADOR));
            empresas.add(empregador);
        } return empresas;
    }

    public void setStatusInscricaoByEstagiarioAndVaga(ControladorVaga controladorVaga){
        inscricaoDAO.mudarStatusInscricao(controladorVaga);
    }

}
