package bsi.mpoo.traineeufrpe.gui.estagiario.acesso;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.estagiario.Estagiario;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class ActEsqueciSenhaEstagiario extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    private static Random rand = new Random();
    private static char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private String codigo;
    private String codigoDigitado;
    private String emailInformado;
    Button btnEnviar, btnOK;
    private EditText edtEmail;
    private EditText edtCodigo;
    private TextView txtEmailOk;
    private TextView txtInformeCod;
    CardView cardView2;
    javax.mail.Session session;
    LoginServices loginServices = new LoginServices(this);
    private static String servidorEmail;
    private static String senhaEmail;
    ProgressDialog progressoDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_esqueci_senha_estagiario);
        servidorEmail = getString(R.string.REMETENTE_EMAIL);
        senhaEmail = getString(R.string.PW_REMETENTE_EMAIL);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        edtEmail = findViewById(R.id.campo_email);
        edtCodigo = findViewById(R.id.campo_codigo);
        txtEmailOk = findViewById(R.id.txtEmailOk);
        txtInformeCod = findViewById(R.id.txtInformeCod);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnOK = findViewById(R.id.btnOk);
        cardView2 = findViewById(R.id.cardView2);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                txtEmailOk.setVisibility(View.INVISIBLE);
                Estagiario estagiario = loginServices.EstagiarioByEmail(email, ActEsqueciSenhaEstagiario.this);
                Pessoa pessoa = new Pessoa();
                pessoa.setEstagiario(estagiario);
                if (ValidacaoGUI.isEmailInvalido(email)) {
                    edtEmail.setError("Digite um e-mail válido");
                } else if (!loginServices.isEmailCadastrado(email, ActEsqueciSenhaEstagiario.this)) {
                    edtEmail.setError("E-mail não cadastrado");
                } else {
                    emailInformado = edtEmail.getText().toString().trim();
                    codigo = codigoAleatorio();
                    SessaoEstagiario.instance.setPessoa(pessoa);
                    if (isOnline(ActEsqueciSenhaEstagiario.this)) {
                        iniciarEnvioCodigo();
                    }
                }
            }

        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoDigitado = edtCodigo.getText().toString().trim();
                if (codigoDigitado.equals(codigo)){
                    startActivity(new Intent(ActEsqueciSenhaEstagiario.this, MudarSenhaEstagiario.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Código informado não é válido.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void iniciarEnvioCodigo() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        session = javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(servidorEmail, senhaEmail);
            }
        });

        progressoDialog = ProgressDialog.show(this, "","Enviando o código de verificação",true);

        EnviarMensagem task = new EnviarMensagem();
        task.execute();
    }

    class EnviarMensagem extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (session != null) {
                    javax.mail.Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(servidorEmail));
                    message.setSubject("Trainee | Código de Verificação");
                    message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(emailInformado));
                    message.setContent("Seu código de verificação do Trainee é: " +
                            codigo + ".", "text/html; charset=utf-8");
                    Transport.send(message);
                    return true;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Erro ao tentar enviar o código", Toast.LENGTH_LONG).show();
            } return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressoDialog.dismiss();
            if (aBoolean) {
                Toast.makeText(getApplicationContext(), "Código enviado. Verifique o seu e-mail.", Toast.LENGTH_LONG).show();
                exibirParte2();
            } else {
                Toast.makeText(getApplicationContext(), "Não foi possível enviar o código. Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();

            }
        }
    }

    public boolean isOnline(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }

    private void exibirParte2() {
        txtEmailOk.setVisibility(View.VISIBLE);
        cardView2.setVisibility(View.VISIBLE);
    }

    public static String codigoAleatorio() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int X = rand.nextInt(letras.length);
            sb.append(letras[X]);
        }
        return sb.toString();
    }

}
