package bsi.mpoo.traineeufrpe.gui.estagiario.gui.TelaCurriculo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.dominio.Estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.Pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_nova.TelaEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;
import bsi.mpoo.traineeufrpe.negocio.LoginServices.LoginServices;

public class TelaCurriculo extends AppCompatActivity {

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
        setContentView(R.layout.activity_curriculo);
        this.edtNome = findViewById(R.id.editNomeCadastroEst);
        this.edtEmail = findViewById(R.id.editEmailCadastroEst);
        this.edtCPF = findViewById(R.id.editCpf);
        this.edtSenha = findViewById(R.id.editSenhaCadastro);
        this.edtConfSenha = findViewById(R.id.editConfirmaSenha);
        this.edtCidade = findViewById(R.id.editCidade);

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
        Pessoa nova = criarPessoa();
        Estagiario novo = criarEstagiario();
        nova.setEstagiario(novo);
        resultado = this.loginServices.cadastrar(nova,this);
        if (resultado == true) {
            Toast.makeText(this,"Conta Criada",Toast.LENGTH_SHORT).show();
            Intent abreTelaPrincipal =  new Intent(this, TelaEstagiarioPrincipal.class);
            startActivity(abreTelaPrincipal);
        } else {
            Toast.makeText(this,"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
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
        String cidade = edtCidade.getText().toString().trim();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setCpf( cpf );
        pessoa.setCidade(cidade);
        return pessoa;
    }
    private Estagiario criarEstagiario() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        Estagiario estagiario = new Estagiario();
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        estagiario.setCurriculo(Sessao.instance.getCurriculo());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_empregador);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        byte[] bitmapdata = blob.toByteArray();
        estagiario.setFoto(bitmapdata);
        return estagiario;
    }


}