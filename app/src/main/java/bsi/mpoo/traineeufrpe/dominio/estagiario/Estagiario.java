package bsi.mpoo.traineeufrpe.dominio.estagiario;

public class Estagiario {
    private Curriculo curriculo;
    private String senha;
    private String email;
    private long id;
    private byte[] foto;

    public Curriculo getCurriculo() {
        return curriculo;
    }
    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
