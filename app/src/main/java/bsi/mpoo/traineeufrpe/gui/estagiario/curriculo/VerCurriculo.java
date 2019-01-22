package bsi.mpoo.traineeufrpe.gui.estagiario.curriculo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import bsi.mpoo.traineeufrpe.R;

public class VerCurriculo extends AppCompatActivity {


    private PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_curriculo);
        pdfView = findViewById(R.id.pdfCurriculo);
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            file = new File(bundle.getString("path",""));


        }

        pdfView.fromFile(file)
                .enableSwipe(true).
                swipeHorizontal(false).
                enableDoubletap(true).
                enableAntialiasing(true).
                load();

    }
}
