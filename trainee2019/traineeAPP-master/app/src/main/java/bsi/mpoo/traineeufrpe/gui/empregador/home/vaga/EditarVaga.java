package bsi.mpoo.traineeufrpe.gui.empregador.home.vaga;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
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
    private TextView txtAreaVaga;
    private TextView txtR$;
    private TextView txtValorBolsa;
    private TextView txtACombinar;
    private TextView txtHoraInicio;
    private TextView txtHoraFim;
    private TextView txtAS;
    CheckBox checkHorario;
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
    private String horario;
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
        txtAS = findViewById(R.id.txtAs);
        txtHoraInicio = findViewById(R.id.txtHoraInicio);
        txtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditarVaga.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        txtHoraInicio.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
        txtHoraFim = findViewById(R.id.txtHoraFIm);
        txtHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditarVaga.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        txtHoraFim.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
        checkHorario = findViewById(R.id.checkBox);
        checkHorario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mudarCorHorario("#999999");
                    horario = "Não especificado";
                } else {
                    mudarCorHorario("#095f8a");
                    horario = txtHoraInicio.getText().toString() + " às " + txtHoraFim.getText().toString();
                }
            }
        });
        btnDivulgar = (Button) findViewById(R.id.btnQueroCandidatar);
        btnDivulgar.setText("SALVAR ALTERAÇÕES");
        btnDivulgar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos() && validarHorario()){
                    capturarTextos();
                    salvar(alterarVaga());
                    volta();
                }
            }
        });
        preencherCampos();
    }

    private void mudarCorHorario(String color) {
        txtHoraInicio.setTextColor(Color.parseColor(color));
        txtHoraFim.setTextColor(Color.parseColor(color));
        txtAS.setTextColor(Color.parseColor(color));
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
        if (vaga.getHorario().equals("Não especificado")){
            checkHorario.setChecked(true);
        } else {
            turno = vaga.getHorario();
            String[] horarios = turno.split(" às ");
            txtHoraInicio.setText(horarios[0]);
            txtHoraFim.setText(horarios[1]);
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
        if (checkHorario.isChecked()){
            horario = "não especificado";
        } else {
            horario = txtHoraInicio.getText().toString() + " às " + txtHoraFim.getText().toString();
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
        vaga.setHorario(horario);
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
        vagaServices.mudarHorarioVaga(vaga, vaga.getHorario());
        SessaoEmpregador.instance.setVaga(vaga);
    }

    private boolean validarHorario(){
        String horaInicio = txtHoraInicio.getText().toString();
        String horaFim = txtHoraFim.getText().toString();
        if (checkHorario.isChecked()){
            return true;
        } else if (horaInicio.equals("--:--") || (horaFim.equals("--:--"))){
            Toast.makeText(this, "Por favor, verifique o horário", Toast.LENGTH_SHORT).show();
            return false;
        } horario = horaInicio + " + " + horaFim;
        return true;
    }
}