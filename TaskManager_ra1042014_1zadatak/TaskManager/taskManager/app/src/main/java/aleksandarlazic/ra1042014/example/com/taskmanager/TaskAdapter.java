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


    public TaskAdapter(Context context) {
        this.context = context;
        zadaci = new ArrayList<Zadatak>();

    }

    public void dodajZadatak(Zadatak z){
        zadaci.add(z);
        notifyDataSetChanged();
    }

    public void izbrisiZadatak(Zadatak z){
        zadaci.remove(z);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
            view=inflater.inflate(R.layout.lista_zadataka,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView)view.findViewById(R.id.imeZadatka);
            viewHolder.pr = (TextView)view.findViewById(R.id.bojaPrioriteta);
            viewHolder.date = (TextView)view.findViewById(R.id.datum);
            viewHolder.time = (TextView)view.findViewById(R.id.vreme);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.podsetnik);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.zavrsen_zadatak);
            view.setTag(viewHolder);
        }

        Zadatak z = (Zadatak)getItem(position);
        final ViewHolder holder= (ViewHolder) view.getTag();
        holder.name.setText(z.getIme());
        holder.time.setText(z.getVreme());

        if(z.getPrioritet() == 1){
            holder.pr.setBackgroundColor(Color.GREEN);
        }else if(z.getPrioritet()==2){
            holder.pr.setBackgroundColor(Color.YELLOW);
        }else{
            holder.pr.setBackgroundColor(Color.RED);
        }

        if(z.isPodsetnik()== true) {
            holder.radioButton.setChecked(true);
        }else{
            holder.radioButton.setChecked(false);
        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        if(holder.checkBox.isChecked()){
            z.setZavrsen(true);
        }else{
            z.setZavrsen(false);
        }




        c = Calendar.getInstance();
        danUNedelji=c.get(Calendar.DAY_OF_WEEK)-1;
        String trenutnoS = trenutno.toString();


        simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            trenutno = simpleDateFormat.parse(trenutnoS);
            Log.d(TAG, "TRENUTNO vreme: "+ trenutno.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }



        sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            zadatka = sdf.parse(z.getDatum()+" "+z.getVreme());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long diff = Math.abs(zadatka.getTime()-trenutno.getTime());
        long diffD = diff / (24 * 60 * 60 * 1000);


        if(diffD == 0 && diffD < 1){
            holder.date.setText("Danas");
        }else if(diffD >= 1 && diffD < 2) {
            holder.date.setText("Sutra");
        }else if(diffD>=2 && diffD<8){
            switch ((int)diffD){
                case 2:
                    switch (danUNedelji){
                        case 1:
                            holder.date.setText("Sreda");break;
                        case 2:
                            holder.date.setText("Cetvrtak");break;
                        case 3:
                            holder.date.setText("Petak");break;
                        case 4:
                            holder.date.setText("Subota");break;
                        case 5:
                            holder.date.setText("Nedelja");break;
                        case 6:
                            holder.date.setText("Ponedeljak");break;
                        case 7:
                            holder.date.setText("Utorak");break;
                    }

                    break;
                case 3:
                    switch (danUNedelji){
                        case 7:
                            holder.date.setText("Sreda");break;
                        case 1:
                            holder.date.setText("Cetvrtak");break;
                        case 2:
                            holder.date.setText("Petak");break;
                        case 3:
                            holder.date.setText("Subota");break;
                        case 4:
                            holder.date.setText("Nedelja");break;
                        case 5:
                            holder.date.setText("Ponedeljak");break;
                        case 6:
                            holder.date.setText("Utorak");break;
                    }

                    break;
                case 4:
                    switch (danUNedelji){
                        case 6:
                            holder.date.setText("Sreda");break;
                        case 7:
                            holder.date.setText("Cetvrtak");break;
                        case 1:
                            holder.date.setText("Petak");break;
                        case 2:
                            holder.date.setText("Subota");break;
                        case 3:
                            holder.date.setText("Nedelja");break;
                        case 4:
                            holder.date.setText("Ponedeljak");break;
                        case 5:
                            holder.date.setText("Utorak");break;
                    }
                    break;
                case 5:
                    switch (danUNedelji){
                        case 5:
                            holder.date.setText("Sreda");break;
                        case 6:
                            holder.date.setText("Cetvrtak");break;
                        case 7:
                            holder.date.setText("Petak");break;
                        case 1:
                            holder.date.setText("Subota");break;
                        case 2:
                            holder.date.setText("Nedelja");break;
                        case 3:
                            holder.date.setText("Ponedeljak");break;
                        case 4:
                            holder.date.setText("Utorak");break;
                    }

                    break;
                case 6:
                    switch (danUNedelji){
                        case 4:
                            holder.date.setText("Sreda");break;
                        case 5:
                            holder.date.setText("Cetvrtak");break;
                        case 6:
                            holder.date.setText("Petak");break;
                        case 7:
                            holder.date.setText("Subota");break;
                        case 1:
                            holder.date.setText("Nedelja");break;
                        case 2:
                            holder.date.setText("Ponedeljak");break;
                        case 3:
                            holder.date.setText("Utorak");break;
                    }

                    break;
                case 7:
                    holder.date.setText("proba");
                    switch (danUNedelji){
                        case 3:
                            holder.date.setText("Sreda");break;
                        case 4:
                            holder.date.setText("Cetvrtak");break;
                        case 5:
                            holder.date.setText("Petak");break;
                        case 6:
                            holder.date.setText("Subota");break;
                        case 7:
                            holder.date.setText("Nedelja");break;
                        case 1:
                            holder.date.setText("Ponedeljak");break;
                        case 2:
                            holder.date.setText("Utorak");break;
                    }

                    break;
            }
        }
       else{
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

