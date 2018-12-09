package com.trainee2.GUI.fragment.empregador.Cadastro_Login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainee2.GUI.estagiario.TelaCadastroCurriculo;
import com.trainee2.R;
import com.trainee2.dominio.Empregador;
import com.trainee2.infra.SessaoEmpregador;
import com.trainee2.infra.TraineeApp;
import com.trainee2.infra.Validacao;
import com.trainee2.negocio.EmpregadorServices;

public class FragmentCadastroEmpregador extends Fragment {
    private SessaoEmpregador sessaoEmpregador = SessaoEmpregador.getInstance();
    private TraineeApp traineeApp = new TraineeApp();
    private EmpregadorServices empregadorServices = new EmpregadorServices(getContext());
    private Validacao validacao = new Validacao();

    Button botCadastroEmpresa;
    EditText nomeCadastroEmpregador, emailCadastroEmpregador, cnpjCadastroEmpresa, senhaCadastroEmpresa,
            repeteSenhaCadastroEmpresa, cidadeCadastroEmpresa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_cadastro_empregador, container, false);

        nomeCadastroEmpregador = (EditText)v.findViewById(R.id.nomeCadastroEmpregador);
        emailCadastroEmpregador = (EditText)v.findViewById(R.id.emailCadastroEmpregador);
        cnpjCadastroEmpresa = (EditText)v.findViewById(R.id.cnpjCadastroEmpresa);
        senhaCadastroEmpresa = (EditText)v.findViewById(R.id.senhaCadastroEmpresa);
        repeteSenhaCadastroEmpresa = (EditText)v.findViewById(R.id.repeteSenhaCadastroEmpresa);
        cidadeCadastroEmpresa = (EditText)v.findViewById(R.id.cidadeCadastroEmpresa);


        botCadastroEmpresa= (Button)v.findViewById(R.id.botCadastroEmpresa);
        botCadastroEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroEmpregador();
            }
        });
        return v;

    }

    private boolean verificarCampos() {
        String nome = nomeCadastroEmpregador.getText().toString().trim();
        String email = emailCadastroEmpregador.getText().toString().trim();
        String senha = senhaCadastroEmpresa.getText().toString().trim();
        String repetirSenha = repeteSenhaCadastroEmpresa.getText().toString().trim();
        String cnpj = cnpjCadastroEmpresa.getText().toString().trim();
        String cidade = cidadeCadastroEmpresa.getText().toString().trim();
        if (validacao.verificarCampoVazio(nome)){
            this.nomeCadastroEmpregador.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoEmail(email)) {
            this.emailCadastroEmpregador.setError("Formato de email inválido");
            return false;
        } else if (validacao.verificarCampoVazio(senha)){
            this.senhaCadastroEmpresa.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(repetirSenha)) {
            this.repeteSenhaCadastroEmpresa.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cnpj)){
            this.cnpjCadastroEmpresa.setError("Campo vazio");
            return false;
        } else if (validacao.verificarCampoVazio(cidade)){
            this.cidadeCadastroEmpresa.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }

    public void cadastroEmpregador() {
        if (!this.verificarCampos()) {
            return;
        }
        if (this.empregadorServices.cadastrarEmpregador(this.criarEmpregador(), getContext())) {
            Toast.makeText(getContext(), "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
            Intent abreTelaEmpregador = new Intent(getActivity(), TelaCadastroCurriculo.class);
            startActivity(abreTelaEmpregador);
        } else {
            Toast.makeText(getContext(),"Já existe uma conta com este e-mail",Toast.LENGTH_SHORT).show();
        }
    }

    private Empregador criarEmpregador() {
        String nome = nomeCadastroEmpregador.getText().toString().trim();
        String email = emailCadastroEmpregador.getText().toString().trim();
        String senha = senhaCadastroEmpresa.getText().toString().trim();
        String cnpj = cnpjCadastroEmpresa.getText().toString().trim();
        String cidade = cidadeCadastroEmpresa.getText().toString().trim();
        Empregador empregador = new Empregador();
        empregador.setNome(nome);
        empregador.setEmail(email);
        empregador.setSenha(senha);
        empregador.setCnpj(cnpj);
        empregador.setCidade(cidade);
        return empregador;
    }
}


