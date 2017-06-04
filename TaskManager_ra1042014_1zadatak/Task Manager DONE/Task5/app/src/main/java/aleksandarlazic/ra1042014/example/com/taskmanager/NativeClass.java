package aleksandarlazic.ra1042014.example.com.taskmanager;

/**
 * Created by lazic on 02-Jun-17.
 */

public class NativeClass {
    public native  float racunajProcente (float zavrsen,float ukupno);

    static {
        System.loadLibrary("Procenti");
    }
}
