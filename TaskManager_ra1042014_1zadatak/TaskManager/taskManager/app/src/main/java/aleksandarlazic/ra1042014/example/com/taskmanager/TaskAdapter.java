package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lazic on 08-Apr-17.
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Zadatak> zadaci;

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
        TextView name;
        TextView desc;
        TextView pr;
        TextView date;
        TextView time;

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
            view=inflater.inflate(R.layout.lista_zadataka,null);
        }
        name = (TextView) view.findViewById(R.id.tekstView);
        desc = (TextView) view.findViewById(R.id.tekstView2);
        pr = (TextView) view.findViewById(R.id.tekstView3);
        date = (TextView) view.findViewById(R.id.tekstView4);
        time = (TextView) view.findViewById(R.id.tekstView5);
        Zadatak z = (Zadatak)getItem(position);
        name.setText(z.getIme());
        desc.setText(z.getOpis());
        pr.setText( String.valueOf(z.getPrioritet()));
        date.setText(z.getDatum());
        time.setText(z.getVreme());

        return view;
    }
}
