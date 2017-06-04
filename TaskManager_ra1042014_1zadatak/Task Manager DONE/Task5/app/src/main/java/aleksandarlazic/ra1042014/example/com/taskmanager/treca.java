package aleksandarlazic.ra1042014.example.com.taskmanager;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;




public class treca  extends AppCompatActivity  {

    private Button back;
    private CustomView cv;
    private float p1=0;                     //najvisi
    private float p2=0;                      //srednji
    private float p3=0;                      //najnizi
    private float brojac1=0;
    private float brojac2=0;
    private float brojac3=0;
    private float ugao;
    private float ugao2;
    private float ugao3;
    private float crveniDone=0;
    private float zutiDone=0;
    private float zeleniDone=0;
    private DataBaseClass db = new DataBaseClass(treca.this);
    public static String TAG="treca";
    private NativeClass nativeClass;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treca);

        back = (Button)findViewById(R.id.nazad);
        cv = (CustomView)findViewById(R.id.chart);
        nativeClass = new NativeClass();

        Zadatak[]zadataks = db.prodjiKrozZadatke();
        if(zadataks == null){
            Log.d(TAG, "VRACA NULLLL KROZ ZADATKE: ");
            brojac1=0;
            brojac2=0;
            brojac3=0;
            p1=0;
            p2=0;
            p3=0;
            crveniDone=0;
            zutiDone =0;
            zeleniDone=0;
        }
        else
        {
            for(int i=0;i<zadataks.length;i++)
            {
//                if(zadataks[i].getPrioritet()==1)
//                {
//                    brojac3++;
//                }
//                else if (zadataks[i].getPrioritet()==2)
//                {
//                    brojac2++;
//                }
//                else
//                {
//                    brojac1++;
//                }

                Log.d(TAG, "IPAK JE USAO U ZADATKEEEE: ");
                Log.d(TAG, "provera IF da li je zavrsen: "+ String.valueOf(zadataks[i].isZavrsen()));
                Log.d(TAG, "provera IF priorite: " + String.valueOf(zadataks[i].getPrioritet()));

                if(zadataks[i].isZavrsen() && zadataks[i].getPrioritet()==3)
                {
                    crveniDone++;
                    Log.d(TAG, "crveni done: " + crveniDone);
                    Log.d(TAG, "crveni zavrsen: "+String.valueOf(zadataks[i].isZavrsen()));
                }
                if(zadataks[i].isZavrsen() && zadataks[i].getPrioritet()==2)
                {
                    zutiDone++;
                    Log.d(TAG, "zuti done: " + zutiDone);
                    Log.d(TAG, "zuti zavrsen: "+String.valueOf(zadataks[i].isZavrsen()));

                }
                if(zadataks[i].isZavrsen() && zadataks[i].getPrioritet()==1)
                {
                    zeleniDone++;
                    Log.d(TAG, "zeleni done: " + zeleniDone);
                    Log.d(TAG, "zeleni zavrsen: "+String.valueOf(zadataks[i].isZavrsen()));

                }
            }

//            p1 = (float) Math.floor ((brojac1 / zadataks.length) * 100); //crveni
//            p2 = (float) Math.floor ((brojac2 / zadataks.length) * 100); //zuti
//            p3 = (float) Math.floor ((brojac3 / zadataks.length) * 100); //zeleni

            p1 = (float) Math.floor ( nativeClass.racunajProcente(crveniDone,zadataks.length));
            p2 = (float) Math.floor ( nativeClass.racunajProcente(zutiDone,zadataks.length));
            p3 = (float) Math.floor ( nativeClass.racunajProcente(zeleniDone,zadataks.length));


        }

        ugao  = (int)  (p1*360)/100; //crveni
        ugao2 = (int) Math.ceil(p2*360)/100; //zuti
        ugao3 = (int) Math.ceil(p3*360)/100; //zeleni
        Log.d(TAG, "P3: "+String.valueOf(p1));
        Log.d(TAG, "P2: "+String.valueOf(p2));
        Log.d(TAG, "P1: "+String.valueOf(p3));


        cv.startAnim(ugao); //crvena
        cv.startAnim2(ugao2); //zuta
        cv.startAnim3(ugao3); //zelena


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });


    }



}

