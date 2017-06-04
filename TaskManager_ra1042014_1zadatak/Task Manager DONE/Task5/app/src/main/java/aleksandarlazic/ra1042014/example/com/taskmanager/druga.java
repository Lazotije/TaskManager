package aleksandarlazic.ra1042014.example.com.taskmanager;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;




public class druga extends AppCompatActivity {

    private Button crveni;
    private Button zuti;
    private Button zeleni;
    private Button dodaj;
    private Button otkazi;
    private EditText ime;
    private EditText opis;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox checkBox;
    private boolean azuriranje;
    private int flag = 0;
    private int flag2 = 0;
    private int prioritet =0;
    public static String TAG="druga Activity";
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minutes;
    private String datum;
    private String vreme;
    private Zadatak z;
    private DataBaseClass db;
    private static final int RESULT_OK_UPDATE = 5;
    private static final int RESULT_OK_DELETE = 6;


    private int vratiPrioritet(){
        return prioritet;
    }

    private String uzmiVreme(){
        hours = timePicker.getCurrentHour();
        minutes =  timePicker.getCurrentMinute();
        if(hours <10 && minutes <10){
            vreme = "0"+Integer.toString(hours)+":"+"0"+Integer.toString(minutes);
        }else if(hours <10){
            vreme = "0"+Integer.toString(hours)+":"+Integer.toString(minutes);
        }else if(minutes<10){
            vreme = Integer.toString(hours)+":"+"0"+Integer.toString(minutes);
        }else{
            vreme = Integer.toString(hours)+":"+Integer.toString(minutes);
        }

        return vreme;
    }

    private String uzmiDatum(){
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() +1;
        year=datePicker.getYear();
        datum = Integer.toString(day)+"."+Integer.toString(month)+"."+Integer.toString(year);
        Log.d(TAG, "uzmiDatum: "+ datum);
        return datum;
    }

    private int proveraImena(){
        if(ime.getText().toString().trim().isEmpty())
            return 0;
        else
            return 1;
    }

    private int proveraOpisa(){
        if(opis.getText().toString().trim().isEmpty())
            return 0;
        else
            return 1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druga);

        crveni = (Button) findViewById(R.id.button5);
        zuti   = (Button) findViewById(R.id.button6);
        zeleni = (Button) findViewById(R.id.button7);
        dodaj  = (Button) findViewById(R.id.button3);
        otkazi = (Button) findViewById(R.id.button4);
        ime    = (EditText) findViewById(R.id.imeZadatka);
        opis   = (EditText) findViewById(R.id.opisZadatka);
        datePicker = (DatePicker) findViewById(R.id.datum);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        db = new DataBaseClass(druga.this);

        Intent i =getIntent();
        if (i.hasExtra("dugi_klik_intent")){
            azuriranje=true;
            z= (Zadatak)i.getSerializableExtra("dugi_klik_intent");
            db.obrisiZadatak(z.getIme());
            dodaj.setText("Azuriraj");
            otkazi.setText("Obrisi");
            ime.setText(z.getIme());
            opis.setText(z.getOpis());
            datum = z.getDatum();
            checkBox.setChecked(z.isZavrsen());
            prioritet = z.getPrioritet();

            String zaVreme[] = z.getVreme().split(":");
            String tpSati = zaVreme[0];
            String tpMinuti = zaVreme[1];
            int h = Integer.valueOf(tpSati);
            int mi = Integer.valueOf(tpMinuti);
            timePicker.setCurrentHour(h);
            timePicker.setCurrentMinute(mi);

//            String zaDatum[] = z.getDatum().split(".");
//            String dpMeseci = zaDatum[1];
//            String dpGodine = zaDatum[2];
//            int d = Integer.valueOf(dpDani);
//            int m = Integer.valueOf(dpMeseci);
//            int y = Integer.valueOf(dpGodine);
//            datePicker.updateDate(y,m,d);


            if(prioritet==1)
            {
                flag2=2;
                prioritet=1;
                zeleni.setEnabled(true);
                if(flag==1){
                    dodaj.setEnabled(true);
                }
                else{
                    dodaj.setEnabled(false);
                }

                crveni.setEnabled(true);
                zuti.setEnabled(true);
                zeleni.setEnabled(false);

            }
            else if(prioritet == 2)
            {
                flag2=2;
                prioritet=2;
                if(flag==1){
                    dodaj.setEnabled(true);
                }
                else{
                    dodaj.setEnabled(false);
                }

                crveni.setEnabled(true);
                zeleni.setEnabled(true);
                zuti.setEnabled(false);
            }
            else
            {
                flag2=2;
                prioritet=3;
                if(flag==1){
                    dodaj.setEnabled(true);
                }
                else{
                    dodaj.setEnabled(false);
                }

                zuti.setEnabled(true);
                zeleni.setEnabled(true);
                crveni.setEnabled(false);
            }


        }else{
            azuriranje=false;
        }



        dodaj.setEnabled(false);

        ime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(proveraImena()==1 && proveraOpisa()==1)
                    flag=1;
                else
                    flag=0;

                if(flag==1 && flag2==2){ //provera za boju
                    dodaj.setEnabled(true);
                }else {
                    dodaj.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        opis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(proveraImena()==1 && proveraOpisa()==1)
                    flag=1;
                else
                    flag=0;

                if(flag==1 && flag2==2){
                    dodaj.setEnabled(true);
                }else {
                    dodaj.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "usao u dodaj: ");
                if(!azuriranje) {
                    z = new Zadatak(ime.getText().toString(), opis.getText().toString(), vratiPrioritet(),
                            uzmiVreme(), uzmiDatum(),false,checkBox.isChecked());
                    db.dodajZadatak(z);
                    Zadatak[]zadataks = db.prodjiKrozZadatke();
                    TaskAdapter adapter = MainActivity.getTaskAdapter();
                    adapter.azuriraj(zadataks);

                    Intent i= getIntent();
                    i.putExtra("novi podaci",3);
                    setResult(RESULT_OK,i);
                    finish();

                }else{
                    z = new Zadatak(ime.getText().toString(), opis.getText().toString(), vratiPrioritet(),
                            uzmiVreme(), uzmiDatum(),false,checkBox.isChecked());
                    db.dodajZadatak(z);
                    Zadatak[]zadataks = db.prodjiKrozZadatke();
                    TaskAdapter adapter= MainActivity.getTaskAdapter();
                    adapter.azuriraj(zadataks);
                    setResult(RESULT_OK_UPDATE);
                    finish();
                }
            }
        });

        otkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!azuriranje) {
                    Intent i = new Intent(druga.this,MainActivity.class);
                    setResult(RESULT_CANCELED);
                    finish();
                }else{
                    Zadatak[]zadataks = db.prodjiKrozZadatke();
                    TaskAdapter adapter = MainActivity.getTaskAdapter();
                    adapter.azuriraj(zadataks);
                    setResult(RESULT_OK_DELETE);
                    finish();
                }
            }
        });

        crveni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag2=2;
                prioritet=3;
                if(flag==1){
                    dodaj.setEnabled(true);
                }
                else{
                    dodaj.setEnabled(false);
                }

                zuti.setEnabled(true);
                zeleni.setEnabled(true);
                crveni.setEnabled(false);

            }
        });

        zuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag2=2;
                prioritet=2;
                if(flag==1){
                    dodaj.setEnabled(true);
                }
                else{
                    dodaj.setEnabled(false);
                }

                crveni.setEnabled(true);
                zeleni.setEnabled(true);
                zuti.setEnabled(false);
            }
        });

        zeleni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag2=2;
                prioritet=1;
                zeleni.setEnabled(true);
                if(flag==1){
                    dodaj.setEnabled(true);
                }
                else{
                    dodaj.setEnabled(false);
                }

                crveni.setEnabled(true);
                zuti.setEnabled(true);
                zeleni.setEnabled(false);
            }
        });

    }
}
