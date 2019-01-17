package bsi.mpoo.traineeufrpe.dominio.vaga;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;

public class Vaga {
    private String nome;
    private String requisito;
    private long id;
    private Empregador empregador;
    private String area;
    private String bolsa;
    private String obs;
    private String horario;
    private long miliseconds;
    private Double avaliacaoEstagiario;
    SimpleDateFormat formatodata = new SimpleDateFormat("dd/MM");

    private String mascaraTempo(long miliseconds){
        Calendar dataAtual = Calendar.getInstance();
        Calendar dataPublicada = Calendar.getInstance();
        dataAtual.setTimeInMillis(System.currentTimeMillis());
        dataPublicada.setTimeInMillis(miliseconds);
        int diaAtual = dataAtual.get(Calendar.DAY_OF_YEAR);
        int diaVaga = dataPublicada.get(Calendar.DAY_OF_YEAR);
        int anoAtual = dataAtual.get(Calendar.YEAR);
        int anoVaga = dataAtual.get(Calendar.YEAR);
        if (anoAtual == anoVaga){
            if (diaAtual == diaVaga){
                return "Hoje";
            } else if ((diaAtual - diaVaga) == 1){
            return "Ontem";
            }
        } return formatodata.format(miliseconds);
    }

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
        return mascaraTempo(this.miliseconds);
    }

    public void setDataCriacao(long miliseconds) {
        this.miliseconds = miliseconds;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getHorario() {
        return horario;
    }
    public Double getAvaliacaoEstagiario() {
        return avaliacaoEstagiario;
    }
    public void setAvaliacaoEstagiario(Double nota) {
        this.avaliacaoEstagiario = nota;
    }
}
