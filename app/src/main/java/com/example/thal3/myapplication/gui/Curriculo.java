package com.example.thal3.myapplication.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thal3.myapplication.R;
import com.example.thal3.myapplication.gui.TelaPrincipalEstagiario;

public class Curriculo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editCurso, editInstituicao;
    Spinner Segmento;
    Button Segue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculo);

        editCurso = (EditText)findViewById(R.id.editCurso);
        editInstituicao = (EditText)findViewById(R.id.editInstituicao);
        Segmento = (Spinner)findViewById(R.id.Segmento);

        Segue = (Button) findViewById(R.id.segue);
        Segue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curso = editCurso.getText().toString();
                String instituicao = editInstituicao.getText().toString();

                if (Cadastro(curso, instituicao)){
                    Toast.makeText(getApplicationContext(), "Ta vazio", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent abreTelaPrincipalEstagiario = new Intent(getBaseContext(), TelaPrincipalEstagiario.class);
                startActivity(abreTelaPrincipalEstagiario);
            }
        });
        Segmento = (Spinner) findViewById(R.id.Segmento);
        Segmento.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Segmento.setAdapter(adapter);
    }
    public boolean Cadastro(String curso, String instituicao){
        boolean resultado = false;
        resultado = this.TaVazio(curso);
        resultado = this.TaVazio(instituicao);
        return resultado;
    }

    public boolean TaVazio(String campo){
        boolean resultado = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        return resultado;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

