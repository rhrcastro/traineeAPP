package bsi.mpoo.traineeufrpe.gui.estagiario.acesso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.main.ActHome;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.Criptografia;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class MudarSenhaEstagiario extends AppCompatActivity {
    private final LoginServices loginServices = new LoginServices(this);
    private final ValidacaoGUI validacaoGUI = new ValidacaoGUI();
    private EditText Senha;
    private EditText Confirma;
    private String senha1;
    private String senha2;
    private Button AlteraSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha_estagiario);
        Senha = findViewById(R.id.editSenhaEsqueceu);
        Confirma = findViewById(R.id.editConfirmaSenhaEsqueceu);
        AlteraSenha = findViewById(R.id.AlteraSenha);
        AlteraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MudaSenha();
            }
        });

    }

    private void MudaSenha() {
        if (isCamposValidos()){
            SessaoEstagiario.instance.getPessoa().getEstagiario().setSenha(Criptografia.criptografar(senha1));
            loginServices.alterarSenha(SessaoEstagiario.instance.getPessoa().getEstagiario());
            Intent TelaPrincipal = new Intent(MudarSenhaEstagiario.this, ActCadastroLoginEstagiario.class);
            startActivity(TelaPrincipal);
            Toast.makeText(this, "Senha Alterada", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Digite uma senha válida", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActHome.class));
        finish();
    }

    private void capturarTextos() {
        senha1 = Senha.getText().toString().trim();
        senha2 = Confirma.getText().toString().trim();
    }

    private boolean isCamposValidos() {
        capturarTextos();
        if (ValidacaoGUI.isCampoVazio(senha1)) {
            Senha.setError("Campo vazio");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(senha2)) {
            Confirma.setError("Campo vazio");
            return false;
        }
        if(! validacaoGUI.isSenhasIguais(senha1, senha2) ){
            Toast.makeText(this, "Senhas Devem ser iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
