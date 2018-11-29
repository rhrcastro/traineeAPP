package com.example.thal3.myapplication.dominio;

import android.content.Context;

import com.example.thal3.myapplication.gui.Curriculo;

public class Estagiario {
    private Curriculo curriculo;
    private String senha;
    private String email;
    private long id;

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setEmail(String email){
        this.email = email;
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
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}
