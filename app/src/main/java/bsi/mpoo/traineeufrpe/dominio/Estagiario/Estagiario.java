package bsi.mpoo.traineeufrpe.dominio.Estagiario;

import bsi.mpoo.traineeufrpe.dominio.Curriculo.Curriculo;

public class Estagiario {
    private Curriculo curriculo;
    private String senha;
    private String email;
    private long id;
    private byte[] foto;

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

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public byte[] getFoto() {
        return foto;
    }
}
