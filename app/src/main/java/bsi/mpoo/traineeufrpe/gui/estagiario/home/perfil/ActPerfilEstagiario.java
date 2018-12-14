package bsi.mpoo.traineeufrpe.gui.estagiario.home.perfil;

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

import java.io.ByteArrayOutputStream;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class ActPerfilEstagiario extends AppCompatActivity {
    private String nomeEstagiario;
    private String localizacaoEstagiario;
    private LoginServices loginServices = new LoginServices(this);
    private long id;

    private TextView edtNomeEstagiario;
    private TextView edtLocalizacaoEstagiario;
    private ImageView imgEstagiario;
    private final int newImage = 1;
    private static final int PERMISSION_REQUEST = 0;
    public ActPerfilEstagiario() {
        this.nomeEstagiario = SessaoEstagiario.getInstance().getPessoa().getNome();
        this.localizacaoEstagiario = SessaoEstagiario.getInstance().getPessoa().getCidade();
        this.id = SessaoEstagiario.getInstance().getPessoa().getEstagiario().getId();
    }
  protected  void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_perfil_estagiario);
      imgEstagiario = findViewById(R.id.imagemPerfilEstagiario);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      byte[] fotoEstagiario = SessaoEstagiario.getInstance().getPessoa().getEstagiario().getFoto();
      Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
      this.imgEstagiario.setImageBitmap(bitmap);
      edtNomeEstagiario = findViewById(R.id.nomePerfilEstagiario);
      edtNomeEstagiario.setText(this.nomeEstagiario);
      edtLocalizacaoEstagiario = findViewById(R.id.localizacaoEstagiario);
      edtLocalizacaoEstagiario.setText(this.localizacaoEstagiario);
      
      FloatingActionButton trocarImagemEstagiario = (FloatingActionButton) findViewById(R.id.trocarImagem);
      trocarImagemEstagiario.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
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
                setFotoEstagiario(bitmap);
                editarImagemObjeto();
                Toast.makeText(this, "Imagem trocada com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void setFotoEstagiario(Bitmap bitmap) {
        this.imgEstagiario.setImageBitmap(bitmap);
    }
    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) imgEstagiario.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, saida);
        return saida.toByteArray();
    }
    public void editarImagemObjeto() {
        byte[] foto = conveterImageViewToByte();
        SessaoEstagiario.getInstance().getPessoa().getEstagiario().setFoto(foto);
        loginServices.alterarFotoEstagiario(SessaoEstagiario.getInstance().getPessoa().getEstagiario());
    }
    private void permissaoGravarLerArquivos() {
        int permissionCheckRead = ContextCompat.checkSelfPermission(ActPerfilEstagiario.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActPerfilEstagiario.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(ActPerfilEstagiario.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            escolherFoto();
        }
    }
    private void escolherFoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), newImage);
    }
}