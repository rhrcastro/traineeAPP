package com.example.thal3.myapplication.negocio;
import android.content.Context;

import com.example.thal3.myapplication.infra.TraineeApp;
import com.example.thal3.myapplication.persistencia.PessoaDAO;
import com.example.thal3.myapplication.persistencia.EstagiarioDAO;
import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.dominio.Estagiario;
public class LoginServices {
    private PessoaDAO PessoaDAO;
    private EstagiarioDAO estagiarioDAO;
    private TraineeApp trainee;
    public LoginServices(Context context) {
        PessoaDAO = new PessoaDAO(context );
        estagiarioDAO = new EstagiarioDAO(context);
    }
    public boolean logar(Estagiario estagiario) {
        Estagiario estagiarioLogin = this.estagiarioDAO.getEstagiarioByEmaileSenha(estagiario.getEmail(), estagiario.getSenha(), trainee.getContext());
        boolean taLogado = false;
        if (estagiarioLogin != null) {
            Pessoa pessoa = this.PessoaDAO.getIdEstagiario(estagiario.getId());
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
            pessoa.setEstagiario(this.estagiarioDAO.getEstagiarioByEmail( pessoa.getEstagiario().getEmail(),context ));

            this.PessoaDAO.inserirPessoa(pessoa);
            return true;
        }
    }
    private boolean verificarEmail(String email,Context context) {
            Estagiario estagiarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email,context);
            if (estagiarioEmail != null) {
                return true;
            }
            return  false;
    }
}