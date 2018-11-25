package com.example.thal3.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroEstagiario extends AppCompatActivity {

    Button cadastrar;
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtCPF;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private EditText edtArea;
    private EditText edtCidade;
    private EditText edtPergunta;
    private EditText edtResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estagiario);

        edtNome = (EditText)findViewById(R.id.editNomeCadastroEst);
        edtEmail = (EditText)findViewById(R.id.editEmailCadastroEst);
        edtCPF = (EditText)findViewById(R.id.editEmailCadastroEst);
        edtSenha = (EditText)findViewById(R.id.editSenhaCadastro);
        edtConfSenha = (EditText)findViewById(R.id.editConfirmaSenha);
        edtArea = (EditText)findViewById(R.id.editArea);
        edtCidade = (EditText)findViewById(R.id.editCidade);
        edtPergunta = (EditText)findViewById(R.id.editPergunta1);
        edtResposta = (EditText)findViewById(R.id.editResposta);


        cadastrar = (Button)findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaCampos();
                //Intent abreTelaCadastroCurriculo = new Intent(getBaseContext(), Curriculo.class);
               // startActivity(abreTelaCadastroCurriculo);
            }
        });

    }

    private void validaCampos() {
        boolean res = false;

        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String cpf = edtCPF.getText().toString();
        String senha = edtSenha.getText().toString();
        String confSenha = edtConfSenha.getText().toString();
        String area = edtArea.getText().toString();
        String cidade = edtCidade.getText().toString();
        String pergunta = edtPergunta.getText().toString();
        String resposta = edtResposta.getText().toString();

        if (res = isCampoVazio(nome)) {
            edtNome.requestFocus();
        } else if (res = !isEmailValido(email)) {
            edtEmail.requestFocus();
        } else if (res = isCampoVazio(cpf)) {
            edtCPF.requestFocus();
        } else if (res = isCampoVazio(senha)) {
            edtSenha.requestFocus();
        } else if (res = isCampoVazio(confSenha)) {
            edtConfSenha.requestFocus();
        } else if (res = isCampoVazio(area)) {
            edtArea.requestFocus();
        } else if (res = isCampoVazio(cidade)) {
            edtCidade.requestFocus();
        } else if (res = isCampoVazio(pergunta)) {
            edtPergunta.requestFocus();
        } else if (res = isCampoVazio(resposta)) {
            edtResposta.requestFocus();
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("Há campos inválidos ou em branco");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    private boolean isEmailValido(String email) {
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }
}
