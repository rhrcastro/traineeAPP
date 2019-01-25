package bsi.mpoo.traineeufrpe.gui.estagiario.curriculo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.ActEstagiarioPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.Criptografia;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class ActCadastroEstagiario extends AppCompatActivity {

    private LoginServices loginServices = new LoginServices(this);
    private ValidacaoGUI validacaoGUI = new ValidacaoGUI();
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtCPF;
    private EditText edtSenha1;
    private EditText edtSenha2;
    private EditText edtCidade;
    private String nome;
    private String email;
    private String cpf;
    private String senha1;
    private String senha2;
    private String cidade;
    Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estagiario);
        findEditTexts();
        this.cadastrar = findViewById(R.id.botCadastroPessoal);
        this.cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private void findEditTexts() {
        edtNome = findViewById(R.id.editNomeCadastroEst);
        edtEmail = findViewById(R.id.editEmailCadastroEst);
        edtCPF = findViewById(R.id.editCpf);
        edtSenha1 = findViewById(R.id.editSenhaCadastro);
        edtSenha2 = findViewById(R.id.editConfirmaSenha);
        edtCidade = findViewById(R.id.editCidade);
    }

    public void cadastrar() {
        capturarTextos();
        if (!isCamposValidos()){
            return;
        }
        Pessoa novaPessoa = criarPessoa();
        Estagiario novoEstagiario = criarEstagiario();
        novaPessoa.setEstagiario(novoEstagiario);
        boolean resultado = this.loginServices.cadastrarPessoaNoBanco(novaPessoa,this);
        if (resultado) {
            Toast.makeText(this,"Conta Criada",Toast.LENGTH_SHORT).show();
            Intent abreTelaPrincipal =  new Intent(this, ActEstagiarioPrincipal.class);
            startActivity(abreTelaPrincipal);
            ActCadastroEstagiario.this.finish();
        } else {
            Toast.makeText(this,"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
        }
    }

    private void capturarTextos() {
        nome = edtNome.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        senha1 = edtSenha1.getText().toString().trim();
        senha2 = edtSenha2.getText().toString().trim();
        cpf = edtCPF.getText().toString().trim();
        cidade = edtCidade.getText().toString().trim();
    }

    private boolean isCamposValidos() {
        if (ValidacaoGUI.isCampoVazio(nome)){
            edtNome.setError("Campo vazio");
            return false;
        } else if (ValidacaoGUI.isEmailInvalido(email)) {
            edtEmail.setError("Email inválido");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(senha1)){
            edtSenha1.setError("Campo vazio");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(senha2)) {
            edtSenha2.setError("Campo vazio");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(cpf)){
            edtCPF.setError("Campo vazio");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(cidade)) {
            edtCidade.setError("Campo vazio");
            return false;
        } if(! validacaoGUI.isSenhasIguais(senha1, senha2) ){
            Toast.makeText(this, "Senhas Devem ser iguais", Toast.LENGTH_SHORT).show();
            return false;
        }return true;
    }

    private Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        pessoa.setCidade(cidade);
        return pessoa;
    }
    private Estagiario criarEstagiario() {
        Estagiario estagiario = new Estagiario();
        estagiario.setEmail(email);
        String senha = Criptografia.criptografar(senha1);
        estagiario.setSenha(senha);
        estagiario.setCurriculo(SessaoEstagiario.instance.getCurriculo());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_empregador);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        byte[] bitmapdata = blob.toByteArray();
        estagiario.setFoto(bitmapdata);
        return estagiario;
    }

}