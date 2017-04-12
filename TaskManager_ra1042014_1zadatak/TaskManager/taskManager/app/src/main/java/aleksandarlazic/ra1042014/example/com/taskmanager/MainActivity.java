package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag" ;
    private  TaskAdapter adapter;
    private  Button b1;
    private  Button b2;
    private  ListView lv;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Zadatak z = (Zadatak) data.getSerializableExtra(getResources().getString(R.string.zadatak));
                adapter.dodajZadatak(z);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        adapter = new TaskAdapter(this);
        lv = (ListView) findViewById(R.id.lista);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i3 = new Intent(MainActivity.this,druga.class);
                Zadatak z = (Zadatak)lv.getItemAtPosition(position);
                i3.putExtra(getResources().getString(R.string.dugi_klik_intnent),z);
                startActivity(i3);
                return true;
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,druga.class);
                startActivityForResult(i,1);
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
