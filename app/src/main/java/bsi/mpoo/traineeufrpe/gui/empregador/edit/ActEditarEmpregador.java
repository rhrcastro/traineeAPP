package bsi.mpoo.traineeufrpe.gui.empregador.edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.persistencia.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.main.ActHome;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.Validacao.Validacao;

public class ActEditarEmpregador extends AppCompatActivity {
    private Button save, del;
    private EditText nome;
    private EditText cidade;
    private EditText senha;
    private Validacao validacao = new Validacao();


    private int selectedid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EmpregadorDAO empregadorDAO = new EmpregadorDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);
        save = (Button) findViewById(R.id.save);
        del = (Button) findViewById(R.id.del);
        nome = (EditText) findViewById(R.id.editAlterarNome);
        cidade = (EditText) findViewById(R.id.editAlterarCidade);
        senha = (EditText) findViewById(R.id.editAlterarSenha) ;
        Intent received = getIntent();
        selectedid = received.getIntExtra("id", -1);
        nome.setText(SessaoEmpregador.getInstance().getEmpregador().getNome());
        cidade.setText(SessaoEmpregador.getInstance().getEmpregador().getCidade());
        senha.setText(SessaoEmpregador.getInstance().getEmpregador().getSenha());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alterarDadosEmpregador(empregadorDAO);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empregadorDAO.deletarEmpregador(selectedid);
                Toast.makeText(getBaseContext(), "Conta excluída com sucesso.", Toast.LENGTH_SHORT).show();
                Intent voltar = new Intent(getBaseContext(), ActHome.class);
                startActivity(voltar);
            }
        });
    }

    private boolean verificarCampos() {
        String nomeEmpregador = nome.getText().toString().trim();
        String senhaEmpregador = senha.getText().toString().trim();
        String cidadeEmpregador = cidade.getText().toString().trim();
        if (validacao.verificarCampoVazio(nomeEmpregador)) {
            this.nome.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(senhaEmpregador)) {
            this.senha.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cidadeEmpregador)) {
            this.cidade.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }
    public void alterarDadosEmpregador(EmpregadorDAO empregadorDAO) {
        if (!this.verificarCampos()) {
            String nomeEmpregador = nome.getText().toString().trim();
            Empregador empregador = empregadorDAO.getId(selectedid, getBaseContext());
            empregador.setNome(nomeEmpregador);
            empregadorDAO.mudarNomeEmpregador(empregador);
            Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "O nome não pode ser vazio", Toast.LENGTH_SHORT).show();
        }
    }
}
