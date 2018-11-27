package com.example.thal3.myapplication.dominio;

public class Pessoa {
    private String nome;
    private String cpf;
    private long id;
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
}
