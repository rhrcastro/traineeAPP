package bsi.mpoo.traineeufrpe.infra.sessao;

import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;

import java.util.HashMap;
import java.util.Map;
public class SessaoEmpregador {
    public static final SessaoEmpregador instance = new SessaoEmpregador();
    private final Map<String,Object> values = new HashMap<>();

    public void setEmpregador(Empregador empregador) {
        setValor("sessao.Empregador", empregador);
    }

    public Empregador getEmpregador() {
        return (Empregador) values.get("sessao.Empregador");
    }

    private void setValor(String chave, Object valor){
        values.put(chave, valor);
    }
    public void resetEmpregador() {
        this.values.clear();
    }
    public static SessaoEmpregador getInstance() {
        return instance;
    }

    public void setVaga(Vaga vaga) {
        setValor("sessao.Vaga", vaga);
    }

    public Vaga getVaga() {
        return (Vaga) values.get("sessao.Vaga");
    }
}
