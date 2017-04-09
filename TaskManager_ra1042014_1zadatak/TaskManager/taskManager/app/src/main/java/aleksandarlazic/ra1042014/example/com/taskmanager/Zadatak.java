package aleksandarlazic.ra1042014.example.com.taskmanager;


import java.io.Serializable;

/**
 * Created by lazic on 08-Apr-17.
 */

public class Zadatak implements Serializable {
    private String ime;
    private String opis;
    private int prioritet;
    private String vreme;
    private String datum;

    public Zadatak(String ime, String opis, int prioritet, String vreme, String datum) {
        this.ime = ime;
        this.opis = opis;
        this.prioritet = prioritet;
        this.vreme = vreme;
        this.datum = datum;
    }

    public Zadatak() {
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getPrioritet() {
        return prioritet;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }


}
