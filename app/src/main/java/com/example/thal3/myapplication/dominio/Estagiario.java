package com.example.thal3.myapplication.dominio;

public class Estagiario {
    private Pessoa pessoa;
    private Curriculo curriculo;
    private String senha;
    private String email;
    private int id;
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
