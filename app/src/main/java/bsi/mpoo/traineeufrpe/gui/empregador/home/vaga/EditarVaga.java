package bsi.mpoo.traineeufrpe.gui.empregador.home.vaga;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class EditarVaga extends AppCompatActivity {

    VagaServices vagaServices = new VagaServices(this);
    private NestedScrollView nestedScrollView;
    private EditText editTitulo;
    private EditText editRequisitos;
    private EditText editObservacoes;
    private RadioGroup radioGroup;
    private TextView txtAreaVaga;
    private TextView txtR$;
    private TextView txtValorBolsa;
    private TextView txtACombinar;
    Toolbar toolbar;
    SeekBar skbAjusteBolsa;
    CardView menu_areas;
    Button btnDivulgar;
    private String titulo;
    private String requisitos;
    private String observacoes;
    private String area;
    private String turno;
    private String valor = "A combinar";
    private Vaga vaga;

    public EditarVaga(){
        vaga = SessaoEmpregador.instance.getVaga();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_vaga);
        editTitulo = (EditText) findViewById(R.id.editTituloVaga);
        editRequisitos = (EditText) findViewById(R.id.editRequisitos);
        editObservacoes = (EditText) findViewById(R.id.editObservacoes);
        txtAreaVaga = (TextView) findViewById(R.id.txt_area_vaga);
        menu_areas = (CardView) findViewById(R.id.cardView_2);
        menu_areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        txtValorBolsa = (TextView) findViewById(R.id.txtValorBolsa);
        txtACombinar = (TextView) findViewById(R.id.txtCombinar);
        txtR$ = (TextView) findViewById(R.id.reais);
        skbAjusteBolsa = (SeekBar) findViewById(R.id.seekbar_ajusta_bolsa);
        skbAjusteBolsa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= 14) {
                    valor = String.valueOf(progress*25);
                    txtValorBolsa.setText(valor);
                    if (progress == 80) {
                        txtValorBolsa.setText("2000+");
                    }
                    txtACombinar.setVisibility(View.INVISIBLE);
                    txtR$.setVisibility(View.VISIBLE);
                    txtValorBolsa.setVisibility(View.VISIBLE);
                } else {
                    valor = "A combinar";
                    txtValorBolsa.setText(String.valueOf(progress*25));
                    txtACombinar.setVisibility(View.VISIBLE);
                    txtR$.setVisibility(View.INVISIBLE);
                    txtValorBolsa.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        preencherCampos();
        btnDivulgar = (Button) findViewById(R.id.btnQueroCandidatar);
        btnDivulgar.setText("SALVAR ALTERAÇÕES");
        btnDivulgar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos()){
                    capturarTextos();
                    salvar(alterarVaga());
                    volta();
                }
            }
        });
    }

    private void preencherCampos() {
        editTitulo.setText(vaga.getNome());
        editRequisitos.setText(vaga.getRequisito());
        editObservacoes.setText(vaga.getObs());
        txtAreaVaga.setText(vaga.getArea());
        String valorBolsa = vaga.getBolsa();
        valorBolsa = valorBolsa.replaceAll("\\D+","");
        if (!valorBolsa.equals("")) {
            skbAjusteBolsa.setProgress(Integer.valueOf(valorBolsa)/25);
        } else {
            skbAjusteBolsa.setProgress(0);
        }
    }

    private void capturarTextos(){
        titulo = editTitulo.getText().toString().trim();
        area = txtAreaVaga.getText().toString();
        requisitos = editRequisitos.getText().toString().trim();
        observacoes = editObservacoes.getText().toString().trim();
        if (txtValorBolsa.getText().toString().equals("A combinar")){
            valor = txtValorBolsa.getText().toString();
        } else {
            valor = "R$ " + txtValorBolsa.getText().toString();
        }
    }

    public boolean verificarCampos() {
        EditText[] campos = {editTitulo, editRequisitos, editObservacoes};
        for (EditText editText : campos) {
            String campoAtual = editText.getText().toString().trim();
            if (ValidacaoGUI.isCampoVazio(campoAtual)) {
                editText.setError("Este campo não pode ser vazio");
                return false;
            }
        } return true;
    }

    private Vaga alterarVaga() {
        vaga.setNome(titulo);
        vaga.setArea(area);
        vaga.setBolsa(valor);
        vaga.setObs(observacoes);
        vaga.setRequisito(requisitos);
        return vaga;
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_areas, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                txtAreaVaga.setText(item.getTitle());
                return true;
            }
        });
        popup.show();
    }

    public void volta(){
        Intent volta = new Intent(EditarVaga.this, PerfilVagaEmpregador.class);
        startActivity(volta);
        finish();
    }

    @Override
    public void onBackPressed() {
        exibirConfirmacaoSair();
    }

    public void exibirConfirmacaoSair() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setTitle("Deseja voltar?");
        msgBox.setMessage("As alterações não serão salvas");
        setBtnPositivoSair(msgBox);
        setBtnNegativoSair(msgBox);
        msgBox.show();
    }
    public void setBtnPositivoSair(AlertDialog.Builder msgBox){
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                volta();
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

    private void salvar(Vaga vaga) {
        vagaServices.mudarNomeVaga(vaga, vaga.getNome());
        vagaServices.mudarBolsaVaga(vaga, vaga.getBolsa());
        vagaServices.mudarRequisitoVaga(vaga, vaga.getRequisito());
        vagaServices.mudarObsVaga(vaga, vaga.getObs());
        vagaServices.mudarAreaVaga(vaga, vaga.getArea());
        SessaoEmpregador.instance.setVaga(vaga);
    }
}