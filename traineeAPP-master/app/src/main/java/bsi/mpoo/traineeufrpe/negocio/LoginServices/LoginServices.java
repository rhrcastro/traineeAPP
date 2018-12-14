package bsi.mpoo.traineeufrpe.negocio.LoginServices;
import android.content.Context;
import android.database.Cursor;

import bsi.mpoo.traineeufrpe.Persistencia.CurriculoDAO.CurriculoDAO;
import bsi.mpoo.traineeufrpe.dominio.Curriculo.Curriculo;
import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;
import bsi.mpoo.traineeufrpe.infra.TraineApp.TraineeApp;
import bsi.mpoo.traineeufrpe.Persistencia.PessoaDAO.PessoaDAO;
import bsi.mpoo.traineeufrpe.Persistencia.EstagiarioDAO.EstagiarioDAO;
import bsi.mpoo.traineeufrpe.dominio.Pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.dominio.Estagiario.Estagiario;
public class LoginServices {
    private PessoaDAO PessoaDAO;
    private EstagiarioDAO estagiarioDAO;
    private CurriculoDAO curriculoDAO;
    private TraineeApp trainee;
    public LoginServices(Context context) {
        PessoaDAO = new PessoaDAO(context );
        estagiarioDAO = new EstagiarioDAO(context);
        curriculoDAO = new CurriculoDAO(context);

    }
    public boolean logar(Estagiario estagiario) {
        Estagiario estagiarioLogin = this.estagiarioDAO.getEstagiarioByEmaileSenha(estagiario.getEmail(), estagiario.getSenha(), trainee.getContext());
        boolean taLogado = false;
        if (estagiarioLogin != null) {
            Pessoa pessoa = this.PessoaDAO.getIdEstagiario(estagiarioLogin.getId());
            pessoa.setEstagiario(estagiarioLogin);
            this.iniciarSessao(pessoa);
            taLogado = true;
        }
        return taLogado;
    }
    public boolean cadastrar(Pessoa pessoa,Context context) {
        if (verificarEmail(pessoa.getEstagiario().getEmail(),context)) {
            return false;
        }
        else {
            long result = this.estagiarioDAO.inserirEstagiario(pessoa.getEstagiario());
            pessoa.getEstagiario().setId(result);
            pessoa.setEstagiario(this.estagiarioDAO.getEstagiarioByEmail( pessoa.getEstagiario().getEmail(),context ));

            long result2 = this.PessoaDAO.inserirPessoa(pessoa);
            pessoa.setId(result2);
            Sessao.instance.setPessoa(pessoa);
            Sessao.instance.getPessoa().getEstagiario().setCurriculo(Sessao.instance.getCurriculo());
            return true;
        }
    }

    public Curriculo cadastrar(Curriculo curriculo, Context context) {
        long result;
        result = this.curriculoDAO.inserirCurriculo(curriculo);
        if (result!= -1){
            curriculo.setId(result);
            return curriculo;
        }else{
            return null;
        }


    }
    private boolean verificarEmail(String email,Context context) {
        Estagiario estagiarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email,context);
        if (estagiarioEmail != null) {
            return true;
        }
        return  false;
    }
    private void iniciarSessao(Pessoa pessoa) {
        Sessao.instance.setPessoa(pessoa);
    }
    public void alterarFotoEstagiario(Estagiario estagiario) {
        estagiarioDAO.mudarFotoEstagiario(estagiario);
    }
}
