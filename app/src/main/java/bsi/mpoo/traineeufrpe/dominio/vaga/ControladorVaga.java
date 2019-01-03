package bsi.mpoo.traineeufrpe.dominio.vaga;

import java.text.SimpleDateFormat;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;

public class ControladorVaga {
    private long id;
    private Vaga vaga;
    private Pessoa pessoa;
    private Empregador empregador;
    private long horaInscricao;
    SimpleDateFormat formatodata = new SimpleDateFormat("dd/MM' às 'HH:mm");

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Empregador getEmpregador() {
        return empregador;
    }

    public void setEmpregador(Empregador empregador) {
        this.empregador = empregador;
    }

    public String getDHoraInscricao(){
        return formatodata.format(horaInscricao);
    }

    public void setHoraInscricao(long horaInscricao) {
        this.horaInscricao = horaInscricao;
    }
}
