package bsi.mpoo.traineeufrpe.gui.main;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bsi.mpoo.traineeufrpe.R;

public class ActContato extends AppCompatActivity {

    Button cancel, send;
    private EditText edtEmail;
    private EditText edtMensagem;
    javax.mail.Session session;
    String servidorEmail;
    String senhaEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        this.edtEmail = findViewById(R.id.editEmail3);
        this.edtMensagem = findViewById(R.id.message);


        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                if (isOnline(getApplicationContext())) {
                    enviarEmail();
                } else {
                    Toast.makeText(getApplicationContext(), "Não foi possível enviar a mensagem. Verifique a sua conexão com a internet.", Toast.LENGTH_LONG).show();
                }

            }
        });

        servidorEmail = getString(R.string.REMETENTE_EMAIL);
        senhaEmail = getString(R.string.PW_REMETENTE_EMAIL);

    }

    private void enviarEmail() {
        String msg = edtMensagem.getText().toString();
        String email = edtEmail.getText().toString();

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        try {
            session = javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(servidorEmail, senhaEmail);
                }
            });
            if (session != null) {
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(servidorEmail));
                message.setSubject("Ajuda - Trainee");
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("traineeufrpe@outlook.com"));
                message.setContent(msg.concat("\n\n Mensagem enviada por: ") + email, "text/html; charset=utf-8");
                Transport.send(message);
                Toast.makeText(getApplicationContext(), "Mensagem enviada com sucesso. Aguarde o nosso contato.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Não foi possível conectar ao servidor. Verfique sua conexão com a internet.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}