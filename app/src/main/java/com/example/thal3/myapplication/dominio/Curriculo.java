package com.example.thal3.myapplication.dominio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.thal3.myapplication.R;
import com.example.thal3.myapplication.TelaPrincipalEstagiario;

public class Curriculo extends AppCompatActivity {

    EditText editQualidades;
    Button cadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculo);

        editQualidades = (EditText)findViewById(R.id.editQualidades);
        cadastrar = (Button)findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaPrincipalEstagiario = new Intent(getBaseContext(), TelaPrincipalEstagiario.class);
                startActivity(abreTelaPrincipalEstagiario);
            }
        });
    }
}
