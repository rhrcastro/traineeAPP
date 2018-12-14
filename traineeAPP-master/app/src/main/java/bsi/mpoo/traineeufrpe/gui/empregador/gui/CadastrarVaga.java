package bsi.mpoo.traineeufrpe.gui.empregador.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.TelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices.VagaServices;

public class CadastrarVaga extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner edtArea, editBolsa;
    EditText edtNomeVaga, editRequisitos, editObs;
    Button publicarVaga;
    private VagaServices vagaServices = new VagaServices(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_vaga);
        edtNomeVaga = (EditText)findViewById(R.id.edtNomeVaga);
        editRequisitos = (EditText)findViewById(R.id.editRequisitos);
        editObs = (EditText)findViewById(R.id.editObs);
        publicarVaga = (Button)findViewById(R.id.publicarVaga);

        edtArea = (Spinner)findViewById(R.id.Area);
        edtArea.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editBolsa = (Spinner)findViewById(R.id.editBolsa);
        editBolsa.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bolsas, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edtArea.setAdapter(adapter);
        editBolsa.setAdapter(adapter1);
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
        String bolsa = editBolsa.getSelectedItem().toString();
        String req = editRequisitos.getText().toString().trim();
        String obs = editObs.getText().toString().trim();
        String area = edtArea.getSelectedItem().toString();
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