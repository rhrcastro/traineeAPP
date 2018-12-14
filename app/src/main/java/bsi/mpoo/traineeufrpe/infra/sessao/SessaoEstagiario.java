package bsi.mpoo.traineeufrpe.infra.sessao;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;

import java.util.HashMap;
import java.util.Map;
public class SessaoEstagiario {
    public static final SessaoEstagiario instance = new SessaoEstagiario();
    private final Map<String,Object> values = new HashMap<>();

    public void setPessoa(Pessoa pessoa) {
        setValor("sessao.Pessoa", pessoa);
    }

    public Pessoa getPessoa() {
        return (Pessoa) values.get("sessao.Pessoa");
    }

    private void setValor(String chave, Object valor){
        values.put(chave, valor);
    }
    public void reset() {
        this.values.clear();
    }
    public static SessaoEstagiario getInstance() {
        return instance;
    }

    public void setCurriculo(Curriculo curriculo) {
        setValor("sessao.Curriculo", curriculo);
    }

    public Curriculo getCurriculo() {
        return (Curriculo) values.get("sessao.Curriculo");
    }
}
