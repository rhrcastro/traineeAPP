package bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.PerfilEmpregador;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;

public class PerfilEmpregador extends AppCompatActivity {
    private String nomeEmpresa;
    private String localizacao;
    private long id;

    private TextView edtNomeEmpresa;
    private TextView edtLocalizacao;
    private EditText descricao;
    private TextView edtMinhasVagas;
    private ImageView imgEmpresa;


    public PerfilEmpregador() {
        this.nomeEmpresa = SessaoEmpregador.getInstance().getEmpregador().getNome();
        this.localizacao = SessaoEmpregador.getInstance().getEmpregador().getCidade();
        this.id = SessaoEmpregador.getInstance().getEmpregador().getId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_empregador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgEmpresa = findViewById(R.id.imagemEmpresa);
        edtNomeEmpresa = findViewById(R.id.nomeEmpresa);
        edtNomeEmpresa.setText(this.nomeEmpresa);
        edtLocalizacao = findViewById(R.id.localizacaoEmpresa);
        edtLocalizacao.setText(this.localizacao);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
