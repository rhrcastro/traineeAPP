package bsi.mpoo.traineeufrpe.gui.empregador.gui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.example.thal3.trainee.R;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.TelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.fragment.FragmentMinhaVaga;
import bsi.mpoo.traineeufrpe.infra.Database.Database;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.VagaServices;

public class CadastrarVaga extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner edtArea;
    EditText edtNomeVaga, editBolsa, editRequisitos, editObs;
    Button publicarVaga;
    private VagaServices vagaServices = new VagaServices(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_vaga);
        edtNomeVaga = (EditText)findViewById(R.id.edtNomeVaga);
        editBolsa = (EditText)findViewById(R.id.editBolsa);
        editRequisitos = (EditText)findViewById(R.id.editRequisitos);
        editObs = (EditText)findViewById(R.id.editObs);
        publicarVaga = (Button)findViewById(R.id.publicarVaga);
        edtArea = (Spinner)findViewById(R.id.Area);
        edtArea.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtArea.setAdapter(adapter);
        publicarVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastrar();
            }
        });

    }

    private void Cadastrar() {
        vagaServices.cadastrarVaga(criarVagar(), this);
        Intent volta = new Intent(this, TelaEmpregadorPrincipal.class);
        startActivity(volta);

    }

    private Vaga criarVagar() {
        String nome = edtNomeVaga.getText().toString().trim();
        String bolsa = editBolsa.getText().toString().trim();
        String req = editRequisitos.getText().toString().trim();
        String obs = editObs.getText().toString().trim();
        String area = publicarVaga.toString();
        Vaga vaga = new Vaga();
        vaga.setArea(area);
        vaga.setBolsa(bolsa);
        vaga.setNome(nome);
        vaga.setObs(obs);
        vaga.setRequisito(req);
        vaga.setEmpregador(SessaoEmpregador.instance.getEmpregador());
        return vaga;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
