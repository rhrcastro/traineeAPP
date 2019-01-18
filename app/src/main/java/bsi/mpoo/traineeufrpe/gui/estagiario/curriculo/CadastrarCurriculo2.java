package bsi.mpoo.traineeufrpe.gui.estagiario.curriculo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class CadastrarCurriculo2 extends AppCompatActivity {

    EditText XP, Relacionamento, Objetivo, Basico, Forte, Disciplinas;
    Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_curriculo2);
        constroi();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessaoEstagiario.instance.setCurriculo(Salvar(CriarCurriculo()));
                CadastrarUsuario();
            }
        });
    }


    private void CadastrarUsuario() {
        Intent intent = new Intent(CadastrarCurriculo2.this, ActCadastroEstagiario.class);
        startActivity(intent);
        finish();
    }

    private Curriculo Salvar(Curriculo curriculo) {
        LoginServices loginServices = new LoginServices(this);
        Curriculo  curriculo1 = loginServices.cadastrarCurriculoNoBanco(curriculo);
        return curriculo1;
    }



    private Curriculo CriarCurriculo() {
        Curriculo curriculo = SessaoEstagiario.instance.getCurriculo();
        curriculo.setLink("");
        curriculo.setAreaAtuacao(SessaoEstagiario.instance.getCurriculo().getAreaAtuacao());
        curriculo.setInstituicao(SessaoEstagiario.instance.getCurriculo().getInstituicao());
        curriculo.setCurso(SessaoEstagiario.instance.getCurriculo().getCurso());
        curriculo.setConhecimentos_especificos(Forte.getText().toString().trim());
        curriculo.setObjetivo(Objetivo.getText().toString().trim());
        curriculo.setConhcimentos_basicos(Basico.getText().toString().trim());
        curriculo.setExperiencia(XP.getText().toString().trim());
        curriculo.setDisciplinas(Disciplinas.getText().toString().trim());
        curriculo.setRelacionamento(Relacionamento.getText().toString().trim());
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
