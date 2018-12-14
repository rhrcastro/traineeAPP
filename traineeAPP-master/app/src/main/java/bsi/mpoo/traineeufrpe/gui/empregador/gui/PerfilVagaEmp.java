package bsi.mpoo.traineeufrpe.gui.empregador.gui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.Persistencia.VagaDAO.VagaDAO;
import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.TelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices.VagaServices;

public class PerfilVagaEmp extends AppCompatActivity {

    Spinner editBolsa;
    EditText edtNomeVaga, editRequisitos, editObs;
    FloatingActionButton saveVaga, delVaga, cancelVaga;
    private VagaServices vagaServices = new VagaServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_emp);
        edtNomeVaga = (EditText)findViewById(R.id.edtNomeVaga2);
        editRequisitos = (EditText)findViewById(R.id.editRequisitos2);
        editObs = (EditText)findViewById(R.id.editObs2);

        editBolsa = (Spinner)findViewById(R.id.editBolsa2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bolsas, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editBolsa.setAdapter(adapter1);

        popular();

        saveVaga = (FloatingActionButton)findViewById(R.id.saveVaga);
        saveVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salva();
                volta();
            }
        });

        delVaga = (FloatingActionButton)findViewById(R.id.delVaga);
        delVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del();
                volta();
            }
        });

        cancelVaga = (FloatingActionButton)findViewById(R.id.CancelVaga);
        cancelVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volta();
            }
        });


    }

    private void popular() {
        edtNomeVaga.setText(SessaoEmpregador.instance.getVaga().getNome());
        editRequisitos.setText(SessaoEmpregador.instance.getVaga().getRequisito());
        editObs.setText(SessaoEmpregador.instance.getVaga().getObs());
    }

    private void salva() {
        Vaga vaga = SessaoEmpregador.instance.getVaga();
        String nome = edtNomeVaga.getText().toString().trim();
        vaga = vagaServices.mudarNomeVaga(vaga, nome);
        String bolsa = editBolsa.getSelectedItem().toString();
        vaga = vagaServices.mudarBolsaVaga(vaga, bolsa);
        String req = editRequisitos.getText().toString().trim();
        vaga = vagaServices.mudarRequisitoVaga(vaga, req);
        String obs = editObs.getText().toString().trim();
        vaga = vagaServices.mudarObsVaga(vaga, obs);


    }

    private void volta() {
        Intent volta = new Intent(PerfilVagaEmp.this, TelaEmpregadorPrincipal.class);
        startActivity(volta);
        Toast.makeText(this, "Alterações feitas com sucesso.", Toast.LENGTH_SHORT).show();
    }

    private void del(){
        long id = SessaoEmpregador.instance.getVaga().getId();
        vagaServices.DelVaga(SessaoEmpregador.instance.getVaga().getId());
    }
}
