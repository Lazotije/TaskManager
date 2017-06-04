package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by lazic on 21-Apr-17.
 */

public class CustomView extends View {

    private int procenti1 = 0; //crveni
    private int procenti2 = 0; //zuti
    private int procenti3 = 0; //zeleni
    private Paint paint = new Paint();
    private Paint paint2 = new Paint();
    private Paint paint3 = new Paint();
    private Paint paint4 = new Paint();
    private Paint paint5 = new Paint();
    private Paint paint6 = new Paint();
    private RectF rect = new RectF();
    private RectF rect2 = new RectF();
    private RectF rect3 =new RectF();
    private float curr;
    private float curr2;
    private float curr3;
    private String TAG ="CUSTOM VIEW";




    public void startAnim(final float maxP){
        Log.d(TAG, "startAnim: usao u startANim");
        curr=0;
        procenti1 =0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: usao u run");
                while(curr<maxP){
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    procenti1 = (int) (curr*100)/360;
                    procenti1++;
                    curr++;
                }
            }
        }).start();
    }

    public void startAnim2(final float maxP){
        Log.d(TAG, "startAnim: usao u startANim");
        curr2=0;
        procenti2 =0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: usao u run");
                while(curr2<maxP){
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    procenti2 = (int) (curr2*100)/360;
                    procenti2++;
                    curr2++;
                }
            }
        }).start();
    }

    public void startAnim3(final float maxP){
        Log.d(TAG, "startAnim: usao u startANim");
        curr3=0;
        procenti3 =0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: usao u run");
                while(curr3<maxP){
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    procenti3 = (int) (curr3*100)/360;
                    procenti3++;
                    curr3++;
                }
            }
        }).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");

        int left = 0;
        int width = getWidth();
        int top = 0;
        int height = getHeight();

        paint.setColor(getContext().getResources().getColor(R.color.tirkiz));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        paint2.setColor(getContext().getResources().getColor(R.color.crvena));
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);

        paint3.setColor(getContext().getResources().getColor(R.color.zelena));
        paint3.setAntiAlias(true);
        paint3.setStyle(Paint.Style.FILL);

        paint4.setColor(getContext().getResources().getColor(R.color.zuta));
        paint4.setAntiAlias(true);
        paint4.setStyle(Paint.Style.FILL);

        paint5.setColor(getContext().getResources().getColor(R.color.crna));
        paint5.setAntiAlias(true);
        paint5.setStyle(Paint.Style.FILL);
        paint5.setTextSize(75);
        paint5.setTextAlign(Paint.Align.CENTER);

        paint6.setColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        paint6.setAntiAlias(true);
        paint6.setStyle(Paint.Style.FILL);
        paint6.setTextSize(35);
        paint6.setTextAlign(Paint.Align.CENTER);

        rect.set(left+3*width/8, top+height/8, left+5*width/8, top + 3*height/8);
        rect2.set(left+width/8, top+4*height/8, left+3*width/8, top + 6*height/8);
        rect3.set(left+5*width/8, top+4*height/8, left+7*width/8, top + 6*height/8);

        canvas.drawArc(rect, -90, 360, true, paint);
        canvas.drawText("Zadaci najviseg prioriteta",width/2,2*height/5,paint6);
        canvas.drawArc(rect2,-90,360,true,paint);
        canvas.drawText("Zadaci najnizeg prioriteta",width/4,4*height/5,paint6);
        canvas.drawArc(rect3,-90,360,true,paint);
        canvas.drawText("Zadaci srednjeg prioriteta",3*width/4,4*height/5,paint6);




        if(procenti1!=0) {
            canvas.drawArc(rect, -90, procenti1*360/100, true, paint2);
            canvas.drawText(String.valueOf((int) (procenti1))+"%", width/2, 27*height/100  , paint5);
        }else{
            canvas.drawText("0%", width/2, 27*height/100  , paint5);

        }

        if(procenti2!=0) {
            canvas.drawArc(rect3, -90, procenti2*360/100, true, paint4);
            canvas.drawText(String.valueOf((int) (procenti2))+"%", 3*width/4, 65*height/100  , paint5);
        }else{
            canvas.drawText("0%", 3*width/4, 65*height/100  , paint5);

        }

        if(procenti3!=0) {
            canvas.drawArc(rect2, -90, procenti3*360/100, true, paint3);
            canvas.drawText(String.valueOf((int) (procenti3))+"%", width/4, 65*height/100  , paint5);
        }else{
            canvas.drawText("0%", width/4, 65*height/100 , paint5);

        }
    }


    public CustomView(Context context) {
        super(context);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
