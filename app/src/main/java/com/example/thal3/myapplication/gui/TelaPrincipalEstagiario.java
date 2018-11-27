package com.example.thal3.myapplication.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thal3.myapplication.R;

public class TelaPrincipalEstagiario extends AppCompatActivity {

    Toolbar botnavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal_estagiario);

        botnavi = (Toolbar)findViewById(R.id.botnavi);
        setSupportActionBar(botnavi);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navi_menu, menu);
        return true;
    }
}
