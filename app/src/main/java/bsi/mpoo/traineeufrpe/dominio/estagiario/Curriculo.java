package bsi.mpoo.traineeufrpe.dominio.estagiario;

public class Curriculo {
    private String curso;
    private String instituicao;
    private String areaAtuacao;
    private String experiencia;
    private String objetivo;
    private String relacionamento;
    private String conhcimentos_basicos;
    private String conhecimentos_especificos;
    private String disciplinas;
    private String link;
    private long id;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAreaAtuacao() {
        return areaAtuacao;
    }
    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    public String getInstituicao() {
        return instituicao;
    }
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getRelacionamento() { return relacionamento; }
    public void setRelacionamento(String relacionamento) { this.relacionamento = relacionamento; }
    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getConhcimentos_basicos() { return conhcimentos_basicos; }
    public void setConhcimentos_basicos(String conhcimentos_basicos) { this.conhcimentos_basicos = conhcimentos_basicos; }
    public String getConhecimentos_especificos() { return conhecimentos_especificos; }
    public void setConhecimentos_especificos(String conhecimentos_especificos) { this.conhecimentos_especificos = conhecimentos_especificos; }
    public String getDisciplinas() { return disciplinas; }
    public void setDisciplinas(String disciplinas) { this.disciplinas = disciplinas; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}

