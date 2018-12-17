package bsi.mpoo.traineeufrpe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.gui.empregador.home.vaga.PerfilVagaEmpregador;
import bsi.mpoo.traineeufrpe.gui.estagiario.home.PerfilVagaEstagiario;
import bsi.mpoo.traineeufrpe.gui.extra.AdapterNovasVagas;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class TestandoList extends AppCompatActivity {

    VagaServices vagaServices = new VagaServices(getBaseContext());
    EmpregadorServices empregadorServices = new EmpregadorServices(getBaseContext());
    ListView listaVagas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testando_list);
        listaVagas = findViewById(R.id.listadeTeste);
        populate();
    }


    private void populate() {
        ArrayList<Vaga> vagas = new ArrayList<Vaga>();
        vagaServices = new VagaServices(this);
        vagas = vagaServices.getListaVagas(this);
        final AdapterNovasVagas adapter = new AdapterNovasVagas(this, vagas);
        listaVagas.setAdapter(adapter);
        onClick();
    }

    private void onClick() {
        listaVagas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vaga vaga = ((Vaga) parent.getItemAtPosition(position));
                Intent it = new Intent(TestandoList.this, PerfilVagaEstagiario.class);
                PerfilVagaEstagiario.vaga = vaga;
                startActivity(it);
            }
        });
    }
}
