package bsi.mpoo.traineeufrpe.gui.estagiario.gui.Home.tela_antiga;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.thal3.trainee.R;

import bsi.mpoo.traineeufrpe.gui.estagiario.gui.Login.LoginEstagiario;
import bsi.mpoo.traineeufrpe.infra.Sessao.Sessao;

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
    public void onBackPressed() {
        exibirConfirmacaoSair();
    }
    public void exibirConfirmacaoSair() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setTitle("Sair");
        msgBox.setMessage("Deseja mesmo sair?");
        setBtnPositivoSair(msgBox);
        setBtnNegativoSair(msgBox);
        msgBox.show();
    }
    public void setBtnPositivoSair(AlertDialog.Builder msgBox) {
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Sessao.getInstance().reset();
                exibirTelaLogin();
                finish();
            }
        });
    }
    public void setBtnNegativoSair(AlertDialog.Builder msgBox){
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
    public void exibirTelaLogin(){
        Intent intent = new Intent(TelaPrincipalEstagiario.this, LoginEstagiario.class);
        startActivity(intent);
    }
}
