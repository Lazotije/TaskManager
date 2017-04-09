package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag" ;

    Zadatak z1= new Zadatak();
    Zadatak z2= new Zadatak();

    TaskAdapter adapter = new TaskAdapter(this);

    @Override
    protected void onResume() {
        super.onResume();
        ListView lv = (ListView) findViewById(R.id.lista);
        Log.d(TAG, "onResume: ");
        if(getIntent().getSerializableExtra("z3")!=null) {
            Zadatak z3 = new Zadatak();
            z3 = (Zadatak) getIntent().getSerializableExtra("z3");
            adapter.dodajZadatak(z3);
            lv.setAdapter(adapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        ListView lv = (ListView) findViewById(R.id.lista);



        Log.d(TAG, "onCreate: pre ubacivanja");
        z1.setIme("zad");
        z1.setDatum("25.01.995");
        z1.setPrioritet(3);
        z2.setOpis("opis drugog");
        z2.setVreme("16:23");
        z2.setPrioritet(1);
        adapter.dodajZadatak(z1);
        adapter.dodajZadatak(z2);

        //Zadatak z3 = (Zadatak) getIntent().getSerializableExtra("z3");
//        if(getIntent().getSerializableExtra("z3")!=null) {
//            Zadatak z3 = new Zadatak();
//            z3 = (Zadatak) getIntent().getSerializableExtra("z3");
//            adapter.dodajZadatak(z3);
//            lv.setAdapter(adapter);
//        }

        Log.d(TAG, "onCreate: posle ubacivanja");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,druga.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this,treca.class);
                startActivity(i2);
            }
        });
    }


}
