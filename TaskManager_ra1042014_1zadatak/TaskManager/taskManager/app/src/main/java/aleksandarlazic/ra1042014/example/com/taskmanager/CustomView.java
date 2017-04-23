package aleksandarlazic.ra1042014.example.com.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by lazic on 21-Apr-17.
 */

public class CustomView extends View {

    private float procenat = 0;
    private float procenat2 =0;
    private float procenat3 =0;
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




    public void startAnim(final float maxP){
        curr=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(curr<maxP){
                    invalidate();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                curr++;
            }
        }).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = 0;
        int width = getWidth();
        int top = 0;
        int height = getHeight();
        setProcenat(35);
        setProcenat2(59);
        setProcenat3(13);

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

        if(procenat!=0) {
            canvas.drawArc(rect, -90, (360*procenat), true, paint2);
            canvas.drawText(String.valueOf((int) (procenat*100))+"%", width/2, 27*height/100  , paint5);
        }else{
            canvas.drawText("0%", width/2, height/4  , paint5);
        }
        if(procenat2!=0) {
            canvas.drawArc(rect2, -90, (360*procenat2), true, paint3);
            canvas.drawText(String.valueOf((int) (procenat2*100))+"%", width/4, 65*height/100  , paint5);
        }else{
            canvas.drawText("0%", width/2, height/4  , paint5);
        }
        if(procenat3!=0) {
            canvas.drawArc(rect3, -90, (360*procenat3), true, paint4);
            canvas.drawText(String.valueOf((int) (procenat3*100))+"%", 3*width/4, 65*height/100  , paint5);
        }else{
            canvas.drawText("0%", width/2, height/4  , paint5);
        }

    }


    public void setProcenat(float p){
        this.procenat = p / 100;
        invalidate();
    }

    public void setProcenat2(float p){
        this.procenat2 = p / 100;
        invalidate();
    }

    public void setProcenat3(float p){
        this.procenat3 = p / 100;
        invalidate();
    }

    public float getProcenat() {
        return procenat;
    }

    public float getProcenat2() {
        return procenat2;
    }

    public float getProcenat3() {
        return procenat3;
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
