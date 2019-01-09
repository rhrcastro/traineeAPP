package bsi.mpoo.traineeufrpe.gui.empregador.home.vaga;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.ControladorVaga;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class PerfilVagaEmpregador extends AppCompatActivity {

    TextView NomeVagaPerfilEmp, CampoEmpresa, BolsaPerfilVagaEmp, AreaPerfilVagaEmp, ObsPerfilVagaEmp, ReqPerfilVagaEmp;
    ImageView imgEmpresa;
    Button ActionEdit, ActionDelete;
    private VagaServices vagaServices = new VagaServices(this);
    private InscricaoServices inscricaoServices = new InscricaoServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_empregador);
        NomeVagaPerfilEmp = findViewById(R.id.txtTituloVaga);
        CampoEmpresa = findViewById(R.id.campo_empresa);
        imgEmpresa = findViewById(R.id.imgEmpresa);
        BolsaPerfilVagaEmp = findViewById(R.id.txtValorBolsa);
        AreaPerfilVagaEmp = findViewById(R.id.txt_area_vaga);
        ObsPerfilVagaEmp = findViewById(R.id.txtObservacoes);
        ReqPerfilVagaEmp = findViewById(R.id.txtRequisitos);


        ActionEdit = findViewById(R.id.btnEditar);
        ActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreEdit = new Intent(PerfilVagaEmpregador.this, EditarVaga.class);
                startActivity(abreEdit);
                finish();
            }
        });

        ActionDelete = findViewById(R.id.btnExcluir);
        ActionDelete.setOnClickListener(new View.OnClickListener() {
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
        String bolsa = SessaoEmpregador.instance.getVaga().getBolsa();
        String area = SessaoEmpregador.instance.getVaga().getArea();
        String obs = SessaoEmpregador.instance.getVaga().getObs();
        String req = SessaoEmpregador.instance.getVaga().getRequisito();
        String nome_empregador = SessaoEmpregador.instance.getEmpregador().getNome();
        byte[] imgBits = SessaoEmpregador.instance.getEmpregador().getFoto();
        Bitmap imagem = BitmapFactory.decodeByteArray(imgBits, 0, imgBits.length);

        NomeVagaPerfilEmp.setText(nome);
        CampoEmpresa.setText(nome_empregador);
        BolsaPerfilVagaEmp.setText(bolsa);
        AreaPerfilVagaEmp.setText(area);
        ObsPerfilVagaEmp.setText(obs);
        ReqPerfilVagaEmp.setText(req);
        imgEmpresa.setImageBitmap(imagem);


    }
    private void del(){
        ArrayList<ControladorVaga> inscritos = inscricaoServices.
                getInscritosByVaga(SessaoEmpregador.instance.getVaga().getId(), this);
        for (ControladorVaga inscrito : inscritos){
            inscricaoServices.delInscricao(inscrito.getVaga().getId(), inscrito.getPessoa().getId());
        }
        vagaServices.DelVaga(SessaoEmpregador.instance.getVaga().getId());
        Toast.makeText(this, "Deletado com sucesso.", Toast.LENGTH_SHORT).show();
    }
}
