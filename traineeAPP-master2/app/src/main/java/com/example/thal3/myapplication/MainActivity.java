package com.example.thal3.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button butest, butemp;
    TextView contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butemp = (Button)findViewById(R.id.butemp);
        butest = (Button)findViewById(R.id.butest);

        butest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaEstagiario = new Intent(getBaseContext(), LoginEstagiario.class);
                startActivity(abreTelaEstagiario);
            }
        });

        butemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaEmpregador = new Intent(getBaseContext(), LoginEmpregador.class);
                startActivity(abreTelaEmpregador);
            }
        });

        contact = (TextView)findViewById(R.id.contact);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaContato = new Intent(getBaseContext(), Contato.class);
                startActivity(abreTelaContato);
            }
        });




    }
}
