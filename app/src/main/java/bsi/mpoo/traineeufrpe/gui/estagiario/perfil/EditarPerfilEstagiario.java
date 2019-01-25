package bsi.mpoo.traineeufrpe.gui.estagiario.perfil;

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
import android.util.Patterns;
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
import java.util.ArrayList;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.dominio.Notificacao;
import bsi.mpoo.traineeufrpe.dominio.empregador.Empregador;
import bsi.mpoo.traineeufrpe.dominio.pessoa.Pessoa;
import bsi.mpoo.traineeufrpe.gui.estagiario.acesso.MudarSenhaEstagiario;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.infra.validacao.ValidacaoGUI;
import bsi.mpoo.traineeufrpe.negocio.InscricaoServices;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;
import bsi.mpoo.traineeufrpe.negocio.NotificacaoServices;

public class EditarPerfilEstagiario extends AppCompatActivity {

    private Pessoa pessoa = new Pessoa();
    private String nome;
    private String curso;
    private String instituicao;
    private String email;
    private String link;
    private EditText editNome;
    private EditText editCurso;
    private EditText editInstituicao;
    private EditText editLink;
    private EditText editEmail;
    private String emailTemp;
    private LoginServices loginServices = new LoginServices(EditarPerfilEstagiario.this);
    private NotificacaoServices notificacaoServices = new NotificacaoServices(EditarPerfilEstagiario.this);
    private InscricaoServices inscricaoServices = new InscricaoServices(EditarPerfilEstagiario.this);
    private ImageView imgPerfil;
    private static final int PERMISSION_REQUEST = 0;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static String mCurrentPhotoPath;
    Toolbar toolbar;
    Button mudaSenha;


    public EditarPerfilEstagiario(){
        this.pessoa = SessaoEstagiario.getInstance().getPessoa();
        this.nome = pessoa.getNome();
        this.curso = pessoa.getEstagiario().getCurriculo().getCurso();
        this.instituicao = pessoa.getEstagiario().getCurriculo().getInstituicao();
        this.email = pessoa.getEstagiario().getEmail();
        this.link = pessoa.getEstagiario().getCurriculo().getLink();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_estagiario);
        Constroi();
        byte[] imagemEmBits = pessoa.getEstagiario().getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemEmBits, 0, imagemEmBits.length);
        Set(bitmap);
        emailTemp = editEmail.getText().toString().trim();
        this.imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] opcoes = {"Tirar foto", "Escolher foto"};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPerfilEstagiario.this);
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

        mudaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarPerfilEstagiario.this, MudarSenhaEstagiario.class);
                startActivity(intent);
            }
        });
    }

    private void Set(Bitmap bitmap) {
        editLink.setText(this.link);
        editNome.setText(this.nome);
        editCurso.setText(this.curso);
        editInstituicao.setText(this.instituicao);
        editEmail.setText(this.email);
        this.imgPerfil.setImageBitmap(bitmap);
    }

    private void Constroi() {
        editNome = findViewById(R.id.campo_nome);
        editLink = findViewById(R.id.campo_link);
        editCurso =  findViewById(R.id.campo_curso);
        editInstituicao =  findViewById(R.id.campo_instituicao);
        editEmail = findViewById(R.id.campo_email);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgPerfil =  findViewById(R.id.photo_profile);
        mudaSenha = findViewById(R.id.editSenhaPerfil);
    }


    @Override
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
        int permissionCheckRead = ContextCompat.checkSelfPermission(EditarPerfilEstagiario.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditarPerfilEstagiario.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(EditarPerfilEstagiario.this,
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
            case REQUEST_IMAGE_CAPTURE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirCameraIntent();
                } else {
                    Toast.makeText(this, "Erro: Permissão é necessária", Toast.LENGTH_LONG).show();
                }
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
                        imgPerfil.setImageBitmap(bm1);
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
                        this.imgPerfil.setImageBitmap(bitmap);
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
        SessaoEstagiario.getInstance().getPessoa().getEstagiario().setFoto(foto);
        loginServices.alterarFotoEstagiario(SessaoEstagiario.getInstance().getPessoa().getEstagiario());
    }

    private byte[] conveterImageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable) imgPerfil.getDrawable()).getBitmap();
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
            pessoa.setNome(editNome.getText().toString().trim());
            pessoa.getEstagiario().setEmail(editEmail.getText().toString().trim());
            pessoa.getEstagiario().getCurriculo().setCurso(editCurso.getText().toString().trim());
            pessoa.getEstagiario().getCurriculo().setInstituicao(editInstituicao.getText().toString().trim());
            pessoa.getEstagiario().getCurriculo().setLink(editLink.getText().toString().trim());
            loginServices.alterarDadosEstagiario(pessoa);
            enviarNotificacaoParaEmpregador(pessoa);
            Toast.makeText(this, "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();
            sair();
        }
    }

    private void enviarNotificacaoParaEmpregador(Pessoa pessoa) {
        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setMensagem("Alterou dados do perfil.");
        novaNotificacao.setPessoaEnvia(pessoa);
        ArrayList<Empregador> empresasAInformar = inscricaoServices.getEmpresasByPessoa(pessoa);
        for (Empregador e : empresasAInformar){
            novaNotificacao.setEmpregadorRecebe(e);
            notificacaoServices.enviar4Empregador(novaNotificacao);
        }
    }

    private void sair(){
        Intent intent = new Intent(EditarPerfilEstagiario.this, ActExibirPerfilEstagiario.class);
        startActivity(intent);
        finish();
    }

    public boolean verificarCampos() {
        EditText[] campos = {editNome, editCurso, editInstituicao, editEmail};
        for (EditText editText : campos) {
            String campoAtual = editText.getText().toString().trim();
            if (ValidacaoGUI.isCampoVazio(campoAtual)) {
                editText.setError("Este campo não pode ser vazio");
                return false;
            }
        }
        String email = editEmail.getText().toString().trim();
        if (ValidacaoGUI.isEmailInvalido(email)){
            editEmail.setError("E-mail inválido");
            return false;
        } else if (!emailTemp.equals(email) && loginServices.isEmailCadastrado(email, this)){
            editEmail.setError("E-mail já cadastrado");
            return false;
        } else if (!editLink.getText().toString().trim().equals("") &&
                !Patterns.WEB_URL.matcher(editLink.getText().toString().trim()).matches()){
            editLink.setError("Insira um link válido");
            return false;
        } if (!(editLink.getText().toString().trim().startsWith("http://") ||
                editLink.getText().toString().trim().startsWith("https://"))){
            editLink.setText("http://" + editLink.getText().toString().trim());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActExibirPerfilEstagiario.class);
        startActivity(intent);
        finish();
    }
}