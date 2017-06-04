package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.provider.Settings.Global.getString;
import static java.security.AccessController.getContext;

/**
 * Created by lazic on 08-Apr-17.
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<Zadatak> zadaci;
    private Calendar c;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat sdf;
    Date trenutno = new Date();
    Date zadatka = new Date();
    private String TAG="iz adaptera";
    int  danUNedelji;
    private DataBaseClass db ;


    public TaskAdapter(Context context) {
        this.context = context;
        db = new DataBaseClass(context);
        zadaci = new ArrayList<Zadatak>();
    }

    public static ArrayList<Zadatak>getZadaci(){
        return zadaci;
    }

    public void dodajZadatak(Zadatak z){
        zadaci.add(z);
        notifyDataSetChanged();
    }

    public void izbrisiZadatak(Zadatak z){
        zadaci.remove(z);
        notifyDataSetChanged();
    }

    public void azuriraj(Zadatak[] tasks)
    {
        zadaci.clear();
        if(tasks!=null)
        {
            for(Zadatak z : tasks)
            {
                zadaci.add(z);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return zadaci.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = zadaci.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
            view = inflater.inflate(R.layout.lista_zadataka, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.imeZadatka);
            viewHolder.pr = (TextView) view.findViewById(R.id.bojaPrioriteta);
            viewHolder.date = (TextView) view.findViewById(R.id.datum);
            viewHolder.time = (TextView) view.findViewById(R.id.vreme);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.podsetnik);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.zavrsen_zadatak);
            view.setTag(viewHolder);
        }

        final Zadatak z = (Zadatak) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(z.getIme());
        holder.time.setText(z.getVreme());

        if (z.getPrioritet() == 1) {
            holder.pr.setBackgroundColor(Color.GREEN);
        } else if (z.getPrioritet() == 2) {
            holder.pr.setBackgroundColor(Color.YELLOW);
        } else {
            holder.pr.setBackgroundColor(Color.RED);
        }

        if (z.isPodsetnik() == true) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }




        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    z.setZavrsen(true);
                    holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    db.updateZadatke(z,z.getIme(),z.getOpis(),z.getDatum(),z.getVreme(),true,z.isPodsetnik(),z.getPrioritet());
                    Log.d(TAG, "treba da je true: DA LI JE ZAVRSEN " +String.valueOf(z.isZavrsen()) );

                }else
                {
                    z.setZavrsen(false);
                    holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    db.updateZadatke(z,z.getIme(),z.getOpis(),z.getDatum(),z.getVreme(),false,z.isPodsetnik(),z.getPrioritet());
                    Log.d(TAG, "treba da je false: DA LI JE ZAVRSEN " +String.valueOf(z.isZavrsen()) );

                }
            }
        });

        holder.checkBox.setChecked(z.isZavrsen());



        c = Calendar.getInstance();
        danUNedelji = c.get(Calendar.DAY_OF_WEEK);

        if (danUNedelji==1){
            danUNedelji=7;
        }else if(danUNedelji == 2){
            danUNedelji=1;
        }
        else if(danUNedelji == 3){
            danUNedelji=2;
        }
        else if(danUNedelji == 4){
            danUNedelji=3;
        }
        else if(danUNedelji == 5){
            danUNedelji=4;
        }else if(danUNedelji == 6){
            danUNedelji=5;
        }
        else  if(danUNedelji == 7){
            danUNedelji=6;
        }



        Log.d(TAG, "Dan u nedelji: "+String.valueOf(danUNedelji));
        String trenutnoS = trenutno.toString();


        simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            trenutno = simpleDateFormat.parse(trenutnoS);
            Log.d(TAG, "TRENUTNO vreme: " + trenutno.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }


        sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            zadatka = sdf.parse(z.getDatum() + " " + z.getVreme());
            Log.d(TAG, "vreme ZADATKA: "+zadatka.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long diff = Math.abs(zadatka.getTime() - trenutno.getTime());
        long diffD = diff / (24 * 60 * 60 * 1000);
        Log.d(TAG, "RAZLIKAAAAAAAAAAAA: "+diffD);

        if (diffD == 0 && diffD < 1) {
            holder.date.setText(context.getApplicationContext().getString(R.string.Danas));
        } else if (diffD >= 1 && diffD < 2) {
            holder.date.setText(context.getApplicationContext().getString(R.string.Sutra));
        } else if (diffD >= 2 && diffD < 8) {
            switch ((int) diffD){
                case 2:
                    switch (danUNedelji){
                        case 1:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Sreda));
                            break;
                        case 2:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Cetvrtak));
                            break;
                        case 3:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Petak));
                            break;
                        case 4:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Subota));
                            break;
                        case 5:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Nedelja));
                            break;
                        case 6:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Ponedeljak));
                            break;
                        case 7:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Utorak));
                            break;
                    }
                    break;

                case 3:
                    switch (danUNedelji) {
                        case 7:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Sreda));
                            break;
                        case 1:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Cetvrtak));
                            break;
                        case 2:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Petak));
                            break;
                        case 3:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Subota));
                            break;
                        case 4:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Nedelja));
                            break;
                        case 5:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Ponedeljak));
                            break;
                        case 6:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Utorak));
                            break;
                    }

                    break;
                case 4:
                    switch (danUNedelji) {
                        case 6:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Sreda));
                            break;
                        case 7:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Cetvrtak));
                            break;
                        case 1:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Petak));
                            break;
                        case 2:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Subota));
                            break;
                        case 3:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Nedelja));
                            break;
                        case 4:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Ponedeljak));
                            break;
                        case 5:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Utorak));
                            break;
                    }
                    break;
                case 5:
                    switch (danUNedelji) {
                        case 5:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Sreda));
                            break;
                        case 6:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Cetvrtak));
                            break;
                        case 7:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Petak));
                            break;
                        case 1:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Subota));
                            break;
                        case 2:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Nedelja));
                            break;
                        case 3:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Ponedeljak));
                            break;
                        case 4:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Utorak));
                            break;
                    }

                    break;
                case 6:
                    switch (danUNedelji) {
                        case 4:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Sreda));
                            break;
                        case 5:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Cetvrtak));
                            break;
                        case 6:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Petak));
                            break;
                        case 7:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Subota));
                            break;
                        case 1:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Nedelja));
                            break;
                        case 2:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Ponedeljak));
                            break;
                        case 3:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Utorak));
                            break;
                    }

                    break;
                case 7:
                    switch (danUNedelji) {
                        case 3:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Sreda));
                            break;
                        case 4:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Cetvrtak));
                            break;
                        case 5:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Petak));
                            break;
                        case 6:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Subota));
                            break;
                        case 7:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Nedelja));
                            break;
                        case 1:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Ponedeljak));
                            break;
                        case 2:
                            holder.date.setText(context.getApplicationContext().getString(R.string.Utorak));
                            break;
                    }

                    break;
            }
        } else {
            holder.date.setText(z.getDatum());
        }

        return view;
    }



    private class ViewHolder{
        public TextView name=null;
        public TextView pr=null;
        public TextView date=null;
        public TextView time=null;
        public CheckBox checkBox = null;
        public RadioButton radioButton = null;
    }

}

