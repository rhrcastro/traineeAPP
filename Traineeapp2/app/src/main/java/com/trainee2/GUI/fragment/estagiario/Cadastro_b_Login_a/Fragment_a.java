package com.trainee2.GUI.fragment.estagiario.Cadastro_b_Login_a;

import android.content.Context;
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

import com.trainee2.GUI.estagiario.TelaPrincipalEstagiario;
import com.trainee2.R;
import com.trainee2.dominio.Estagiario;
import com.trainee2.infra.Sessao;
import com.trainee2.infra.TraineeApp;
import com.trainee2.infra.Validacao;
import com.trainee2.negocio.LoginServices;

public class Fragment_a extends Fragment {
    private Sessao sessao = Sessao.getInstance();
    private TraineeApp traineeApp = new TraineeApp();
    private EditText senhaEstagiarioLogin, emailEstagiarioLogin;
    private Validacao validacao = new Validacao();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_a, container, false);
        mContext = v.getContext();
        emailEstagiarioLogin = (EditText)v.findViewById(R.id.emailLogin);
        senhaEstagiarioLogin = (EditText)v.findViewById(R.id.senhaLogin);
        Button botLogin = (Button)v.findViewById(R.id.botLogin);
        botLogin.setOnClickListener(new View.OnClickListener() {
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
            LoginServices loginServices = new LoginServices(getActivity());
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
        startActivity(new Intent(getActivity(), TelaPrincipalEstagiario.class));
    }



    private boolean verificarCampos(){
        String email = this.emailEstagiarioLogin.getText().toString().trim();
        String senha = this.senhaEstagiarioLogin.getText().toString().trim();
        if (this.validacao.verificarCampoEmail(email)) {
            this.emailEstagiarioLogin.setError("Email Inválido");
            return false;
        } else if (this.validacao.verificarCampoVazio(senha)) {
            this.senhaEstagiarioLogin.setError("Senha Inválida");
            return false;
        } else {
            return true;
        }
    }

    private Estagiario criarEstagiario() {
        Estagiario estagiario = new Estagiario();
        String email = this.emailEstagiarioLogin.getText().toString().trim();
        String senha = this.senhaEstagiarioLogin.getText().toString().trim();
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        return estagiario;
    }



}
