package bsi.mpoo.traineeufrpe.dominio.vaga;

import java.text.SimpleDateFormat;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;

public class Vaga {
    private String nome;
    private String requisito;
    private long id;
    private Empregador empregador;
    private String area;
    private String bolsa;
    private String obs;
    private long miliseconds;
    SimpleDateFormat formatodata = new SimpleDateFormat("dd/MM' às 'HH:mm");

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBolsa() {
        return bolsa;
    }

    public void setBolsa(String bolsa) {
        this.bolsa = bolsa;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Empregador getEmpregador() {
        return empregador;
    }

    public void setEmpregador(Empregador empregador) {
        this.empregador = empregador;
    }

    public String getDataCriacao(){
        return formatodata.format(miliseconds);
    }

    public void setDataCriacao(long miliseconds) {
        this.miliseconds = miliseconds;
    }
}
