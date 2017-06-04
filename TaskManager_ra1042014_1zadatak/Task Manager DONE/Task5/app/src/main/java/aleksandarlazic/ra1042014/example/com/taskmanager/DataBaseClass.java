package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lazic on 27-May-17.
 */

public class DataBaseClass extends SQLiteOpenHelper {

    private static String DB_NAME="Zadaci.db";
    private static String TABLE_NAME="tabela";
    private static int VERSION=1;
    private static String IME="ime_zadatka";
    private static String DATUM="datum_zadatka";
    private static String VREME="vreme_zadatka";
    private static String PRIORITET="prioritet_zadatka";
    private static String OPIS ="opis_zadatka";
    private static String REMINDER="podsetnik_zadatka";
    private static String ZAVRSEN="zavrsen_zadatak";
    private static String ID="id_zadatka";
    private boolean reminder,zavrsen;

    public DataBaseClass(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+IME+" TEXT, "+DATUM+" TEXT, "
                +VREME+" TEXT, "+ PRIORITET+" INTEGER,"+OPIS+" TEXT, "+REMINDER+" INTEGER, "
                + ZAVRSEN+" INTEGER, "+ID+" INTEGER PRIMARY KEY );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }

    private Zadatak kreirajZadatak(Cursor cursor)
    {
        String ime = cursor.getString(cursor.getColumnIndex(IME));
        String vreme = cursor.getString(cursor.getColumnIndex(VREME));
        String datum = cursor.getString(cursor.getColumnIndex(DATUM));
        int prioritet = cursor.getInt(cursor.getColumnIndex(PRIORITET));
        String opis = cursor.getString(cursor.getColumnIndex(OPIS));
        int intReminder = cursor.getInt(cursor.getColumnIndex(REMINDER));
        int intZavrsen = cursor.getInt(cursor.getColumnIndex(ZAVRSEN));
        reminder = (intReminder==1) ? true:false;
        zavrsen = (intZavrsen==1) ? true:false;
        return new Zadatak(ime,opis,prioritet,vreme,datum,zavrsen,reminder);
    }

    public void dodajZadatak(Zadatak z)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IME,z.getIme());
        contentValues.put(DATUM,z.getDatum());
        contentValues.put(VREME,z.getVreme());
        contentValues.put(PRIORITET,z.getPrioritet());
        contentValues.put(OPIS,z.getOpis());

        if(z.isPodsetnik())
        {
            contentValues.put(REMINDER,1);
        }else
        {
            contentValues.put(REMINDER,0);
        }

        if(z.isZavrsen())
        {
            contentValues.put(ZAVRSEN,1);
        }else
        {
            contentValues.put(ZAVRSEN,0);
        }

        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_NAME,null,contentValues);
        database.close();
    }

    public void updateZadatke(Zadatak z,String ime,String opis,String datum,String vreme,boolean zavrsen,boolean reminder,int prioritet)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IME,z.getIme());
        contentValues.put(DATUM,z.getDatum());
        contentValues.put(VREME,z.getVreme());
        contentValues.put(PRIORITET,z.getPrioritet());
        contentValues.put(OPIS,z.getOpis());

        if(z.isPodsetnik())
        {
            contentValues.put(REMINDER,1);
        }else
        {
            contentValues.put(REMINDER,0);
        }

        if(z.isZavrsen())
        {
            contentValues.put(ZAVRSEN,1);
        }else
        {
            contentValues.put(ZAVRSEN,0);
        }

        SQLiteDatabase database=getWritableDatabase();
        database.update(TABLE_NAME,contentValues,IME+"=?",new String[]{ime});
        database.close();
    }

    public void obrisiZadatak(String ime)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME,IME+"=?",new String[] {ime});
        database.close();
    }


    public Zadatak nadjiZadatak(String ime)
    {
        SQLiteDatabase  database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,IME+"=?",new String[]{ime},
                null,null,null);
        cursor.moveToFirst();
        Zadatak z = kreirajZadatak(cursor);
        close();
        return z;
    }

    public Zadatak[] prodjiKrozZadatke()
    {
        int brojZadataka=0;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null,null);
        if(cursor.getCount()<=0)
        {
            return null;
        }

        Zadatak[] zadaci = new Zadatak[cursor.getCount()];
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            zadaci[brojZadataka++]= kreirajZadatak(cursor);
        }

        database.close();
        return zadaci;

    }
}
