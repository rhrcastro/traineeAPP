package bsi.mpoo.traineeufrpe.gui.empregador.acesso.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.empregador.acesso.ActEsqueciSenhaEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.gui.main.ActHome;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;

public class FragmentLoginEmpregador extends Fragment {
    Button loginEmpregador;
    private ValidacaoGUI validacaoGUI = new ValidacaoGUI();
    private EditText edtEmailEmpregador;
    private EditText edtSenhaEmpregador;
    private TextView forgot2;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.fragment_login_empregador, container, false);
        this.edtEmailEmpregador = v.findViewById(R.id.edtEmailEmpregador);
        this.edtSenhaEmpregador = v.findViewById(R.id.edtSenhaEmpregador);
        this.edtEmailEmpregador.requestFocus();
        loginEmpregador = v.findViewById(R.id.butlogin2);
        loginEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logarEmpregador();
            }
        });
        forgot2 = v.findViewById(R.id.forgot2);
        forgot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActEsqueciSenhaEmpregador.class));
            }
        });
        return v;
    }
    public void logarEmpregador() {
        if (!this.verificarCampos()) {
            return;
        } else {
            EmpregadorServices empregadorServices  = new EmpregadorServices(getContext());
            boolean taLogado = empregadorServices.logarEmpregador(this.criarEmpregador());
            if (taLogado) {
                Toast.makeText(getContext(),"Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                goHome();
            } else {
                Toast.makeText(getContext(),"Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            }

        }

    }
    private void goHome() {
        startActivity(new Intent(getContext(), ActEmpregadorPrincipal.class));
        finishActivity();
    }
    private boolean verificarCampos(){
        String email = this.edtEmailEmpregador.getText().toString().trim();
        String senha = this.edtSenhaEmpregador.getText().toString().trim();
        if (this.validacaoGUI.isEmailInvalido(email)) {
            this.edtEmailEmpregador.setError("Email Inválido");
            return false;
        } else if (this.validacaoGUI.isCampoVazio(senha)) {
            this.edtSenhaEmpregador.setError("Senha Inválida");
            return false;
        } else {
            return true;
        }
    }
    private Empregador criarEmpregador() {
        String email = this.edtEmailEmpregador.getText().toString().trim();
        String senha = this.edtSenhaEmpregador.getText().toString().trim();
        Empregador empregador = new Empregador();
        empregador.setEmail(email);
        String senhaCodificada = codificarBase64(senha);
        empregador.setSenha(senhaCodificada);
        return empregador;
    }
    public void onBackPressed() {
        startActivity(new Intent(getContext(), ActHome.class));
    }

    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }

    public static String codificarBase64 (String texto) {
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
}