package com.example.thal3.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.thal3.myapplication.persistencia.BancoController;
import com.example.thal3.myapplication.persistencia.CriaBanco;

public class Consulta extends AppCompatActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        /*

        BancoController crud = new BancoController(getBaseContext());
        Cursor cursor = crud.ConsultarDado();

        String[] nomeCampos = new String[] {CriaBanco.Id};
        int[] idViews = new int[] {R.id.editEmail1, R.id.editSenha1};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.activity_consulta,cursor,nomeCampos,idViews, 0);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adaptador);
    }*/

    }
}
