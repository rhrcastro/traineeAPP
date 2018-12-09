package com.trainee2.GUI.estagiario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainee2.R;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Pessoa;
import com.trainee2.infra.Sessao;
import com.trainee2.infra.Validacao;
import com.trainee2.negocio.LoginServices;

public class TelaCadastroCurriculo extends AppCompatActivity  {
    Button cadastrar;
    private LoginServices loginServices = new LoginServices(this);
    private Validacao validacao = new Validacao();
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtCPF;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private EditText edtCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_curriculo);

        this.edtNome = findViewById(R.id.nomeCadastroEstagiario);
        this.edtEmail = findViewById(R.id.emailCadastroEstagiario);
        this.edtCPF = findViewById(R.id.cpfCadastroEstagirario);
        this.edtSenha = findViewById(R.id.senhaCadastroEstagiario);
        this.edtConfSenha = findViewById(R.id.repeteSenhaCadastroEstagiario);
        this.edtCidade = findViewById(R.id.cidadeCadastroEstagiario);

        this.cadastrar = findViewById(R.id.botCadastroPessoal);
        this.cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

    }
    public void cadastrar() {
        boolean resultado;
        if (!this.verificarCampos()) {
            return;
        }
        resultado = this.loginServices.cadastrar(criarPessoa(),this);
        if (resultado == true) {
            Toast.makeText(getApplicationContext(),"Conta Criada",Toast.LENGTH_SHORT).show();
            Intent abreTelaPrincipal =  new Intent(this, TelaPrincipalEstagiario.class);
            startActivity(abreTelaPrincipal);
        } else {
            Toast.makeText(getApplicationContext(),"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean verificarCampos() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String repetirSenha = edtConfSenha.getText().toString().trim();
        String cpf = edtCPF.getText().toString().trim();
        String cidade = edtCidade.getText().toString().trim();
        if (validacao.verificarCampoVazio(nome)){
            this.edtNome.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoEmail(email)) {
            this.edtEmail.setError("Formato de email inválido");
            return false;
        } else if (validacao.verificarCampoVazio(senha)){
            this.edtSenha.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(repetirSenha)) {
            this.edtConfSenha.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cpf)){
            this.edtCPF.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cidade)){
            this.edtCidade.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }
    private Pessoa criarPessoa() {
        String nome = edtNome.getText().toString().trim();
        String cpf = edtCPF.getText().toString().trim();
        Pessoa pessoa = Sessao.instance.getPessoa();
        pessoa.setNome(nome);
        pessoa.setCpf( cpf );
        pessoa.setEstagiario(this.criarEstagiario());
        return pessoa;
    }
    private Estagiario criarEstagiario() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        Estagiario estagiario = Sessao.instance.getPessoa().getEstagiario();
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        return estagiario;
    }


}
