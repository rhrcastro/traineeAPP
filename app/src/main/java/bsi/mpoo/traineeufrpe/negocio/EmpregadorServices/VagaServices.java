package bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.Persistencia.VagaDAO.VagaDAO;
import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.Database.Database;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.TraineApp.TraineeApp;

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

}
