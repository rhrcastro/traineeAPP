package bsi.mpoo.traineeufrpe.dominio.pessoa;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;

public class Pessoa {
    private String nome;
    private String cpf;
    private long id;
    private String cidade;
    private Estagiario estagiario;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf (){
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Estagiario getEstagiario() {
        return this.estagiario;
    }
    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String toString() {
        return this.nome;
    }
}
