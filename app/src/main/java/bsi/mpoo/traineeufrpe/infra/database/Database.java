package bsi.mpoo.traineeufrpe.infra.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 65;
    private static final String DATABASE_NAME = "traineeapp.bd";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pessoa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL, " +
                "cpf text NOT NULL," +
                "cidade text NOT NULL," +
                "id_estagiario integer NOT NULL);");

        db.execSQL("CREATE TABLE estagiario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email text NOT NULL," +
                "senha text NOT NULL," +
                "id_curriculo integer NOT NULL, fotoestagiario blob NOT NULL);");

        db.execSQL("CREATE TABLE curriculo(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "curso text NOT NULL," +
                "instituicao text NOT NULL," +
                "experiencia text," +
                "objetivo text," +
                "basico text," +
                "especifico text," +
                "disciplina text," +
                "link text," +
                "areaAtuacao text NOT NULL);");

        db.execSQL("CREATE TABLE empregador(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL,"+
                "email text NOT NULL," +
                "cnpj text NOT NULL,"+
                "senha text NOT NULL," +
                "cidade NOT NULL, fotoperfil blob NOT NULL);");

        db.execSQL("CREATE TABLE vaga(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL,"+
                "requisito text NOT NULL," +
                "bolsa text NOT NULL,"+
                "area text NOT NULL," +
                "obs text NOT NULL," +
                "id_empregador integer NOT NULL," +
                "avaliacao int," +
                "data_criacao NOT NULL," +
                "horario text NOT NULL);");

        db.execSQL("CREATE TABLE controlador_vaga(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "id_vaga integer NOT NULL," +
                "id_empregador integer NOT NULL," +
                "id_pessoa integer NOT NULL," +
                "data_inscricao integer NOT NULL," +
                "status text NOT NULL);");

        db.execSQL("CREATE TABLE notificacoes(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "mensagem text NOT NULL," +
                "id_pessoa_envia integer," +
                "id_pessoa_recebe integer," +
                "id_empregador_envia integer," +
                "id_empregador_recebe integer," +
                "id_vaga integer);");

        db.execSQL("CREATE TABLE avaliacoesVagas(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idestagiarioavaliador INTEGER NOT NULL," + "" +
                "idvagavaliada INTEGER NOT NULL," +
                "notaAvaliacao DOUBLE NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pessoa");
        db.execSQL("DROP TABLE IF EXISTS estagiario");
        db.execSQL("DROP TABLE IF EXISTS curriculo");
        db.execSQL("DROP TABLE IF EXISTS empregador");
        db.execSQL("DROP TABLE IF EXISTS vaga");
        db.execSQL("DROP TABLE IF EXISTS controlador_vaga");
        db.execSQL("DROP TABLE IF EXISTS notificacoes");
        db.execSQL("DROP TABLE IF EXISTS avaliacoesVagas");
        onCreate(db);
    }

    public SQLiteDatabase getBancoLeitura(Context context){
        SQLiteDatabase bancoDados = this.getReadableDatabase();
        return bancoDados;
    }

    public SQLiteDatabase getBancoEscrita(Context context) {
        SQLiteDatabase bancoDados = this.getWritableDatabase();
        return bancoDados;
    }
}
