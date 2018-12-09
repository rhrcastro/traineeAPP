package bsi.mpoo.traineeufrpe.infra.Sessao;
import bsi.mpoo.traineeufrpe.dominio.Curriculo.Curriculo;
import bsi.mpoo.traineeufrpe.dominio.Estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.Pessoa.Pessoa;

import java.util.HashMap;
import java.util.Map;
public class Sessao {
    public static final Sessao instance = new Sessao();
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
    public static Sessao getInstance() {
        return instance;
    }

    public void setCurriculo(Curriculo curriculo) {
        setValor("sessao.Curriculo", curriculo);
    }

    public Curriculo getCurriculo() {
        return (Curriculo) values.get("sessao.Curriculo");
    }
}
