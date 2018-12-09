package com.trainee2.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trainee2.GUI.empregador.TelaCadastroEmpregador;
import com.trainee2.GUI.estagiario.TelaCadastroEstagiario;
import com.trainee2.R;

public class TelaPrincipal extends AppCompatActivity {

    Button butEstagiario, butEmpregador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        butEstagiario = (Button)findViewById(R.id.butEstagiario);
        butEmpregador = (Button)findViewById(R.id.butEmpregador);

        butEstagiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaEstagiario = new Intent(TelaPrincipal.this, TelaCadastroEstagiario.class);
                startActivity(abreTelaEstagiario);
            }
        });

        butEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaEmpregador = new Intent(TelaPrincipal.this, TelaCadastroEmpregador.class);
                startActivity(abreTelaEmpregador);
            }
        });

        

    }
}
