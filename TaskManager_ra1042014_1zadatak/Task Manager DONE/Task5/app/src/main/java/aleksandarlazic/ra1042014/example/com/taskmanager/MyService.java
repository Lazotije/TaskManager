package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by lazic on 17-May-17.
 */

public class MyService extends Service {

    private ArrayList<Zadatak> zadaci;
    private final IBinder binder = new LocalBinder();
    public String TAG = "iz sevisa";
    SimpleDateFormat sdf1;
    SimpleDateFormat sdf2;
    Date datumZadatka = new Date();
    private int id = 0;
    NotificationManager notificationManager = null;
    NotificationCompat.Builder builder = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }


    public MyService() {
//        final Handler handler = new Handler();
//        final Runnable thread = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    zadaci = TaskAdapter.getZadaci();
//                    Date trenutanDatum = new Date();
//                    for (Zadatak z : zadaci) {
//                        if (z.isPodsetnik()) {
//                            String trenutnoS = trenutanDatum.toString();
//                            sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
//                            try {
//                                trenutanDatum = sdf1.parse(trenutnoS);
//                                Log.d(TAG, "TRENUTNO vreme: " + trenutanDatum.toString());
//
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            sdf2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//                            try {
//                                datumZadatka = sdf2.parse(z.getDatum() + " " + z.getVreme());
//                                Log.d(TAG, "vreme ZADATKA: " + datumZadatka.toString());
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            long diff = Math.abs(datumZadatka.getTime() - trenutanDatum.getTime());
//                            if (diff <= 1000 * 60 * 15) {
//                                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                                builder = new NotificationCompat.Builder(MyService.this).setContentTitle("Task manager").setSmallIcon(R.mipmap.app).setTicker("OBAVESTENJE")
//                                        .setContentText(z.getIme() + " istice u narednih 15 min");
//                                notificationManager.notify(id, builder.build());
//                                z.setZavrsen(false);
//
//                            }
//
//                        }
//                    }
//                } finally {
//                    handler.postDelayed(this, 1000);
//                }
//            }
//        };
//        thread.run();
    }

    public class LocalBinder extends Binder {

        IMyCallbacklInterface mCallback;
        CallbackCaller mCaller;

        MyService getService() {
            return MyService.this;
        }

        void getZadaci(ArrayList<Zadatak> z) {
            zadaci = z;
        }


        public void setCallbackDone(IMyCallbacklInterface callback) {
            mCallback = callback;
            mCaller = new CallbackCaller();
            mCaller.start();
        }

        public void stop() {
            mCaller.stop();
        }


        private class CallbackCaller implements Runnable {

            private static final long PERIOD = 1000L;

            private Handler mHandler = null;
            private boolean mRun = true;

            public void start() {
                mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(this, PERIOD);
            }

            public void stop() {
                mRun = false;
                mHandler.removeCallbacks(this);
            }

            @Override
            public void run() {
                if (!mRun) {
                    return;
                }
                try {

                    zadaci = TaskAdapter.getZadaci();
                    Date trenutanDatum = new Date();
                    for (Zadatak z : zadaci) {
                        if (z.isPodsetnik()) {
                            String trenutnoS = trenutanDatum.toString();
                            sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                            try {
                                trenutanDatum = sdf1.parse(trenutnoS);
                                Log.d(TAG, "TRENUTNO vreme: " + trenutanDatum.toString());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            sdf2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                            try {
                                datumZadatka = sdf2.parse(z.getDatum() + " " + z.getVreme());
                                Log.d(TAG, "vreme ZADATKA: " + datumZadatka.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            long diff = Math.abs(datumZadatka.getTime() - trenutanDatum.getTime());
                            if (diff <= 1000 * 60 * 15) {
                                try {
                                    mCallback.onTimeIstek(z.getIme());
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }

                            }


                            mHandler.postDelayed(this, 10000);
                            Log.d(TAG, "ISPISISSSSS: "+trenutanDatum.toString() + " "+ datumZadatka.toString());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        }
    }
}
