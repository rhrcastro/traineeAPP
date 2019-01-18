package bsi.mpoo.traineeufrpe.gui.empregador.acesso.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.empregador.home.ActEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.Criptografia;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;

public class FragmentCadastroEmpregador extends Fragment {
    Button cadastrarEmpregador;
    private ValidacaoGUI validacaoGUI = new ValidacaoGUI();
    private EditText editNomeCadastroEmpregador;
    private EditText editEmailCadastroEmpregador;
    private EditText editCNPJ;
    private EditText editSenhaCadastroEmpregador;
    private EditText editConfirmaSenhaEmpregador;
    private EditText editCidadeEmpregador;
    private Context context;
    private Criptografia criptografia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.fragment_cadastro_empregador, container, false);
        Constroi(v);

        this.cadastrarEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroEmpregador();
            }
        });
        return v;
    }

    private void Constroi(View v) {
        context = v.getContext();
        this.editNomeCadastroEmpregador = v.findViewById(R.id.editNomeCadastroEmpregador);
        this.editEmailCadastroEmpregador = v.findViewById(R.id.editEmailCadastroEmpregador);
        this.editCNPJ = v.findViewById(R.id.editCNPJ);
        this.editSenhaCadastroEmpregador = v.findViewById(R.id.editSenhaCadastroEmpregador);
        this.editConfirmaSenhaEmpregador = v.findViewById(R.id.editConfirmaSenhaEmpregador);
        this.editCidadeEmpregador = v.findViewById(R.id.editCidadeEmpregador);
        this.cadastrarEmpregador = v.findViewById(R.id.cadastrarEmpregador);
    }

    private boolean verificarCampos() {
        String nome = editNomeCadastroEmpregador.getText().toString().trim();
        String email = editEmailCadastroEmpregador.getText().toString().trim();
        String senha = editSenhaCadastroEmpregador.getText().toString().trim();
        String repetirSenha = editConfirmaSenhaEmpregador.getText().toString().trim();
        String cnpj = editCNPJ.getText().toString().trim();
        String cidade = editCidadeEmpregador.getText().toString().trim();
        if (validacaoGUI.isCampoVazio(nome)){
            this.editNomeCadastroEmpregador.setError("Campo vazio");
            return false;
        } else if (validacaoGUI.isEmailInvalido(email)) {
            this.editEmailCadastroEmpregador.setError("Formato de email inválido");
            return false;
        } else if (validacaoGUI.isCampoVazio(senha)){
            this.editSenhaCadastroEmpregador.setError("Campo vazio");
            return false;
        } else if (validacaoGUI.isCampoVazio(repetirSenha)) {
            this.editConfirmaSenhaEmpregador.setError("Campo vazio");
            return false;
        } else if (validacaoGUI.isCampoVazio(cnpj)){
            this.editCNPJ.setError("Campo vazio");
            return false;
        } else if (validacaoGUI.isCampoVazio(cidade)){
            this.editCNPJ.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }
    public void cadastroEmpregador() {
        if (!this.verificarCampos()) {
            return;
        }
        EmpregadorServices empregadorServices = new EmpregadorServices(getContext());
        if (empregadorServices.cadastrarEmpregador(this.criarEmpregador())) {
            Toast.makeText(getContext(), "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
            Intent abreTelaEmpregador = new Intent(getActivity(), ActEmpregadorPrincipal.class);
            startActivity(abreTelaEmpregador);
            finishActivity();
        } else {
            Toast.makeText(getContext(),"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
        }
    }
    private Empregador criarEmpregador() {
        String nome = editNomeCadastroEmpregador.getText().toString().trim();
        String email = editEmailCadastroEmpregador.getText().toString().trim();
        String senha = editSenhaCadastroEmpregador.getText().toString().trim();
        String cnpj = editCNPJ.getText().toString().trim();
        String cidade = editCidadeEmpregador.getText().toString().trim();
        Empregador empregador = new Empregador();
        empregador.setNome(nome);
        empregador.setEmail(email);
        String senhaCodificada = criptografia.criptografar(senha);
        empregador.setSenha(senhaCodificada);
        empregador.setCnpj(cnpj);
        empregador.setCidade(cidade);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_empregador);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        byte[] bitmapdata = blob.toByteArray();
        empregador.setFoto(bitmapdata);
        return empregador;
    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }

}
