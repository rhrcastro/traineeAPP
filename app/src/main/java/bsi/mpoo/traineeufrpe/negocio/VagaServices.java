package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.app.TraineeApp;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.persistencia.VagaDAO;

public class VagaServices {
    private VagaDAO vagaDAO;
    private TraineeApp traineeApp;
    public VagaServices(Context context) {
        vagaDAO = new VagaDAO(context);
    }

    public boolean cadastrarVaga(Vaga vaga, Context context) {
        long result = this.vagaDAO.inserirVaga(vaga);
        vaga.setId(result);
        return true;

    }

    public ArrayList<String> getListaNomeVagas(){
        ArrayList<String> listaNomeVaga = new ArrayList<String>();
        Cursor data = this.vagaDAO.getData(SessaoEmpregador.instance.getEmpregador().getId());
        while (data.moveToNext()){
            listaNomeVaga.add(data.getString(1));
        }

        return listaNomeVaga;
    }


    public ArrayList<String> getListaBolsaVagas(){
        ArrayList<String> listaNomeVaga = new ArrayList<String>();
        Cursor data = this.vagaDAO.getData(SessaoEmpregador.instance.getEmpregador().getId());
        while (data.moveToNext()){
            listaNomeVaga.add(data.getString(3));
        }

        return listaNomeVaga;
    }

    public ArrayList<String> getVagas(){
        ArrayList<String> listaVaga = new ArrayList<String>();
        Cursor data = this.vagaDAO.getData(SessaoEmpregador.instance.getEmpregador().getId());
        while (data.moveToNext()){
            listaVaga.add(data.getString(0));
        }

        return listaVaga;
    }

    public ArrayList<Vaga> getObjetoVaga(ArrayList<String> listaId, Context context){
        ArrayList<Vaga> listaVaga = new ArrayList<Vaga>();
        for(int i = 0; i<listaId.size(); i++){
            int id = Integer.parseInt(listaId.get(i));
            Vaga vaga = vagaDAO.getId(id, context);
            listaVaga.add(vaga);
        }
        return listaVaga;
    }


    public int getIdVaga(String name){
        int idempresa = 0;
        Cursor data = this.vagaDAO.getId(name);
        while (data.moveToNext()){
            idempresa = data.getInt(0);
        }
        return idempresa;
    }

    public Vaga mudarNomeVaga(Vaga vaga, String nome){
        if (nome!="") {
            vaga.setNome(nome);
            vagaDAO.mudarNomeVaga(vaga);
        }
        return vaga;
    }
    public Vaga mudarBolsaVaga(Vaga vaga, String bolsa){
        if(bolsa != ""){
            vaga.setBolsa(bolsa);
            vagaDAO.mudarBolsaVaga(vaga);
        }

        return vaga;

    }
    public Vaga mudarRequisitoVaga(Vaga vaga, String req) {
        if (req != ""){
            vaga.setRequisito(req);
            vagaDAO.mudarRequisitoVaga(vaga);
        }

        return vaga;

    }

    public Vaga mudarObsVaga(Vaga vaga, String obs){
        if(obs != ""){
            vaga.setObs(obs);
            vagaDAO.mudarObsVaga(vaga);
        }

        return vaga;

    }

    public void DelVaga(long id){
        vagaDAO.deletarVaga(id);
    }

}
