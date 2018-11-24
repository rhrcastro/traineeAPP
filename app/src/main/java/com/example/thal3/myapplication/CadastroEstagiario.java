package com.example.thal3.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CadastroEstagiario extends AppCompatActivity {

    Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estagiario);

        cadastrar = (Button)findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaCadastroCurriculo = new Intent(getBaseContext(), Curriculo.class);
                startActivity(abreTelaCadastroCurriculo);
            }
        });

    }
}
