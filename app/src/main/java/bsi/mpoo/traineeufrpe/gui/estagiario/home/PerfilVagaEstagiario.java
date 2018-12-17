package bsi.mpoo.traineeufrpe.gui.estagiario.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;

public class PerfilVagaEstagiario extends AppCompatActivity {

    TextView nomeVaga, BolsaPerfilEst, AreaPerfilEst, ObsPerfilEst, reqPerfilEst;
    Button queroCandidatar;
    public static Vaga vaga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga_estagiario);
        nomeVaga = findViewById(R.id.NomeVagaPerfilEst);
        BolsaPerfilEst = findViewById(R.id.BolsaPerfilVagaEst);
        AreaPerfilEst = findViewById(R.id.AreaPerfilVagaEst);
        ObsPerfilEst = findViewById(R.id.ObsPerfilVagaEst);
        reqPerfilEst = findViewById(R.id.ReqPerfilVagaEst);
        queroCandidatar = findViewById(R.id.quero_candidatar);
        popular();
        queroCandidatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Disponível em breve.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void popular(){
        String nome = vaga.getNome();
        String bolsa = vaga.getBolsa();
        String area = vaga.getArea();
        String obs = vaga.getObs();
        String req = vaga.getRequisito();

        nomeVaga.setText(nome);
        BolsaPerfilEst.setText(bolsa);
        AreaPerfilEst.setText(area);
        ObsPerfilEst.setText(obs);
        reqPerfilEst.setText(req);
    }
}
