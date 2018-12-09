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
import bsi.mpoo.traineeufrpe.dominio.Empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_antiga.HomeEmpregador;
import bsi.mpoo.traineeufrpe.infra.Database.Database;

public class EditarEmpresa extends AppCompatActivity {
    private static final String TAG = "editar";
    private Button save, del;
    private EditText nome;

    private Database mDatabase;

    private String selectedname;
    private int selectedid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EmpregadorDAO empregadorDAO = new EmpregadorDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);
        save = (Button)findViewById(R.id.save);
        del = (Button)findViewById(R.id.del);
        nome = (EditText)findViewById(R.id.nome);
        mDatabase = new Database(this);

        Intent received = getIntent();

        selectedid = received.getIntExtra("id", -1);
        selectedname = received.getStringExtra("nome");

        nome.setText(selectedname);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = nome.getText().toString();
                if(item != ""){
                    Empregador empregador = empregadorDAO.getId(selectedid, getBaseContext());
                    empregador.setNome(item);
                    empregadorDAO.mudarNomeEmpregador(empregador);
                    Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent voltar = new Intent(getBaseContext(), HomeEmpregador.class);
                    startActivity(voltar);
                }else{
                    Toast.makeText(getBaseContext(), "O nome não pode ser vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empregadorDAO.deletarEmpregador(selectedid, selectedname);
                nome.setText("");
                Toast.makeText(getBaseContext(), "Removido.", Toast.LENGTH_SHORT).show();
                Intent voltar = new Intent(getBaseContext(), HomeEmpregador.class);
                startActivity(voltar);
            }
        });
    }
}
