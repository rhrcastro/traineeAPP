package bsi.mpoo.traineeufrpe.gui.estagiario.gui.Cadastro.fragment_cadastro_e_login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.dominio.Estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_nova.TelaEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;
import bsi.mpoo.traineeufrpe.negocio.LoginServices.LoginServices;

public class FragmentLoginEstagiario extends Fragment {

    private EditText edtEmail;
    private EditText edtSenha;
    private Validacao validacao = new Validacao();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_login_estagiario, container, false);
        this.edtEmail = v.findViewById(R.id.emailLogin);
        this.edtSenha = v.findViewById(R.id.senhaLogin);
        Button butlogin = (Button)v.findViewById(R.id.botLog);
        butlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return v;
    }

    public void login() {
        if (!this.verificarCampos()) {
            return;
        }
        else {
            LoginServices loginServices = new LoginServices(getContext());
            boolean taLogado = loginServices.logar(this.criarEstagiario());
            if (taLogado) {
                Toast.makeText(getContext(),"Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                goHome();
            } else {
                Toast.makeText(getContext(),"Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void goHome() {
        startActivity(new Intent(getActivity(), TelaEstagiarioPrincipal.class));
        finishActivity();
    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
    private Estagiario criarEstagiario() {
        String email = this.edtEmail.getText().toString().trim();
        String senha = this.edtSenha.getText().toString().trim();
        Estagiario estagiario = new Estagiario();
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        return estagiario;
    }
    private boolean verificarCampos(){
        String email = this.edtEmail.getText().toString().trim();
        String senha = this.edtSenha.getText().toString().trim();
        if (this.validacao.verificarCampoEmail(email)) {
            this.edtEmail.setError("Email Inválido");
            return false;
        } else if (this.validacao.verificarCampoVazio(senha)) {
            this.edtSenha.setError("Senha Inválida");
            return false;
        } else {
            return true;
        }
    }

}

