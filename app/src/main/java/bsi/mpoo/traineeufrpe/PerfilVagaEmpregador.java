package bsi.mpoo.traineeufrpe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class PerfilVagaEmpregador extends AppCompatActivity {

    TextView NomeVagaPerfilEmp, BolsaPerfilVagaEmp, AreaPerfilVagaEmp, ObsPerfilVagaEmp, ReqPerfilVagaEmp, IdPerfilVagaEmp;
    FloatingActionButton floatingActionEdit, floatingActionDelete;
    private VagaServices vagaServices = new VagaServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_empregador);
        NomeVagaPerfilEmp = findViewById(R.id.NomeVagaPerfilEmp);
        BolsaPerfilVagaEmp = findViewById(R.id.BolsaPerfilVagaEmp);
        AreaPerfilVagaEmp = findViewById(R.id.AreaPerfilVagaEmp);
        ObsPerfilVagaEmp = findViewById(R.id.ObsPerfilVagaEmp);
        ReqPerfilVagaEmp = findViewById(R.id.ReqPerfilVagaEmp);
        IdPerfilVagaEmp = findViewById(R.id.IdPerfilVagaEmp);

        floatingActionEdit = findViewById(R.id.floatingActionEdit);
        floatingActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreEdit = new Intent(PerfilVagaEmpregador.this, EditarVaga.class);
                startActivity(abreEdit);
            }
        });

        floatingActionDelete = findViewById(R.id.floatingActionDelete);
        floatingActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del();
                Intent volta = new Intent(PerfilVagaEmpregador.this, ActEmpregadorPrincipal.class);
                startActivity(volta);
            }
        });

        pupular();



    }

    private void pupular() {
        String nome = SessaoEmpregador.instance.getVaga().getNome();
        String Bolsa = SessaoEmpregador.instance.getVaga().getBolsa();
        String area = SessaoEmpregador.instance.getVaga().getArea();
        String obs = SessaoEmpregador.instance.getVaga().getObs();
        String req = SessaoEmpregador.instance.getVaga().getRequisito();
        long id = SessaoEmpregador.instance.getVaga().getId();

        NomeVagaPerfilEmp.setText(nome);
        BolsaPerfilVagaEmp.setText(Bolsa);
        AreaPerfilVagaEmp.setText(area);
        ObsPerfilVagaEmp.setText(obs);
        ReqPerfilVagaEmp.setText(req);
        IdPerfilVagaEmp.setText(id + "");

    }

    private void del(){
        long id = SessaoEmpregador.instance.getVaga().getId();
        vagaServices.DelVaga(SessaoEmpregador.instance.getVaga().getId());
        Toast.makeText(this, "Deletado com sucesso.", Toast.LENGTH_SHORT).show();
    }

}
