package bsi.mpoo.traineeufrpe.gui.main.gui.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.gui.empregador.gui.CadastroEmpregador.CadastroEmpregador;
import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Cadastro.CadastroEstagiario;
import bsi.mpoo.traineeufrpe.gui.main.gui.Contato.Contato;

public class MainActivity extends AppCompatActivity {
    Button butest, butemp;
    TextView contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butemp = (Button)findViewById(R.id.butEmpregador);
        butest = (Button)findViewById(R.id.butEstagiario);

        butest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreEst = new Intent(getBaseContext(), CadastroEstagiario.class);
                startActivity(abreEst);
            }
        });

        butemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaEmpregador = new Intent(getBaseContext(), CadastroEmpregador.class);
                startActivity(abreTelaEmpregador);
            }
        });

        contact = (TextView)findViewById(R.id.contato2);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaContato = new Intent(getBaseContext(), Contato.class);
                startActivity(abreTelaContato);
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
