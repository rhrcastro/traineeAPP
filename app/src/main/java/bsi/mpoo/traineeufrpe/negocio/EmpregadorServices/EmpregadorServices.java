package bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.Persistencia.VagaDAO.VagaDAO;
import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.Persistencia.EmpregadorDAO.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.infra.TraineApp.TraineeApp;

public class EmpregadorServices {
    private EmpregadorDAO empregadorDAO;
    private VagaDAO vagaDAO;
    private TraineeApp traineeApp;
    public EmpregadorServices(Context context) {
        empregadorDAO = new EmpregadorDAO(context);
        vagaDAO = new VagaDAO(context);
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
            long result = this.empregadorDAO.inserirEmpregador(empregador);
            empregador.setId(result);
            this.iniciarSessaoEmpregador(empregador);
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
    public int getIdEmpresa(String name){
        int idempresa = 0;
        Cursor data = this.empregadorDAO.getId(name);
        while (data.moveToNext()){
            idempresa = data.getInt(0);
        }
        return idempresa;
    }
}
