package bsi.mpoo.traineeufrpe.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bsi.mpoo.traineeufrpe.dominio.vaga.Vaga;
import bsi.mpoo.traineeufrpe.infra.database.Database;

public class VagaDAO {
    private Database bancoDados;

    public VagaDAO(Context context){
        bancoDados = new Database(context);
    }

    private Vaga criarVaga(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexNome = cursor.getColumnIndex("nome");
        String nome = cursor.getString(indexNome);
        int indexRequisito = cursor.getColumnIndex("requisito");
        String req = cursor.getString(indexRequisito);
        int indexBolsa = cursor.getColumnIndex("bolsa");
        String bolsa = cursor.getString(indexBolsa);
        int indexArea = cursor.getColumnIndex("area");
        String area = cursor.getString(indexArea);
        int indexObs = cursor.getColumnIndex("obs");
        String obs = cursor.getString(indexObs);
        Vaga vaga = new Vaga();
        vaga.setId(id);
        vaga.setNome(nome);
        vaga.setRequisito(req);
        vaga.setBolsa(bolsa);
        vaga.setArea(area);
        vaga.setObs(obs);
        return vaga;
    }

    public long inserirVaga(Vaga vaga) {
        SQLiteDatabase escreverBanco = bancoDados.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", vaga.getNome());
        valores.put("requisito", vaga.getRequisito());
        valores.put("area",vaga.getArea());
        valores.put("bolsa",vaga.getBolsa());
        valores.put("obs",vaga.getObs());
        valores.put("id_empregador",vaga.getEmpregador().getId());
        valores.put("data_criacao", System.currentTimeMillis());
        long resultado = escreverBanco.insert("vaga", null, valores);
        escreverBanco.close();
        return resultado;
    }

    public Cursor getDatabyEmpregador(long id){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query =  "SELECT * FROM vaga " +
                "WHERE id_empregador = ?";
        String[] args = {String.valueOf(id)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public Cursor getAllDataOrderByDate(){
        SQLiteDatabase db = bancoDados.getReadableDatabase();
        String query = "SELECT * FROM vaga ORDER BY data_criacao DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getId(String name){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        String query = "SELECT  id FROM vaga " +
                "WHERE nome = ?";
        String[] args = {String.valueOf(name)};
        Cursor data = db.rawQuery(query, args);
        return data;
    }

    public Vaga getId(long id, Context context) {
        String query = "SELECT * FROM vaga " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        return this.carregarObjeto(query, args, context);
    }

    private Vaga carregarObjeto(String query, String[] args, Context context) {
        SQLiteDatabase leitorBanco = bancoDados.getBancoLeitura(context);
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Vaga vaga = null;
        if (cursor.moveToNext()) {
            vaga = criarVaga(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return vaga;
    }

    public void mudarNomeVaga(Vaga vaga){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", vaga.getNome());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }
    public void mudarBolsaVaga(Vaga vaga){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bolsa", vaga.getBolsa());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }
    public void mudarRequisitoVaga(Vaga vaga){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("requisito", vaga.getRequisito());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }

    public void mudarObsVaga(Vaga vaga){
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("obs", vaga.getObs());
        db.update("vaga", values, "id = ?", new String[]{String.valueOf(vaga.getId())});

    }
    public void deletarVaga(long id2){
        SQLiteDatabase db =  bancoDados.getWritableDatabase();
        String query = "DELETE FROM vaga " +
                "WHERE id = " + id2;
        db.execSQL(query);

    }

}
