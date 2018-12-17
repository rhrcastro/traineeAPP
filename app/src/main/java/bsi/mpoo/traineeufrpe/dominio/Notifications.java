package bsi.mpoo.traineeufrpe.dominio;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;

public class Notifications {

    private long id;
    private long idRemetente;
    private long idDestinatario;
    private long idVaga;

    private String mensagem;
    private String nomeRemetente;
    private String nomeDestinatario;

    private boolean isEmpregador;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(long idRemetente) {
        this.idRemetente = idRemetente;
    }

    public long getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(long idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public long getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(long idVaga) {
        this.idVaga = idVaga;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public boolean getIsEmpregador(){
        return isEmpregador;
    }

    public void setIsEmpregador(boolean isEmpregador){
        this.isEmpregador = isEmpregador;
    }
}
