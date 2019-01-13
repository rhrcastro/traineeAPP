package bsi.mpoo.traineeufrpe.dominio;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;

public class Notificacao {

    private long id;
    private Pessoa pessoaEnvia;
    private Pessoa pessoaRecebe;
    private Empregador empregadorEnvia;
    private Empregador empregadorRecebe;
    private Vaga vagaRelacionada;
    private String mensagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pessoa getPessoaEnvia() {
        return pessoaEnvia;
    }

    public void setPessoaEnvia(Pessoa pessoaEnvia) {
        this.pessoaEnvia = pessoaEnvia;
    }

    public Pessoa getPessoaRecebe() {
        return pessoaRecebe;
    }

    public void setPessoaRecebe(Pessoa pessoaRecebe) {
        this.pessoaRecebe = pessoaRecebe;
    }

    public Empregador getEmpregadorEnvia() {
        return empregadorEnvia;
    }

    public void setEmpregadorEnvia(Empregador empregadorEnvia) {
        this.empregadorEnvia = empregadorEnvia;
    }

    public Empregador getEmpregadorRecebe() {
        return empregadorRecebe;
    }

    public void setEmpregadorRecebe(Empregador empregadorRecebe) {
        this.empregadorRecebe = empregadorRecebe;
    }

    public Vaga getVagaRelacionada() {
        return vagaRelacionada;
    }

    public void setVagaRelacionada(Vaga vagaRelacionada) {
        this.vagaRelacionada = vagaRelacionada;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
