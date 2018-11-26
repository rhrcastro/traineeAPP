package com.example.thal3.myapplication.dominio;

public class Pessoa {
    private String nome;
    private String cpf;
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
}
