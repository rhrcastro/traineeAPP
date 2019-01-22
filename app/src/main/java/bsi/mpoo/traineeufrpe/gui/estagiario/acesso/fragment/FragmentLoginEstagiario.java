package bsi.mpoo.traineeufrpe.gui.estagiario.acesso.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.estagiario.acesso.ActEsqueciSenhaEstagiario;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.ActEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.Criptografia;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class FragmentLoginEstagiario extends Fragment {

    private ValidacaoGUI validacaoGUI = new ValidacaoGUI();
    private TextView forgot;
    private EditText edtEmail;
    private EditText edtSenha;
    private String email;
    private String senha;
    Button btnLogin;
    private Criptografia criptografia;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_estagiario, container, false);
        edtEmail = v.findViewById(R.id.emailLogin);
        edtSenha = v.findViewById(R.id.senhaLogin);
        btnLogin = (Button)v.findViewById(R.id.botLog);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
        forgot = v.findViewById(R.id.forgot2);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActEsqueciSenhaEstagiario.class));
            }
        });
        return v;
    }

    public void logar() {
        capturaTextos();
        if (!isCamposValidos()) {
            return;
        }
        LoginServices loginServices = new LoginServices(getContext());
        boolean taLogado = loginServices.fazerLogin(email, criptografia.criptografar(senha));
        if (taLogado) {
            Toast.makeText(getContext(),"Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
            goHome();
        } else {
            Toast.makeText(getContext(),"Email ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCamposValidos(){
        if (this.validacaoGUI.isEmailInvalido(email)) {
            this.edtEmail.setError("Email Inválido");
            return false;
        } else if (this.validacaoGUI.isCampoVazio(senha)) {
            this.edtSenha.setError("Senha Inválida");
            return false;
        } return true;
    }

    private void capturaTextos() {
        email = this.edtEmail.getText().toString().trim();
        senha = this.edtSenha.getText().toString().trim();
    }

    private void goHome() {
        startActivity(new Intent(getActivity(), ActEstagiarioPrincipal.class));
        finishActivity();
    }

    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }

}

