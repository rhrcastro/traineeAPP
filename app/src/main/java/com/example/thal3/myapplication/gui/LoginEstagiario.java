package com.example.thal3.myapplication.gui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.myapplication.R;
import com.example.thal3.myapplication.dominio.Estagiario;
import com.example.thal3.myapplication.negocio.LoginServices;
import com.example.thal3.myapplication.infra.Validacao;

public class LoginEstagiario extends AppCompatActivity {

    FloatingActionButton cadastro;
    Button butlogin;
    private EditText edtEmail;
    private EditText edtSenha;
    private Validacao validacao = new Validacao();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_estagiario);
        this.edtEmail = findViewById(R.id.editEmail1);
        this.edtSenha = findViewById(R.id.editSenha1);
        this.edtEmail.requestFocus();

        cadastro = (FloatingActionButton) findViewById(R.id.cadastro);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaCadastroEstagiario = new Intent(getBaseContext(), CadastroEstagiario.class);
                startActivity(abreTelaCadastroEstagiario);
            }
        });

        butlogin = (Button)findViewById(R.id.butlogin);
        butlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login() {
        if (!this.verificarCampos()) {
            return;
        }
        else {
            LoginServices loginServices = new LoginServices(this);
            boolean taLogado = loginServices.logar(this.criarEstagiario());
            if (taLogado) {
                Toast.makeText(getApplicationContext(),"Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                goHome();
            } else {
                Toast.makeText(getApplicationContext(),"Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void goHome() {
        startActivity(new Intent(LoginEstagiario.this, TelaPrincipalEstagiario.class));
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
