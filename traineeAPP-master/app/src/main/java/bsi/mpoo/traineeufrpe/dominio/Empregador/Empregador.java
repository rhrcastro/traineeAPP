package bsi.mpoo.traineeufrpe.dominio.Empregador;

public class Empregador {
    private long id;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String cidade;
    private byte[] foto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public byte[] getFoto() {
        return foto;
    }
}
