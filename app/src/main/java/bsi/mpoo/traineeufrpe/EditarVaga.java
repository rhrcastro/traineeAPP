package bsi.mpoo.traineeufrpe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class EditarVaga extends AppCompatActivity {

    Spinner editBolsa;
    EditText edtNomeVaga, editRequisitos, editObs;
    FloatingActionButton saveVaga, cancelVaga;
    private VagaServices vagaServices = new VagaServices(this);
    private boolean mudou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vaga);
        edtNomeVaga = (EditText)findViewById(R.id.edtNomeVaga2);
        editRequisitos = (EditText)findViewById(R.id.editRequisitos2);
        editObs = (EditText)findViewById(R.id.editObs2);

        editBolsa = (Spinner)findViewById(R.id.editBolsa2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bolsas, R.layout.spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        editBolsa.setAdapter(adapter1);

        popular();

        saveVaga = (FloatingActionButton)findViewById(R.id.saveVaga);
        saveVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudou = true;
                salva();
                volta(mudou);
            }
        });


        cancelVaga = (FloatingActionButton)findViewById(R.id.CancelVaga);
        cancelVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volta(mudou);
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

    private void volta(boolean mudou) {
        Intent volta = new Intent(EditarVaga.this, ActEmpregadorPrincipal.class);
        startActivity(volta);
        if (mudou == true){
            Toast.makeText(this, "Alterações feitas com sucesso.", Toast.LENGTH_SHORT).show();
        }
    }

}