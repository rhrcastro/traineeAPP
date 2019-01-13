package bsi.mpoo.traineeufrpe.negocio;
import android.content.Context;

import bsi.mpoo.traineeufrpe.persistencia.CurriculoDAO;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.app.TraineeApp;
import bsi.mpoo.traineeufrpe.persistencia.PessoaDAO;
import bsi.mpoo.traineeufrpe.persistencia.EstagiarioDAO;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;

public class LoginServices {

    private static final int naoEncontrado = -1;
    private PessoaDAO pessoaDAO;
    private EstagiarioDAO estagiarioDAO;
    private CurriculoDAO curriculoDAO;
    private TraineeApp trainee;

    public LoginServices(Context context) {
        pessoaDAO = new PessoaDAO(context);
        estagiarioDAO = new EstagiarioDAO(context);
        curriculoDAO = new CurriculoDAO(context);
    }

    public boolean fazerLogin(String email, String senha) {
        Estagiario estagiario = this.estagiarioDAO.getEstagiarioByEmaileSenha(email, senha, trainee.getContext());
        if (estagiario != null) {
            estagiario.setCurriculo(this.curriculoDAO.getIdCurriculo(estagiario.getId(), trainee.getContext()));
            Pessoa pessoa = this.pessoaDAO.getPessoaByIdEstagiario(estagiario.getId());
            pessoa.setEstagiario(estagiario);
            this.iniciarSessao(pessoa, estagiario.getCurriculo());
            return true;
        } return false;
    }

    public boolean cadastrarPessoaNoBanco(Pessoa pessoa, Context context) {
        if (!isEmailCadastrado(pessoa.getEstagiario().getEmail(),context)) {
            long codigoEstagiario = this.estagiarioDAO.inserirEstagiario(pessoa.getEstagiario());
            pessoa.getEstagiario().setId(codigoEstagiario);
            long codigoPessoa = this.pessoaDAO.inserirPessoa(pessoa);
            pessoa.setId(codigoPessoa);
            iniciarSessao(pessoa);
            return true;
        } return false;
    }

    public Curriculo cadastrarCurriculoNoBanco(Curriculo curriculo) {
        long codigoCurriculo;
        codigoCurriculo = this.curriculoDAO.inserirCurriculo(curriculo);
        if (codigoCurriculo!= naoEncontrado) {
            curriculo.setId(codigoCurriculo);
            return curriculo;
        } return null;
    }

    public boolean isEmailCadastrado(String email, Context context) {
        Estagiario estagiarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email,context);
        return (estagiarioEmail != null);
    }

    public Estagiario EstagiarioByEmail(String email, Context context) {
        Estagiario estagiarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email,context);
        return estagiarioEmail;
    }

    private void iniciarSessao(Pessoa pessoa) {
        SessaoEstagiario.instance.setPessoa(pessoa);
        SessaoEstagiario.instance.getPessoa().getEstagiario()
                .setCurriculo(SessaoEstagiario.instance.getCurriculo());
    }

    private void iniciarSessao(Pessoa pessoa, Curriculo curriculo) {
        SessaoEstagiario.instance.setPessoa(pessoa);
        SessaoEstagiario.instance.setCurriculo(curriculo);
        SessaoEstagiario.instance.getPessoa().getEstagiario()
                .setCurriculo(curriculo);
    }

    public void alterarFotoEstagiario(Estagiario estagiario) {
        estagiarioDAO.mudarFotoEstagiario(estagiario);
    }

    public void alterarDadosEstagiario(Pessoa pessoa){
        pessoaDAO.mudarNome(pessoa);
        curriculoDAO.mudarCurso(pessoa.getEstagiario().getCurriculo());
        curriculoDAO.mudarInstituicao(pessoa.getEstagiario().getCurriculo());
        estagiarioDAO.mudarEmailEstagiario(pessoa.getEstagiario());
    }

    public void alterarSenha(Estagiario estagiario){
        estagiarioDAO.mudarSenhaEstagiario(estagiario);
    }
}
