package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag" ;
    TaskAdapter adapter = new TaskAdapter(this);
    public int brojZadataka=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        ListView lv = (ListView) findViewById(R.id.lista);



        Log.d(TAG, "onCreate: pre ubacivanja");

        if(getIntent().getSerializableExtra("z")!=null) {
           // brojZadataka++;
            Zadatak z = new Zadatak();
            z = (Zadatak) getIntent().getSerializableExtra("z");
            adapter.dodajZadatak(z);
            lv.setAdapter(adapter);
        }

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
