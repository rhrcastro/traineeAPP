package com.trainee2.negocio;

import android.content.Context;
import android.database.Cursor;

import com.trainee2.dominio.Empregador;
import com.trainee2.infra.SessaoEmpregador;
import com.trainee2.infra.TraineeApp;
import com.trainee2.persistencia.EmpregadorDAO;

import java.util.ArrayList;

public class EmpregadorServices {
    private EmpregadorDAO empregadorDAO;
    private TraineeApp traineeApp;
    public EmpregadorServices(Context context) {
        empregadorDAO = new EmpregadorDAO(context);
    }

    public boolean logarEmpregador(Empregador empregador) {
        Empregador empregadorLogin = this.empregadorDAO.getEmpregadorByEmaileSenha(empregador.getEmail(), empregador.getSenha(), traineeApp.getContext());
        boolean taLogado = false;
        if (empregadorLogin != null) {
            Empregador empregado = this.empregadorDAO.getId(empregadorLogin.getId(), traineeApp.getContext());
            this.iniciarSessaoEmpregador(empregado);
            taLogado = true;
        }
        return taLogado;
    }
    public void iniciarSessaoEmpregador(Empregador empregador) {
        SessaoEmpregador.instance.setEmpregador(empregador);
    }
    public boolean cadastrarEmpregador(Empregador empregador, Context context) {
        if (verificarEmail(empregador.getEmail(), context)) {
            return false;
        } else {
            this.empregadorDAO.inserirEmpregador(empregador);
            return true;
        }

    }
    private boolean verificarEmail(String email,Context context) {
        Empregador empregadorEmail = this.empregadorDAO.getEmpregadorByEmail(email, context);
        if (empregadorEmail != null) {
            return true;
        }
        return false;
    }
    public ArrayList<String> getListaNomeEmpresa(){
        ArrayList<String> listaNomeEmpresa = new ArrayList<String>();
        Cursor data = this.empregadorDAO.getData();
        while (data.moveToNext()){
            listaNomeEmpresa.add(data.getString(1));
        }

        return listaNomeEmpresa;
    }
    public int getIdEmpresa(String name){
        int idempresa = 0;
        Cursor data = this.empregadorDAO.getId(name);
        while (data.moveToNext()){
            idempresa = data.getInt(0);
        }
        return idempresa;
    }
}
