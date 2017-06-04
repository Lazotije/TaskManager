package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import aleksandarlazic.ra1042014.example.com.taskmanager.MyService.LocalBinder;

import java.util.ArrayList;

import static android.R.attr.id;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag" ;
    private static final int REQUEST_CODE_LIST = 2;
    private static final int REQUEST_CODE_UPDATE = 4;
    private static final int RESULT_OK_UPDATE = 5;
    private static final int RESULT_OK_DELETE = 6;
    private static TaskAdapter adapter;
    private  Button b1;
    private  Button b2;
    private  ListView lv;
    MyService myService;
    DataBaseClass db;


    public static TaskAdapter getTaskAdapter() {
        return adapter;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBinder localBinder = (LocalBinder) service;
            myService = localBinder.getService();
            localBinder.getZadaci(adapter.getZadaci());
            localBinder.setCallbackDone(new IMyCallbacklInterface() {
                @Override
                public void onTimeIstek(String ime) throws RemoteException {

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this).setContentTitle("Task manager").setSmallIcon(R.mipmap.app).setTicker("OBAVESTENJE")
                            .setContentText(ime + " istice u narednih 15 min");
                    notificationManager.notify(id, builder.build());
                    //z.setZavrsen(false);


                }

                @Override
                public IBinder asBinder() {
                    return null;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LIST){
            if(resultCode == Activity.RESULT_OK){
                adapter.notifyDataSetChanged();
            }
        }else if(requestCode==REQUEST_CODE_UPDATE){
            if(resultCode==RESULT_OK_UPDATE){

            }
        }
        else if(requestCode==REQUEST_CODE_UPDATE){
            if(resultCode==RESULT_OK_DELETE){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
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
        db = new DataBaseClass(this);

        Zadatak[]zadataks = db.prodjiKrozZadatke();
        if (zadataks!=null)
        {
            adapter.azuriraj(zadataks);
        }


        Intent i = new Intent(this,MyService.class);
        bindService(i,connection,BIND_AUTO_CREATE);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i3 = new Intent(MainActivity.this,druga.class);
                Zadatak z = (Zadatak)lv.getItemAtPosition(position);
                i3.putExtra(getResources().getString(R.string.dugi_klik_intnent),z);
                startActivityForResult(i3,REQUEST_CODE_UPDATE);
                return true;
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,druga.class);
                startActivityForResult(i,REQUEST_CODE_LIST);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this,treca.class);
                startActivityForResult(i2,REQUEST_CODE_LIST);

            }
        });

    }


}
