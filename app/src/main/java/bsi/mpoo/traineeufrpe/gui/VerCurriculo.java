package bsi.mpoo.traineeufrpe.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bsi.mpoo.traineeufrpe.R;
import bsi.mpoo.traineeufrpe.infra.sessao.SessaoEstagiario;
import de.hdodenhof.circleimageview.CircleImageView;

public class VerCurriculo extends AppCompatActivity {

    TextView xp, obj, relacionamento, forte, basico, disciplina, nome, curso, instituicao, area;

    CircleImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_curriculo);
        Constroi();
        Bitmap bitmap = getImagem();
        Set(bitmap);
    }

    private void Set(Bitmap bitmap) {
        xp.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getExperiencia());
        obj.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getObjetivo());
        relacionamento.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getRelacionamento());
        forte.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getConhecimentos_especificos());
        basico.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getConhcimentos_basicos());
        disciplina.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getDisciplinas());
        imagem.setImageBitmap(bitmap);
        nome.setText(SessaoEstagiario.instance.getPessoa().getNome());
        curso.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getCurso());
        instituicao.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getInstituicao());
        area.setText(SessaoEstagiario.instance.getPessoa().getEstagiario().getCurriculo().getAreaAtuacao());
    }

    private void Constroi() {
        xp = findViewById(R.id.textViewxp);
        obj = findViewById(R.id.textViewobj);
        relacionamento = findViewById(R.id.textViewrel);
        forte = findViewById(R.id.textViewforte);
        basico = findViewById(R.id.textViewbasico);
        disciplina = findViewById(R.id.textViewdisciplinas);
        imagem = findViewById(R.id.imagemperfilcurriculo);
        nome = findViewById(R.id.textViewnome);
        curso = findViewById(R.id.textViewCurso);
        instituicao = findViewById(R.id.textViewInst);
        area = findViewById(R.id.textViewArea);
    }

    private Bitmap getImagem(){
        byte[] fotoEstagiario = SessaoEstagiario.getInstance().getPessoa().getEstagiario().getFoto();
        return BitmapFactory.decodeByteArray(fotoEstagiario, 0, fotoEstagiario.length);
    }
}

