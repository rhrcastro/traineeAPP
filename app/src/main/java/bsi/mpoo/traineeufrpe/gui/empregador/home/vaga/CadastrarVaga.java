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

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.VagaServices;

public class CadastrarVaga extends AppCompatActivity {

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
    private TextView txtHoraInicio;
    private TextView txtHoraFim;
    private TextView txtAS;
    CheckBox checkHorario;
    SeekBar skbAjusteBolsa;
    CardView menu_areas;
    Button btnDivulgar;
    private String titulo;
    private String requisitos;
    private String observacoes;
    private String area;
    private String horario;
    private String valor = "A combinar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_vaga);
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
        btnDivulgar = (Button) findViewById(R.id.btnQueroCandidatar);
        btnDivulgar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarArea() && validarHorario()){
                    finderTextos();
                    cadastrar();
                }
            }
        });
        txtAS = findViewById(R.id.txtAs);
        txtHoraInicio = findViewById(R.id.txtHoraInicio);
        txtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CadastrarVaga.this, new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(CadastrarVaga.this, new TimePickerDialog.OnTimeSetListener() {
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
    }

    private void mudarCorHorario(String color) {
        txtHoraInicio.setTextColor(Color.parseColor(color));
        txtHoraFim.setTextColor(Color.parseColor(color));
        txtAS.setTextColor(Color.parseColor(color));
    }

    private void finderTextos(){
        editTitulo = (EditText) findViewById(R.id.editTituloVaga);
        editRequisitos = (EditText) findViewById(R.id.editRequisitos);
        editObservacoes = (EditText) findViewById(R.id.editObservacoes);
        capturarTextos();
    }

    private void capturarTextos(){
        titulo = editTitulo.getText().toString().trim();
        requisitos = editRequisitos.getText().toString().trim();
        observacoes = editObservacoes.getText().toString().trim();
    }

    private Vaga criarVaga() {
        Vaga vaga = new Vaga();
        vaga.setNome(titulo);
        vaga.setArea(area);
        if (valor.equals("A combinar")){
            vaga.setBolsa(valor);
        } else {
            vaga.setBolsa("R$ "+valor);
        }
        vaga.setObs(observacoes);
        vaga.setRequisito(requisitos);
        vaga.setEmpregador(SessaoEmpregador.instance.getEmpregador());
        vaga.setDataCriacao(System.currentTimeMillis());
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
                txtAreaVaga.setTextColor(getResources().getColor(R.color.colorTextDark));
                return true;
            }
        });
        popup.show();
    }

    private boolean validarArea(){
        String area = txtAreaVaga.getText().toString();
        if (area.equals("Selecione a área da vaga")){
            Toast.makeText(this, "Selecione a área da vaga", Toast.LENGTH_SHORT).show();
            txtAreaVaga.setTextColor(getResources().getColor(R.color.colorTextError));
            return false;
        } this.area = area;
        return true;
    }

    private void cadastrar() {
        vagaServices.cadastrarVaga(criarVaga(), this);
        Toast.makeText(this,"Vaga cadastrada", Toast.LENGTH_SHORT).show();
        volta();
    }

    public void volta(){
        Intent volta = new Intent(CadastrarVaga.this, ActEmpregadorPrincipal.class);
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
        msgBox.setMessage("A vaga não será cadastrada");
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


    private boolean validarHorario(){
        String horaInicio = txtHoraInicio.getText().toString();
        String horaFim = txtHoraFim.getText().toString();
        if (checkHorario.isChecked()){
            return true;
        } else if (horaInicio.equals("--:--") || (horaFim.equals("--:--"))){
            Toast.makeText(this, "Por favor, verifique o horário", Toast.LENGTH_SHORT).show();
            return false;
        } horario = horaInicio + " às " + horaFim;
        return true;
    }
}
