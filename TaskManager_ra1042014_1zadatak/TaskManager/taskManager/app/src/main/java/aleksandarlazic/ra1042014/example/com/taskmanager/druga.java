package aleksandarlazic.ra1042014.example.com.taskmanager;


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

    Button crveni;
    Button zuti;
    Button zeleni;
    Button dodaj;
    Button otkazi;
    EditText ime;
    EditText opis;
    DatePicker datePicker;
    TimePicker timePicker;
    CheckBox checkBox;
    int flag = 0;
    int flag2 = 0;
    int prioritet =0;
    public static String TAG="tag";
    int day;
    int month;
    int year;
    int hours;
    int minutes;
    public String datum;
    public String vreme;
    final TaskAdapter adapter = new TaskAdapter(this);
    ListView lw;

    public Zadatak napraviZadatak(){
        Zadatak z = new Zadatak(ime.getText().toString(),opis.getText().toString(),vratiPrioritet(),
                uzmiVreme(), uzmiDatum());
        Log.d(TAG, "napraviZadatak: "+z.getIme()+" "+z.getOpis()+" "+z.getPrioritet()+" "+
                z.getVreme()+" "+z.getDatum());
        return  z;
    }

    private int vratiPrioritet(){
        return prioritet;
    }

    private String uzmiVreme(){
        hours = timePicker.getCurrentHour();
        minutes =  timePicker.getCurrentMinute();
        vreme = Integer.toString(hours)+":"+Integer.toString(minutes);
        return vreme;
    }

    private String uzmiDatum(){
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() +1;
        year=datePicker.getYear();
        datum = Integer.toString(day)+"."+Integer.toString(month)+"."+Integer.toString(year);
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



        dodaj.setEnabled(false);

        ime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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
            public void afterTextChanged(Editable s) {

            }
        });

        opis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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
            public void afterTextChanged(Editable s) {

            }
        });


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "usao u dodaj: ");
                adapter.dodajZadatak(napraviZadatak());
                Intent i = new Intent(druga.this, MainActivity.class);
                i.putExtra("z3",napraviZadatak());
                startActivity(i);
            }
        });

        otkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(druga.this,MainActivity.class);
                startActivity(i2);
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
