package com.example.thal3.myapplication.gui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.myapplication.R;
import com.example.thal3.myapplication.negocio.EstagiarioServices;
import com.example.thal3.myapplication.negocio.LoginServices;
import com.example.thal3.myapplication.dominio.Pessoa;
import com.example.thal3.myapplication.dominio.Estagiario;

import java.util.ArrayList;

public class CadastroEstagiario extends AppCompatActivity {

    Button cadastrar;
    private LoginServices loginServices = new LoginServices(this);
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtCPF;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private EditText edtCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estagiario);

        this.edtNome = findViewById(R.id.editNomeCadastroEst);
        this.edtEmail = findViewById(R.id.editEmailCadastroEst);
        this.edtCPF = findViewById(R.id.editCpf);
        this.edtSenha = findViewById(R.id.editSenhaCadastro);
        this.edtConfSenha = findViewById(R.id.editConfirmaSenha);
        this.edtCidade = findViewById(R.id.editCidade);

        this.cadastrar = findViewById(R.id.cadastrar);
        this.cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

    }
    public void cadastrar() {
        if (!this.validaCampos()) {
            return;
        }

        if (this.loginServices.cadastrar(criarPessoa(),this)) {
            Toast.makeText(getApplicationContext(),"Conta Criada",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validaCampos() {
        boolean camposVazios = false;
        ArrayList<String> logError = new ArrayList<>();

        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String cpf = edtCPF.getText().toString();
        String senha = edtSenha.getText().toString();
        String confSenha = edtConfSenha.getText().toString();
        String cidade = edtCidade.getText().toString();

        if (camposVazios = isCampoVazio(nome)) {
            edtNome.requestFocus();
        } else if (camposVazios = isCampoVazio(email)) {
            edtEmail.requestFocus();
        } else if (camposVazios = isCampoVazio(cpf)) {
            edtCPF.requestFocus();
        } else if (camposVazios = isCampoVazio(senha)) {
            edtSenha.requestFocus();
        } else if (camposVazios = isCampoVazio(confSenha)) {
            edtConfSenha.requestFocus();
        }
          else if (camposVazios = isCampoVazio(cidade)) {
            edtCidade.requestFocus();
        }
        if (camposVazios) {
            logError.add("- Preencha todos os campos vazios.");
        }

        if (!EstagiarioServices.isEmailValido(email)) {
            logError.add("- O email não é válido.");
        }

        if (!EstagiarioServices.isCPF(cpf)) {
            logError.add("- O CPF não é válido.");
        }

        if (!EstagiarioServices.isSenhaIgual(senha, confSenha)) {
            logError.add("- As senhas não conferem.");
        }

        if (logError.size() > 0) {
            String msg = new String();
            for (String erro : logError) {
                msg = msg.concat(erro + "\n");
            }
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(msg);
            dlg.setNeutralButton("OK", null);
            dlg.show();
            return false;
        } return true;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }
    private Pessoa criarPessoa() {
        String nome = edtNome.getText().toString().trim();
        String cpf = edtCPF.getText().toString().trim();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setCpf( cpf );
        pessoa.setEstagiario(this.criarEstagiario());
        return pessoa;
    }
    private Estagiario criarEstagiario() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        Estagiario estagiario = new Estagiario();
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        return estagiario;
    }

}
