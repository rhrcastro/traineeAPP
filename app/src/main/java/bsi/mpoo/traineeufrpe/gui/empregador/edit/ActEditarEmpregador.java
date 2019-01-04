package bsi.mpoo.traineeufrpe.gui.empregador.edit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.gui.empregador.perfil.PerfilEmp;
import bsi.mpoo.traineeufrpe.gui.estagiario.perfil.ActExibirPerfilEstagiario;
import bsi.mpoo.traineeufrpe.gui.estagiario.perfil.EditarPerfilEstagiario;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.EmpregadorServices;
import bsi.mpoo.traineeufrpe.persistencia.EmpregadorDAO;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.gui.main.ActHome;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEmpregador;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;

public class ActEditarEmpregador extends AppCompatActivity {
    private Empregador empregador;
    private long id;
    private String nomeEmpregador;
    private String emailEmpregador;
    private EditText edtNomeEmpregador;
    private EditText edtEmailEmpregador;
    private String emailTempEmpregador;
    private EmpregadorServices empregadorServices = new EmpregadorServices(ActEditarEmpregador.this);
    private ImageView imgEmpregador;
    private static final int PERMISSION_REQUEST = 0;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static String mCurrentPhotoPath;
    Toolbar toolbar;

    public ActEditarEmpregador() {
        this.empregador = SessaoEmpregador.getInstance().getEmpregador();
        this.nomeEmpregador = SessaoEmpregador.getInstance().getEmpregador().getNome();
        this.emailEmpregador = SessaoEmpregador.getInstance().getEmpregador().getEmail();
        this.emailTempEmpregador = emailEmpregador;
        this.id = SessaoEmpregador.getInstance().getEmpregador().getId();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empresa);
        edtNomeEmpregador = findViewById(R.id.campo_nome);
        edtEmailEmpregador = findViewById(R.id.campo_email);
        edtNomeEmpregador.setText(this.nomeEmpregador);
        edtEmailEmpregador.setText(this.emailEmpregador);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgEmpregador = findViewById(R.id.photo_profile);
        byte[] fotoEmpregador = SessaoEmpregador.getInstance().getEmpregador().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEmpregador, 0, fotoEmpregador.length);
        this.imgEmpregador.setImageBitmap(bitmap);
        this.imgEmpregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] opcoes = {"Tirar foto", "Escolher foto"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActEditarEmpregador.this);
                builder.setTitle("Alterar Foto");
                builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("Tirar foto".equals(opcoes[which])){
                            getPermissionsCamera();
                        }
                        else if ("Escolher foto".equals(opcoes[which])){
                            getPermissionsGaleria();
                        }
                    }
                });
                builder.show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        salvarAlteracoes();
        return super.onOptionsItemSelected(item);
    }

    private void getPermissionsCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else
            abrirCameraIntent();
    }

    private void getPermissionsGaleria() {
        int permissionCheckRead = ContextCompat.checkSelfPermission(ActEditarEmpregador.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActEditarEmpregador.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(ActEditarEmpregador.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            abrirGaleriaIntent();
        }
    }


    private void abrirCameraIntent() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile("PHOTOAPP", ".jpg", storageDir);
                mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath();
            }
            catch(IOException ex){
                Toast.makeText(getApplicationContext(), "Erro ao tirar a foto", Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void abrirGaleriaIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirCameraIntent();
                } else {
                    Toast.makeText(this, "Erro: Permissão é necessária", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bm1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(mCurrentPhotoPath)));
                        bm1 = getThumbnailFromBitmap(bm1);
                        imgEmpregador.setImageBitmap(bm1);
                        salvarFoto();
                        Toast.makeText(this, "Imagem alterada com sucesso", Toast.LENGTH_SHORT).show();
                    } catch(FileNotFoundException fnex){
                        Toast.makeText(getApplicationContext(), "Foto não encontrada", Toast.LENGTH_LONG).show();
                    }
                }
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        bitmap = getThumbnailFromBitmap(bitmap);
                        this.imgEmpregador.setImageBitmap(bitmap);
                        salvarFoto();
                        Toast.makeText(this, "Imagem alterada com sucesso", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public void salvarFoto() {
        byte[] foto = conveterImageViewToByte();
        SessaoEmpregador.getInstance().getEmpregador().setFoto(foto);
        empregadorServices.alterarFotoEmpregador(SessaoEmpregador.getInstance().getEmpregador());
    }

    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) imgEmpregador.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, saida);
        return saida.toByteArray();
    }

    private Bitmap getThumbnailFromBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max>512) {
            int thumbWidth = Math.round((512f/max)* width);
            int thumbHeight = Math.round((512f/max)* height);
            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, thumbWidth , thumbHeight);
            bitmap.recycle();
            return thumbnail ;
        } else {
            return bitmap ;
        }
    }

    private void salvarAlteracoes(){
        if (verificarCampos()){
            empregador.setNome(edtNomeEmpregador.getText().toString().trim());
            empregador.setEmail(edtEmailEmpregador.getText().toString().trim());
            empregadorServices.alterarDadosEmpregador(empregador);
            Toast.makeText(this, "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();
            sair();
        }
    }

    private void sair(){
        Intent intent = new Intent(ActEditarEmpregador.this, PerfilEmp.class);
        startActivity(intent);
        finish();
    }

    public boolean verificarCampos() {
        EditText[] campos = {edtNomeEmpregador, edtEmailEmpregador};
        for (EditText editText : campos) {
            String campoAtual = editText.getText().toString().trim();
            if (ValidacaoGUI.isCampoVazio(campoAtual)) {
                editText.setError("Este campo não pode ser vazio");
                return false;
            }
        }
        String email = edtEmailEmpregador.getText().toString().trim();
        if (ValidacaoGUI.isEmailInvalido(email)){
            edtEmailEmpregador.setError("E-mail inválido");
            return false;
        } else if (!emailTempEmpregador.equals(email) && empregadorServices.isEmailCadastrado(email, this)){
            edtEmailEmpregador.setError("E-mail já cadastrado");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PerfilEmp.class);
        startActivity(intent);
        finish();
    }
}

