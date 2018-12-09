package com.trainee2.negocio;

import android.content.Context;

import com.trainee2.dominio.Curriculo;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Pessoa;
import com.trainee2.infra.Sessao;
import com.trainee2.infra.TraineeApp;
import com.trainee2.persistencia.CurriculoDAO;
import com.trainee2.persistencia.EstagiarioDAO;
import com.trainee2.persistencia.PessoaDAO;

public class LoginServices {
    private PessoaDAO PessoaDAO;
    private EstagiarioDAO estagiarioDAO;
    private CurriculoDAO curriculoDAO;
    private TraineeApp trainee;
    public LoginServices(Context context) {
        PessoaDAO = new PessoaDAO(context );
        estagiarioDAO = new EstagiarioDAO(context);
        curriculoDAO = new CurriculoDAO(context);

    }
    public boolean logar(Estagiario estagiario) {
        Estagiario estagiarioLogin = this.estagiarioDAO.getEstagiarioByEmaileSenha(estagiario.getEmail(), estagiario.getSenha(), trainee.getContext());
        boolean taLogado = false;
        if (estagiarioLogin != null) {
            Pessoa pessoa = this.PessoaDAO.getIdEstagiario(estagiario.getId());
            this.iniciarSessao(pessoa);
            taLogado = true;
        }
        return taLogado;
    }
    public boolean cadastrar(Pessoa pessoa,Context context) {
        if (verificarEmail(pessoa.getEstagiario().getEmail(),context)) {
            return false;
        }
        else {
            this.estagiarioDAO.inserirEstagiario(pessoa.getEstagiario());
            pessoa.setEstagiario(this.estagiarioDAO.getEstagiarioByEmail(pessoa.getEstagiario().getEmail(),context ));

            this.PessoaDAO.inserirPessoa(pessoa);
            return true;
        }
    }

    public Curriculo cadastrar(Curriculo curriculo, Context context) {
        long result;
        result = this.curriculoDAO.inserirCurriculo(curriculo);
        if (result!= -1){
            curriculo.setId(result);
            return curriculo;
        }else{
            return null;
        }


    }
    private boolean verificarEmail(String email,Context context) {
        Estagiario estagiarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email,context);
        if (estagiarioEmail != null) {
            return true;
        }
        return  false;
    }
    private void iniciarSessao(Pessoa pessoa) {
        Sessao.instance.setPessoa(pessoa);
    }


}