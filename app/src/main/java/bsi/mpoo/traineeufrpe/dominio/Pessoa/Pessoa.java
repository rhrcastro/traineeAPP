package bsi.mpoo.traineeufrpe.dominio.Pessoa;

import bsi.mpoo.traineeufrpe.dominio.Estagiario.Estagiario;

public class Pessoa {
    private String nome;
    private String cpf;
    private long id;
    private String cidade;
    private Estagiario estagiario;
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String toString() {
        return this.nome;
    }
    public String getCpf (){
        return cpf;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public String getNome() {
        return this.nome;
    }
    public Estagiario getEstagiario() {
        return this.estagiario;
    }
    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
}
