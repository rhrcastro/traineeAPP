package bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.PerfilEmpregador;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thal3.trainee.R;

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.gui.empregador.gui.HomeEmpregador.tela_nova.TelaEmpregadorPrincipal;
import bsi.mpoo.traineeufrpe.infra.SessaoEmpregador.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices.EmpregadorServices;

public class PerfilEmpregador extends AppCompatActivity {
    private String nomeEmpresa;
    private String localizacao;
    private EmpregadorServices empregadorServices = new EmpregadorServices(this);
    private long id;

    private TextView edtNomeEmpresa;
    private TextView edtLocalizacao;
    private TextView Mvagas;
    private ImageView imgEmpresa;
    private final int newImage = 1;
    private static final int PERMISSION_REQUEST = 0;


    public PerfilEmpregador() {
        this.nomeEmpresa = SessaoEmpregador.getInstance().getEmpregador().getNome();
        this.localizacao = SessaoEmpregador.getInstance().getEmpregador().getCidade();
        this.id = SessaoEmpregador.getInstance().getEmpregador().getId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_empregador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgEmpresa = findViewById(R.id.imagemEmpresa);
        byte[] foto = SessaoEmpregador.getInstance().getEmpregador().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        this.imgEmpresa.setImageBitmap(bitmap);
        edtNomeEmpresa = findViewById(R.id.nomeEmpresa);
        edtNomeEmpresa.setText(this.nomeEmpresa);
        edtLocalizacao = findViewById(R.id.localizacaoEmpresa);
        edtLocalizacao.setText(this.localizacao);
        Mvagas =(TextView) findViewById(R.id.minhasVagas);
        Mvagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaP = new Intent(PerfilEmpregador.this, TelaEmpregadorPrincipal.class);
                startActivity(abreTelaP);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissaoGravarLerArquivos();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == newImage) {
            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                setFotoEmpregador(bitmap);
                editarImagemObjeto();
                Toast.makeText(this, "Imagem trocada com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setFotoEmpregador(Bitmap bitmap)
    {
        this.imgEmpresa.setImageBitmap(bitmap);
    }

    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) imgEmpresa.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, saida);
        return saida.toByteArray();
    }

    public void editarImagemObjeto() {
        byte[] foto = conveterImageViewToByte();
        SessaoEmpregador.getInstance().getEmpregador().setFoto(foto);
        empregadorServices.alterarFotoEmpregador(SessaoEmpregador.getInstance().getEmpregador());
    }

    private void escolherFoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), newImage);
    }

    private void permissaoGravarLerArquivos() {
        int permissionCheckRead = ContextCompat.checkSelfPermission(PerfilEmpregador.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PerfilEmpregador.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(PerfilEmpregador.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            escolherFoto();
        }
    }
}
