package com.example.thal3.myapplication.gui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thal3.myapplication.R;

public class LoginEstagiario extends AppCompatActivity {

    FloatingActionButton cadastro;
    Button butlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_estagiario);

        cadastro = (FloatingActionButton) findViewById(R.id.cadastro);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaCadastroEstagiario = new Intent(getBaseContext(), CadastroEstagiario.class);
                startActivity(abreTelaCadastroEstagiario);
            }
        });

        butlogin = (Button)findViewById(R.id.butlogin);
        butlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaPrincipalEstagiario = new Intent(getBaseContext(), TelaPrincipalEstagiario.class);
                startActivity(abreTelaPrincipalEstagiario);
            }
        });
    }

}
