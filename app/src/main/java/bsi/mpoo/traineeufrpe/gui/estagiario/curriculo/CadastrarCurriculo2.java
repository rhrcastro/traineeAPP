package bsi.mpoo.traineeufrpe.gui.estagiario.curriculo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class CadastrarCurriculo2 extends AppCompatActivity {

    private EditText XP;
    private EditText Relacionamento;
    private EditText Objetivo;
    private EditText Basico;
    private EditText Forte;
    private EditText Disciplinas;
    private Button cadastrar;
    private final LoginServices loginServices = new LoginServices(this);
    private ValidacaoGUI validacaoGUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_curriculo2);
        constroi();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessaoEstagiario.instance.getPessoa() != null){
                    Curriculo curriculo = CriarCurriculo2();
                    loginServices.SubstituirCurriculo(SessaoEstagiario.instance.getPessoa().getEstagiario(), curriculo);
                    Toast.makeText(CadastrarCurriculo2.this, "Alterações realizadas com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Curriculo curriculo = CriarCurriculo();
                    if (curriculo != null){
                        salvar(curriculo);
                        SessaoEstagiario.instance.setCurriculo(curriculo);
                        cadastrarUsuario();
                    }
                }
            }
        });
    }

    private void cadastrarUsuario() {
        Intent intent = new Intent(CadastrarCurriculo2.this, ActCadastroEstagiario.class);
        startActivity(intent);
        finish();
    }

    private void salvar(Curriculo curriculo) {
        LoginServices loginServices = new LoginServices(this);
        loginServices.cadastrarCurriculoNoBanco(curriculo);
    }

    private boolean isCamposValidos(){
        if (ValidacaoGUI.isCampoVazio(XP.getText().toString())) {
            this.XP.setError("Campo Inválido");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(Relacionamento.getText().toString())) {
            this.Relacionamento.setError("Campo Inválido");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(Objetivo.getText().toString())) {
            this.Objetivo.setError("Campo Inválido");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(Basico.getText().toString())) {
            this.Basico.setError("Campo Inválido");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(Forte.getText().toString())) {
            this.Forte.setError("Campo Inválido");
            return false;
        } else if (ValidacaoGUI.isCampoVazio(Disciplinas.getText().toString())) {
            this.Disciplinas.setError("Campo Inválido");
            return false;
        } return true;
    }

    private Curriculo CriarCurriculo() {
        Curriculo curriculo = SessaoEstagiario.instance.getCurriculo();
        curriculo.setLink("");
        curriculo.setAreaAtuacao(SessaoEstagiario.instance.getCurriculo().getAreaAtuacao());
        curriculo.setInstituicao(SessaoEstagiario.instance.getCurriculo().getInstituicao());
        curriculo.setCurso(SessaoEstagiario.instance.getCurriculo().getCurso());

        if (ValidacaoGUI.isCampoVazio(XP.getText().toString())) {
            this.XP.setError("Campo Inválido");
            return null;
        }else {
            curriculo.setExperiencia(XP.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Relacionamento.getText().toString())) {
            this.Relacionamento.setError("Campo Inválido");
            return null;
        }else {
            curriculo.setRelacionamento(Relacionamento.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Objetivo.getText().toString())) {
            this.Objetivo.setError("Campo Inválido");
            return null;
        }else {
            curriculo.setObjetivo(Objetivo.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Basico.getText().toString())) {
            this.Basico.setError("Campo Inválido");
            return null;
        }else {
            curriculo.setConhcimentos_basicos(Basico.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Forte.getText().toString())) {
            this.Forte.setError("Campo Inválido");
            return null;
        }else {
            curriculo.setConhecimentos_especificos(Forte.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Disciplinas.getText().toString())) {
            this.Disciplinas.setError("Campo Inválido");
            return null;
        }else {
            curriculo.setDisciplinas(Disciplinas.getText().toString().trim());
        }
        return curriculo;
    }

    private Curriculo CriarCurriculo2() {
        Curriculo curriculo = new Curriculo();
        if (ValidacaoGUI.isCampoVazio(Forte.getText().toString())) {
            curriculo.setConhecimentos_especificos("");
        }else {
            curriculo.setConhecimentos_especificos(Forte.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Objetivo.getText().toString())) {
            curriculo.setObjetivo("");

        }else {
            curriculo.setObjetivo(Objetivo.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Basico.getText().toString())) {
            curriculo.setConhcimentos_basicos("");
        }else {
            curriculo.setConhcimentos_basicos(Basico.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(XP.getText().toString())) {
            curriculo.setExperiencia("");
        }else {
            curriculo.setExperiencia(XP.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Disciplinas.getText().toString())) {
            curriculo.setDisciplinas("");
        }else {
            curriculo.setDisciplinas(Disciplinas.getText().toString().trim());
        }
        if (ValidacaoGUI.isCampoVazio(Relacionamento.getText().toString())) {
            curriculo.setRelacionamento("");
        }else {
            curriculo.setRelacionamento(Relacionamento.getText().toString().trim());
        }
        return curriculo;
    }

    private void constroi() {
        XP = findViewById(R.id.editXP);
        Relacionamento = findViewById(R.id.editRelacionamento);
        Objetivo = findViewById(R.id.editObjetivo);
        Basico = findViewById(R.id.edtxpBasico);
        Forte = findViewById(R.id.edtxpForte);
        Disciplinas = findViewById(R.id.editDisciplinas);
        cadastrar = findViewById(R.id.btnQueroCandidatar);
    }
}

