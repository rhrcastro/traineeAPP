package bsi.mpoo.traineeufrpe.gui.empregador.gui.CadastroEmpregador.fragment_login_cadastro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.fragment.TelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.gui.main.gui.Main.MainActivity;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.EmpregadorServices;

public class FragmentLoginEmpregador extends Fragment {
    Button loginEmpregador;
    private Validacao validacao = new Validacao();
    private EditText edtEmailEmpregador;
    private EditText edtSenhaEmpregador;
    private TextView forgot;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_login_empregador, container, false);
        this.edtEmailEmpregador = v.findViewById(R.id.edtEmailEmpregador);
        this.edtSenhaEmpregador = v.findViewById(R.id.edtSenhaEmpregador);
        this.edtEmailEmpregador.requestFocus();
        loginEmpregador = (Button)v.findViewById(R.id.butlogin2);
        loginEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logarEmpregador();
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
        startActivity(new Intent(getContext(), TelaEmpregadorPrincipal.class));
    }
    private boolean verificarCampos(){
        String email = this.edtEmailEmpregador.getText().toString().trim();
        String senha = this.edtSenhaEmpregador.getText().toString().trim();
        if (this.validacao.verificarCampoEmail(email)) {
            this.edtEmailEmpregador.setError("Email Inválido");
            return false;
        } else if (this.validacao.verificarCampoVazio(senha)) {
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
        empregador.setSenha(senha);
        return empregador;
    }
    public void onBackPressed() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}