package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.persistencia.EstagiarioDAO;
import bsi.mpoo.traineeufrpe.persistencia.VagaDAO;

public class VagaServices {
    private VagaDAO vagaDAO;
    private EmpregadorServices empregadorServices;

    public VagaServices(Context context) {
        vagaDAO = new VagaDAO(context);
    }

    public boolean cadastrarVaga(Vaga vaga, Context context) {
        long result = this.vagaDAO.inserirVaga(vaga);
        vaga.setId(result);
        return true;
    }

    public boolean inserirNotaVaga(Pessoa pessoa, Vaga vaga, float nota) {
        this.vagaDAO.inserirNotaVaga(pessoa.getId(), vaga.getId(), nota);
        return true;
    }

    public ArrayList<String> getListaNomeVagas() {
        ArrayList<String> listaNomeVaga = new ArrayList<String>();
        Cursor data = this.vagaDAO.getDatabyEmpregador(SessaoEmpregador.instance.getEmpregador().getId());
        while (data.moveToNext()) {
            listaNomeVaga.add(data.getString(1));
        }

        return listaNomeVaga;
    }

    public ArrayList<Vaga> getVagasPorData(Context context) {
        ArrayList<Vaga> listaVagas = new ArrayList<Vaga>();
        Cursor data = this.vagaDAO.getAllDataOrderByDate();
        Vaga vaga;
        Empregador empregador;
        empregadorServices = new EmpregadorServices(context);
        while (data.moveToNext()) {
            vaga = new Vaga();
            vaga.setId(data.getLong(0));
            vaga.setNome(data.getString(1));
            vaga.setRequisito(data.getString(2));
            vaga.setBolsa(data.getString(3));
            vaga.setArea(data.getString(4));
            vaga.setObs(data.getString(5));
            vaga.setDataCriacao(data.getLong(7));
            vaga.setHorario(data.getString(8));
            empregador = empregadorServices.getEmpregadorById(data.getLong(6));
            vaga.setEmpregador(empregador);
            listaVagas.add(vaga);
        }
        return listaVagas;
    }

    public ArrayList<Vaga> getVagasPorNome(Context context) {
        ArrayList<Vaga> listaVagas = new ArrayList<Vaga>();
        Cursor data = this.vagaDAO.getAllDataOrderByName();
        Vaga vaga;
        Empregador empregador;
        empregadorServices = new EmpregadorServices(context);
        while (data.moveToNext()) {
            vaga = new Vaga();
            empregador = new Empregador();
            vaga.setId(data.getLong(0));
            vaga.setNome(data.getString(1));
            vaga.setRequisito(data.getString(2));
            vaga.setBolsa(data.getString(3));
            vaga.setArea(data.getString(4));
            vaga.setObs(data.getString(5));
            vaga.setDataCriacao(data.getLong(7));
            vaga.setHorario(data.getString(8));
            empregador = empregadorServices.getEmpregadorById(data.getLong(6));
            vaga.setEmpregador(empregador);
            listaVagas.add(vaga);
        }
        return listaVagas;
    }

    public ArrayList<Vaga> getVagasPorArea(Context context, String area) {
        ArrayList<Vaga> listaVagas = new ArrayList<Vaga>();
        Cursor data = this.vagaDAO.getAllDataByArea(area);
        Vaga vaga;
        Empregador empregador;
        empregadorServices = new EmpregadorServices(context);
        while (data.moveToNext()) {
            vaga = new Vaga();
            empregador = new Empregador();
            vaga.setId(data.getLong(0));
            vaga.setNome(data.getString(1));
            vaga.setRequisito(data.getString(2));
            vaga.setBolsa(data.getString(3));
            vaga.setArea(data.getString(4));
            vaga.setObs(data.getString(5));
            vaga.setDataCriacao(data.getLong(7));
            vaga.setHorario(data.getString(8));
            empregador = empregadorServices.getEmpregadorById(data.getLong(6));
            vaga.setEmpregador(empregador);
            listaVagas.add(vaga);
        }
        return listaVagas;
    }

    public ArrayList<String> getListaBolsaVagas() {
        ArrayList<String> listaNomeVaga = new ArrayList<String>();
        Cursor data = this.vagaDAO.getDatabyEmpregador(SessaoEmpregador.instance.getEmpregador().getId());
        while (data.moveToNext()) {
            listaNomeVaga.add(data.getString(3));
        }

        return listaNomeVaga;
    }

    public ArrayList<String> getVagas() {
        ArrayList<String> listaVaga = new ArrayList<String>();
        Cursor data = this.vagaDAO.getDatabyEmpregador(SessaoEmpregador.instance.getEmpregador().getId());
        while (data.moveToNext()) {
            listaVaga.add(data.getString(0));
        }

        return listaVaga;
    }

    public ArrayList<Vaga> getObjetoVaga(ArrayList<String> listaId) {
        ArrayList<Vaga> listaVaga = new ArrayList<Vaga>();
        for (int i = 0; i < listaId.size(); i++) {
            int id = Integer.parseInt(listaId.get(i));
            Vaga vaga = vagaDAO.getVagaById(id);
            listaVaga.add(vaga);
        }
        return listaVaga;
    }


    public int getIdVaga(String name) {
        int idempresa = 0;
        Cursor data = this.vagaDAO.getId(name);
        while (data.moveToNext()) {
            idempresa = data.getInt(0);
        }
        return idempresa;
    }

    public Vaga mudarNomeVaga(Vaga vaga, String nome) {
        if (nome != "") {
            vaga.setNome(nome);
            vagaDAO.mudarNomeVaga(vaga);
        }
        return vaga;
    }

    public Vaga mudarAreaVaga(Vaga vaga, String area) {
        if (area != "") {
            vaga.setArea(area);
            vagaDAO.mudarNomeVaga(vaga);
        }
        return vaga;
    }


    public Vaga mudarBolsaVaga(Vaga vaga, String bolsa) {
        if (bolsa != "") {
            vaga.setBolsa(bolsa);
            vagaDAO.mudarBolsaVaga(vaga);
        }

        return vaga;

    }

    public Vaga mudarRequisitoVaga(Vaga vaga, String req) {
        if (req != "") {
            vaga.setRequisito(req);
            vagaDAO.mudarRequisitoVaga(vaga);
        }
        return vaga;
    }

    public Vaga mudarObsVaga(Vaga vaga, String obs) {
        if (obs != "") {
            vaga.setObs(obs);
            vagaDAO.mudarObsVaga(vaga);
        }
        return vaga;
    }

    public Vaga mudarHorarioVaga(Vaga vaga, String horario) {
        if (horario != "") {
            vaga.setHorario(horario);
            vagaDAO.mudarHorarioVaga(vaga);
        }
        return vaga;
    }

    public void DelVaga(long id) {
        vagaDAO.deletarVaga(id);
    }

    public Vaga vagaById(String nomeVAGA, Context context) {
        VagaDAO vaga = new VagaDAO(context);
        return vaga.getVagaById(Integer.parseInt(nomeVAGA));
    }

    public ArrayList<Vaga> getRecomendacao(Context context) {
        EstagiarioDAO estagiarioDAO = new EstagiarioDAO(context);
        Estagiario estagiarioLogado = SessaoEstagiario.instance.getPessoa().getEstagiario();
        Map<Estagiario, Map<String, Double>> dados = getAvaliacoesEstagiario(context);
        HashMap<String, Double> avaliacoesUsuario = avaliacaoPorEstagiario(estagiarioLogado, context);
        SlopeOne slopeOne = new SlopeOne(dados);
        Map<String, Double> predicoes = slopeOne.predict(avaliacoesUsuario);
        return getVagasRecomendadas(predicoes, context);
    }

    private ArrayList<Vaga> getVagasRecomendadas(Map<String, Double> predicoes, Context context) {
        ArrayList<Vaga> recomendados = new ArrayList<>();
        for (String vaga : predicoes.keySet()) {
            Vaga vagaAtual = vagaById(vaga, context);
            vagaAtual.setAvaliacaoEstagiario(predicoes.get(vaga));
            Double notaVagaEstagiario = avaliacaoVagaEstagiario(vagaAtual, context);
            if (notaVagaEstagiario == null) {
                recomendados.add(vagaAtual);
            }
        }
        Collections.sort(recomendados, new Comparator<Vaga>() {
            @Override
            public int compare(Vaga v1, Vaga v2) {
                return v2.getAvaliacaoEstagiario().intValue() - v2.getAvaliacaoEstagiario().intValue();
            }
        });
        return recomendados;
    }
    public Double avaliacaoVagaEstagiario(Vaga vaga, Context context){
        Estagiario estagiario = SessaoEstagiario.instance.getPessoa().getEstagiario();
        VagaDAO vagaDAO = new VagaDAO(context);
        return vagaDAO.getNotaVaga(estagiario.getId(), vaga.getId());
    }
    private  Map<Estagiario, Map<String, Double>> getAvaliacoesEstagiario(Context context) {
        EstagiarioDAO estagiarioDAO = new EstagiarioDAO(context);
        Estagiario estagiarioLogado = SessaoEstagiario.instance.getPessoa().getEstagiario();
        Map<Estagiario, Map<String, Double>> dados = new HashMap<>();
        ArrayList<Estagiario> estagiarios = estagiarioDAO.carregarEstagiarios();
        for (Estagiario estagiario : estagiarios) {
            if (estagiario.getId() != estagiarioLogado.getId()) {
                dados.put(estagiario, avaliacaoPorEstagiario(estagiario, context));
            }
        }
        return dados;
    }
    private HashMap<String,Double> avaliacaoPorEstagiario(Estagiario estagiario, Context context){
        VagaDAO vagaDAO = new VagaDAO(context);
        return  vagaDAO.getAvaliacaoVaga(estagiario);
    }
}
