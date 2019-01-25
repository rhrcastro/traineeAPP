package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.database.Cursor;

import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.persistencia.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;

public class EmpregadorServices {

    private final Context mContext;
    private final EmpregadorDAO empregadorDAO;

    private final int COLUMN_ID = 0;
    private final int COLUMN_NOME = 1;
    private final int COLUMN_EMAIL = 2;
    private final int COLUMN_CNPJ = 3;
    private final int COLUMN_SENHA = 4;
    private final int COLUMN_CIDADE = 5;
    private final int COLUMN_FOTO = 6;

    public EmpregadorServices(Context context) {
        mContext = context;
        empregadorDAO = new EmpregadorDAO(context);
    }

    private Empregador getEmpregadorByEmailAndSenha(String email, String senha) {
        Cursor data = empregadorDAO.getEmpregadorByEmaileSenha(email, senha);
        if (data != null && data.moveToFirst()) {
            Empregador empregador = new Empregador();
            empregador.setId(data.getLong(COLUMN_ID));
            empregador.setNome(data.getString(COLUMN_NOME));
            empregador.setEmail(data.getString(COLUMN_EMAIL));
            empregador.setCnpj(data.getString(COLUMN_CNPJ));
            empregador.setSenha(data.getString(COLUMN_SENHA));
            empregador.setCidade(data.getString(COLUMN_CIDADE));
            empregador.setFoto(data.getBlob(COLUMN_FOTO));
            data.close();
            return empregador;
        } return null;
    }

    public Empregador getEmpregadorByEmail(String email) {
        Cursor data = empregadorDAO.getEmpregadorByEmail(email);
        if (data != null && data.moveToFirst()) {
            Empregador empregador = new Empregador();
            empregador.setId(data.getLong(COLUMN_ID));
            empregador.setNome(data.getString(COLUMN_NOME));
            empregador.setEmail(data.getString(COLUMN_EMAIL));
            empregador.setCnpj(data.getString(COLUMN_CNPJ));
            empregador.setSenha(data.getString(COLUMN_SENHA));
            empregador.setCidade(data.getString(COLUMN_CIDADE));
            empregador.setFoto(data.getBlob(COLUMN_FOTO));
            data.close();
            return empregador;
        } return null;
    }

    public Empregador getEmpregadorById(long id) {
        Cursor data = empregadorDAO.getEmpregadorById(id);
        if (data != null && data.moveToFirst()) {
            Empregador empregador = new Empregador();
            empregador.setId(data.getLong(COLUMN_ID));
            empregador.setNome(data.getString(COLUMN_NOME));
            empregador.setEmail(data.getString(COLUMN_EMAIL));
            empregador.setCnpj(data.getString(COLUMN_CNPJ));
            empregador.setSenha(data.getString(COLUMN_SENHA));
            empregador.setCidade(data.getString(COLUMN_CIDADE));
            empregador.setFoto(data.getBlob(COLUMN_FOTO));
            data.close();
            return empregador;
        } return null;
    }

    public boolean cadastrarEmpregador(Empregador empregador) {
        if (!this.isEmailCadastrado(empregador.getEmail())) {
            long result = this.empregadorDAO.inserirEmpregador(empregador);
            empregador.setId(result);
            this.iniciarSessaoEmpregador(empregador);
            return true;
        } return false;
    }

    public boolean logarEmpregador(Empregador empregadorLogin) {
        Empregador empregador = getEmpregadorByEmailAndSenha
                (empregadorLogin.getEmail(), empregadorLogin.getSenha());
        if (empregador != null) {
            this.iniciarSessaoEmpregador(empregador);
            return true;
        } return false;
    }

    private void iniciarSessaoEmpregador(Empregador empregador) {
        SessaoEmpregador.instance.setEmpregador(empregador);
    }

    public boolean isEmailCadastrado(String email) {
        Cursor data = empregadorDAO.getEmpregadorByEmail(email);
        return ((data != null) && (data.getCount() > 0));
    }

    public Empregador isEmailCadastrado(String email, Context context) {
        Empregador data = empregadorDAO.getEmpregadorByEmail(email, context);
        if (data != null){
            return data;
        }
        return null;
    }

    public void alterarFotoEmpregador(Empregador empregador) {
        empregadorDAO.mudarFoto(empregador);
    }

    public void alterarDadosEmpregador(Empregador empregador){
        empregadorDAO.mudarNomeEmpregador(empregador);
        empregadorDAO.mudarEmailEmpregador(empregador);
    }

    public void alterarSenha(Empregador empregador){
        empregadorDAO.mudarSenhaEmpregador(empregador);
    }
}
