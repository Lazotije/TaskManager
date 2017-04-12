package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.app.TimePickerDialog;
import android.content.Context;
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

/**
 * Created by lazic on 08-Apr-17.
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<Zadatak> zadaci;
    private Calendar c;
    private Calendar c2;
    private Calendar c3;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat sdf;
    private String TAG="iz adaptera";
    int min,sat,dan,mesec,godina;
    int parsDan,parsMesec,parsGodina;
    int parsSat,parsMinut;


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


//        parsiranje zbog poredjenja

        simpleDateFormat = new SimpleDateFormat("dd.mm.yyyy");
        try {
            Date date = simpleDateFormat.parse(z.getDatum());
            String formated1 = new SimpleDateFormat("mm").format(date);
            String formated2 = new SimpleDateFormat("dd").format(date);
            String formated3 = new SimpleDateFormat("yyyy").format(date);
            parsMesec=Integer.parseInt(formated1);
            parsDan=Integer.parseInt(formated2);
            parsGodina=Integer.parseInt(formated3);
            Log.d(TAG, "getView: "+Integer.toString(parsDan));
            Log.d(TAG, "getView: "+Integer.toString(parsMesec));
            Log.d(TAG, "getView: "+Integer.toString(parsGodina));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //ako zatreba za naredne zadatke
        String[] split = z.getVreme().split(":");
        parsSat=Integer.valueOf(split[0]);
        Log.d(TAG, "getView: "+ Integer.toString(parsSat));
        parsMinut=Integer.valueOf(split[1]);
        Log.d(TAG, "getView: "+ Integer.toString(parsMinut));



        Calendar c4= Calendar.getInstance();
        c4.set(Calendar.YEAR,parsGodina);
        c4.set(Calendar.MONTH,parsMesec);
        c4.set(Calendar.DAY_OF_YEAR,parsDan);
        Log.d(TAG, "Calendar: "+c4.toString());

        c = Calendar.getInstance();
        c3 = Calendar.getInstance();
        c3.add(Calendar.DAY_OF_YEAR,7);
        c3.add(Calendar.MONTH,1);
        Log.d(TAG, "C3: "+c3.toString());
        min= c.get(Calendar.MINUTE);
        sat=c.get(Calendar.HOUR);
        dan=c.get(Calendar.DAY_OF_MONTH);
        mesec=c.get(Calendar.MONTH)+1;
        godina=c.get(Calendar.YEAR);



        if(dan == parsDan && mesec == parsMesec && godina==parsGodina){
            holder.date.setText("Danas");
        }else if(parsDan == dan+1 && mesec == parsMesec && godina == parsGodina){
            holder.date.setText("Sutra");
        }else if(c4.get(Calendar.MONTH) < c3.get(Calendar.MONTH)){
            switch (c4.get(Calendar.DAY_OF_WEEK)){
                case Calendar.MONDAY:
                    holder.date.setText("Ponedeljak");
                    break;
                case Calendar.TUESDAY:
                    holder.date.setText("Utorak");
                    break;
                case Calendar.WEDNESDAY:
                    holder.date.setText("Sreda");
                    break;
                case Calendar.THURSDAY:
                    holder.date.setText("Cetvrtak");
                    break;
                case Calendar.FRIDAY:
                    holder.date.setText("Petak");
                    break;
                case Calendar.SATURDAY:
                    holder.date.setText("Subota");
                    break;
                case Calendar.SUNDAY:
                    holder.date.setText("Nedelja");
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

