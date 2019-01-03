package bsi.mpoo.traineeufrpe.gui.estagiario.acesso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.main.ActHome;

public class MudarSenhaEstagiario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha_estagiario);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActHome.class));
        finish();
    }
}
