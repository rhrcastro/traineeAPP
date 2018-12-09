package bsi.mpoo.traineeufrpe.gui.empregador.gui.LoginEmpregador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.CadastroEmpregador.CadastroEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_antiga.HomeEmpregador;
import bsi.mpoo.traineeufrpe.gui.main.gui.Main.MainActivity;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.EmpregadorServices;

public class LoginEmpregador extends AppCompatActivity {
    FloatingActionButton cadastroEmpregador;
    Button loginEmpregador;
    private Validacao validacao = new Validacao();
    private EditText edtEmailEmpregador;
    private EditText edtSenhaEmpregador;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empregador);
        this.edtEmailEmpregador = findViewById(R.id.editEmail2);
        this.edtSenhaEmpregador = findViewById(R.id.editSenha2);
        this.edtEmailEmpregador.requestFocus();
        cadastroEmpregador = (FloatingActionButton) findViewById(R.id.cadastro2);
        cadastroEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCadastroEmpregador = new Intent(getBaseContext(), CadastroEmpregador.class);
                startActivity(abreCadastroEmpregador);
            }
        });
        loginEmpregador = (Button)findViewById(R.id.butlogin2);
        loginEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logarEmpregador();
            }
        });
    }
    public void logarEmpregador() {
        if (!this.verificarCampos()) {
            return;
        } else {
            EmpregadorServices empregadorServices  = new EmpregadorServices(this);
            boolean taLogado = empregadorServices.logarEmpregador(this.criarEmpregador());
            if (taLogado) {
                Toast.makeText(getApplicationContext(),"Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                goHome();
            } else {
                Toast.makeText(getApplicationContext(),"Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            }

        }

    }
    private void goHome() {
        startActivity(new Intent(LoginEmpregador.this, HomeEmpregador.class));
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
        startActivity(new Intent(LoginEmpregador.this, MainActivity.class));
    }
}
