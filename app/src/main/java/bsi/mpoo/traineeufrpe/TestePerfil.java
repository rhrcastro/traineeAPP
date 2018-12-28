package bsi.mpoo.traineeufrpe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import bsi.mpoo.traineeufrpe.dominio.estagiario.Curriculo;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import bsi.mpoo.traineeufrpe.negocio.LoginServices;

public class TestePerfil extends AppCompatActivity {

    private TextView curso, instituicao, area, email, cidade;
    private ImageView imagem;
    private LoginServices loginServices = new LoginServices(this);
    Toolbar toolbar;

    private String strCurso, strInstituicao, strArea, strEmail, strCidade;
    private String strNome;

    public TestePerfil(){
        Curriculo curriculo = getCurriculo();
        strCurso = curriculo.getCurso();
        strInstituicao = curriculo.getInstituicao();
        strArea = curriculo.getAreaAtuacao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testando_cards);
        curso = (TextView) findViewById(R.id.campo_curso);
        instituicao = (TextView) findViewById(R.id.campo_instituicao);
        area = (TextView) findViewById(R.id.campo_area);
        email = (TextView) findViewById(R.id.campo_email);
        cidade = (TextView) findViewById(R.id.campo_local);
        imagem = (ImageView) findViewById(R.id.campo_imagem);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bitmap bitmap = getImagem();
        Curriculo curriculo = getCurriculo();
        curso.setText(strCurso);
        instituicao.setText(strInstituicao);
        area.setText(strArea);
        email.setText(SessaoEstagiario.getInstance().getPessoa().getEstagiario().getEmail());
        cidade.setText(SessaoEstagiario.getInstance().getPessoa().getCidade());
        imagem.setContentDescription(SessaoEstagiario.getInstance().getPessoa().getNome());
        imagem.setImageBitmap(bitmap);
        toolbar.setTitle(SessaoEstagiario.getInstance().getPessoa().getNome());
    }

    private Bitmap getImagem(){
        byte[] fotoEstagiario = SessaoEstagiario.getInstance().getPessoa().getEstagiario().getFoto();
        return BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
    }

    private Curriculo getCurriculo(){
        return SessaoEstagiario.getInstance().getPessoa().getEstagiario().getCurriculo();
    }
}
