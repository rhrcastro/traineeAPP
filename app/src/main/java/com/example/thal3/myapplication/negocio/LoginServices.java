package com.example.thal3.myapplication.negocio;
import com.example.thal3.myapplication.persistencia.pessoaDAO;
import com.example.thal3.myapplication.persistencia.estagiarioDAO;
import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.dominio.Estagiario;
public class LoginServices {
    private pessoaDAO pessoaDAO;
    private estagiarioDAO estagiarioDAO;
    public LoginServices() {
        pessoaDAO = new pessoaDAO();
        estagiarioDAO = new estagiarioDAO();
    }
    public boolean cadastrar(Pessoa pessoa) {
        if (verificarEmail(pessoa.getEstagiario().getEmail())) {
            return false;
        }
        else {
            long id = this.estagiarioDAO.inserirEstagiario(pessoa.getEstagiario());
            pessoa.getEstagiario().setId(id);
            this.pessoaDAO.inserirPessoa(pessoa);
            return true;
        }
    }
    private boolean verificarEmail(String email) {
            Estagiario estagiarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email);
            return estagiarioEmail != null;
        }
}
