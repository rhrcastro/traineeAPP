package bsi.mpoo.traineeufrpe.gui.empregador.gui.EditarEmpresa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.Persistencia.EmpregadorDAO.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.Persistencia.VagaDAO.VagaDAO;
import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.Vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_antiga.HomeEmpregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.TelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.Database.Database;

public class EditarEmpresa extends AppCompatActivity {
    private static final String TAG = "editar";
    private Button save, del;
    EditText nome, bolsa, area, requisitos, obs;


    String selectedname, selectedbag, selectedreq, selectedobs;
    private int selectedid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final VagaDAO vagaDAO = new VagaDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);
        save = (Button)findViewById(R.id.save);
        del = (Button)findViewById(R.id.del);
        nome = (EditText)findViewById(R.id.nome);
        bolsa = (EditText)findViewById(R.id.bolsa);
        area = (EditText) findViewById(R.id.area);
        requisitos = (EditText)findViewById(R.id.requisitos);
        obs = (EditText)findViewById(R.id.obs);

        Intent received = getIntent();

        selectedid = received.getIntExtra("id", -1);
        selectedname = received.getStringExtra("nome");
        selectedbag = received.getStringExtra("bolsa");
        selectedreq = received.getStringExtra("requisitos");
        selectedobs = received.getStringExtra("obs");

        nome.setText(selectedname);
        bolsa.setText(selectedbag);
        requisitos.setText(selectedreq);
        obs.setText(selectedobs);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = nome.getText().toString();
                if(itemName != ""){
                    Vaga vaga = vagaDAO.getId(selectedid, getBaseContext());
                    vaga.setNome(itemName);
                    vagaDAO.mudarNomeVaga(vaga);
                    Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent voltar = new Intent(getBaseContext(), TelaEmpregadorPrincipal.class);
                    startActivity(voltar);
                }else{
                    Toast.makeText(getBaseContext(), "O nome não pode ser vazio", Toast.LENGTH_SHORT).show();
                }
                String itemBolsa = bolsa.getText().toString();
                if(itemBolsa != ""){
                    Vaga vaga = vagaDAO.getId(selectedid, getBaseContext());
                    vaga.setBolsa(itemBolsa);
                    vagaDAO.mudarBolsaVaga(vaga);
                    Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent voltar = new Intent(getBaseContext(), TelaEmpregadorPrincipal.class);
                    startActivity(voltar);
                }else{
                    Toast.makeText(getBaseContext(), "O nome não pode ser vazio", Toast.LENGTH_SHORT).show();
                }
                String itemReq = requisitos.getText().toString();
                if(itemReq != ""){
                    Vaga vaga = vagaDAO.getId(selectedid, getBaseContext());
                    vaga.setRequisito(itemReq);
                    vagaDAO.mudarRequisitoVaga(vaga);
                    Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent voltar = new Intent(getBaseContext(), TelaEmpregadorPrincipal.class);
                    startActivity(voltar);
                }else{
                    Toast.makeText(getBaseContext(), "O nome não pode ser vazio", Toast.LENGTH_SHORT).show();
                }
                String itemObs = obs.getText().toString();
                if(itemObs != ""){
                    Vaga vaga = vagaDAO.getId(selectedid, getBaseContext());
                    vaga.setObs(itemName);
                    vagaDAO.mudarObsVaga(vaga);
                    Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent voltar = new Intent(getBaseContext(), TelaEmpregadorPrincipal.class);
                    startActivity(voltar);
                }else{
                    Toast.makeText(getBaseContext(), "O nome não pode ser vazio", Toast.LENGTH_SHORT).show();
                }

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vagaDAO.deletarVaga(selectedid, selectedname);
                nome.setText("");
                Toast.makeText(getBaseContext(), "Removido.", Toast.LENGTH_SHORT).show();
                Intent voltar = new Intent(getBaseContext(), HomeEmpregador.class);
                startActivity(voltar);
            }
        });
    }
}
