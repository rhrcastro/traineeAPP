package com.example.thal3.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.myapplication.dominio.Curriculo;
import com.example.thal3.myapplication.dominio.Usuario;
import com.example.thal3.myapplication.persistencia.BancoController;
import com.example.thal3.myapplication.persistencia.CriaBanco;

public class CadastroEstagiario extends AppCompatActivity {
    private static final int Versao = 1;


    CriaBanco teste = new CriaBanco(this);

    Button cadastrar;
    EditText edtNome;
    EditText edtEmail;
    EditText edtCPF;
    EditText edtSenha;
    EditText edtConfSenha;
    EditText edtArea;
    EditText edtCidade;

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
        cadastrar = (Button)findViewById(R.id.cadastrar);
        String resultado;
        Usuario usuario = new Usuario();

        resultado = teste.addUser(new Usuario("Thales","140299","thal3s49@hotmail.com","Recife" ));
        Toast.makeText(CadastroEstagiario.this, resultado, Toast.LENGTH_LONG).show();

        Intent abreTelaCadastroCurriculo = new Intent(getBaseContext(), Curriculo.class);
        startActivity(abreTelaCadastroCurriculo);

    }

    private boolean validaCampos() {
        boolean res = false;

        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String cpf = edtCPF.getText().toString();
        String senha = edtSenha.getText().toString();
        String confSenha = edtConfSenha.getText().toString();
        String area = edtArea.getText().toString();
        String cidade = edtCidade.getText().toString();

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
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("Há campos inválidos ou em branco");
            dlg.setNeutralButton("OK", null);
            dlg.show();
            return false;
        }else{
            return true;
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





