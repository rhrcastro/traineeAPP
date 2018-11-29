package com.example.thal3.myapplication.negocio;
import android.content.Context;

import com.example.thal3.myapplication.persistencia.pessoaDAO;
import com.example.thal3.myapplication.persistencia.EstagiarioDAO;
import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.dominio.Estagiario;
public class LoginServices {
    private pessoaDAO pessoaDAO;
    private EstagiarioDAO estagiarioDAO;
    public LoginServices(Context context) {
        pessoaDAO = new pessoaDAO(context );
        estagiarioDAO = new EstagiarioDAO(context);
    }
    public boolean cadastrar(Pessoa pessoa,Context context) {
        if (verificarEmail(pessoa.getEstagiario().getEmail(),context)) {
            return false;
        }
        else {
            this.estagiarioDAO.inserirEstagiario(pessoa.getEstagiario());
            pessoa.setEstagiario(this.estagiarioDAO.getEstagiarioByEmail( pessoa.getEstagiario().getEmail(),context ));

            this.pessoaDAO.inserirPessoa(pessoa);
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
