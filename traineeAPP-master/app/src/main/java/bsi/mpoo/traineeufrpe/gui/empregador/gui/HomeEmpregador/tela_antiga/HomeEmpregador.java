package bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_antiga;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.gui.empregador.gui.EditarEmpresa.EditarEmpresa;
import bsi.mpoo.traineeufrpe.gui.empregador.gui.LoginEmpregador.LoginEmpregador;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.EmpregadorServices;

public class HomeEmpregador extends AppCompatActivity {
    private final String TAG = "listData";
    private ListView listView;
    private EmpregadorServices empregadorServices = new EmpregadorServices(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empregador);
        listView = (ListView)findViewById(R.id.list);
        populate();
    }

    private void populate() {
        Log.d(TAG, "Populando");
        ArrayList<String> listD = new ArrayList<>();
        listD = empregadorServices.getListaNomeEmpresa();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listD);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nome = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "Voce clicou no " + nome);


                int itemID = empregadorServices.getIdEmpresa(nome);

                if (itemID > -1){
                    Log.d(TAG, "Voce clicou no id" + itemID);
                    Intent editarEmpresa = new Intent(getBaseContext(), EditarEmpresa.class);
                    editarEmpresa.putExtra("id", itemID);
                    editarEmpresa.putExtra("nome", nome);
                    startActivity(editarEmpresa);
                }else{
                    Toast.makeText(getBaseContext(), "Nao temos id associado.", Toast.LENGTH_SHORT).show();

                }
            }
        });
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
                SessaoEmpregador.getInstance().resetEmpregador();
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
        Intent intent = new Intent(HomeEmpregador.this, LoginEmpregador.class);
        startActivity(intent);
    }
}
