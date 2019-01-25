package bsi.mpoo.traineeufrpe.gui.estagiario.acesso.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.gui.estagiario.curriculo.ActCadastroEstagiario;
import bsi.mpoo.traineeufrpe.gui.estagiario.curriculo.CadastrarCurriculo2;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class FragmentCadastroAcademico extends Fragment implements AdapterView.OnItemSelectedListener{
    private EditText edtCurso;
    private EditText edtInstituicao;
    private EditText Hyperlink;
    private Spinner edtSegmento;
    private final ValidacaoGUI validacaoGUI = new ValidacaoGUI();
    private Button cadastrar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.fragment_cadastro_academico, container, false);
        this.edtCurso = v.findViewById(R.id.cursoCadastro);
        this.edtInstituicao = v.findViewById(R.id.instituicaoCadastro);
        edtSegmento = v.findViewById(R.id.Segmento);
        Hyperlink = v.findViewById(R.id.linkCadastro);
        edtSegmento.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtSegmento.setAdapter(adapter);
        cadastrar = v.findViewById(R.id.botCadastroCurriculo);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarCurriculo();
            }
        });

        return v;
    }
    private boolean verificarCampos() {
        String curso = edtCurso.getText().toString().trim();
        String instituicao = edtInstituicao.getText().toString().trim();
        String area = edtSegmento.getSelectedItem().toString();
        String link = Hyperlink.getText().toString().trim();
        if (ValidacaoGUI.isCampoVazio(curso)) {
            this.edtCurso.setError("O campo curso não pode ficar vazio");
            return false;
        }
        if (ValidacaoGUI.isCampoVazio(instituicao)) {
            this.edtInstituicao.setError("O campo instituição não pode ficar vazio");
            return false;
        }
        if (validacaoGUI.isAreaValida(area)) {
            Toast.makeText(getContext(), "Escolha uma área", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!link.equals("") && !Patterns.WEB_URL.matcher(link).matches()){
                Hyperlink.setError("Insira um link válido");
                return false;
            } if (!(link.startsWith("http://") || link.startsWith("https://"))){
                Hyperlink.setText("http://" + link);
            }
        return true;
    }

    private void cadastrarCurriculo() {
        if (!this.verificarCampos()) {
            return;
        }
        Curriculo curriculo;
        LoginServices loginServices = new LoginServices(getContext());
        curriculo = criarCurriculo();
        if (!Hyperlink.getText().toString().trim().equals("")){
            curriculo.setExperiencia("");
            curriculo.setConhecimentos_especificos("");
            curriculo.setConhcimentos_basicos("");
            curriculo.setObjetivo("");
            curriculo.setDisciplinas("");
            curriculo.setRelacionamento("");
            Curriculo curriculo1 = loginServices.cadastrarCurriculoNoBanco(curriculo);
            SessaoEstagiario.instance.setCurriculo(curriculo1);
            Intent abreTelaCadastroEstagiario = new Intent(getActivity(), ActCadastroEstagiario.class);
            startActivity(abreTelaCadastroEstagiario);
        }else{
            if (curriculo != null) {
                SessaoEstagiario.instance.setCurriculo(curriculo);
                Intent abreTelaCadastroEstagiario = new Intent(getActivity(), CadastrarCurriculo2.class);
                startActivity(abreTelaCadastroEstagiario);
                finishActivity();
            }else{
                Toast.makeText(getContext(), "Erro de inserção do curriculo.", Toast.LENGTH_SHORT).show();
            }

        }


    }
    private Curriculo criarCurriculo(){
        String curso = edtCurso.getText().toString().trim();
        String instituicao = edtInstituicao.getText().toString().trim();
        String areaAtuacao = edtSegmento.getSelectedItem().toString();
        String page = Hyperlink.getText().toString().trim();
        Curriculo curriculo = new Curriculo();
        curriculo.setCurso(curso);
        curriculo.setAreaAtuacao(areaAtuacao);
        curriculo.setInstituicao(instituicao);
        curriculo.setLink(page);
        return curriculo;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}
