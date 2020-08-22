package com.example.alarmz.stopwatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.alarmz.R;

public class MyCanvas extends View {

    public static Boolean start = false;
    public static long startTime = 0;
    public static long currentTime = 0;
    float canvasSize ;
    public static float secondX = 0F;
    public static float secondY = -160F;
    public static float minuteX = 0F;
    public static float minuteY = -30F;
    Paint circlePaint1 = new Paint();
    Paint circlePaint2 = new Paint();
    Paint linePaint2 = new Paint();
    Paint linePaint3 = new Paint();
    Paint whitePaint1 = new Paint();

    public MyCanvas(Context context) {
        super(context);

        init(null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        circlePaint1.setColor(getResources().getColor(R.color.circlePaint));
        circlePaint1.setStyle(Paint.Style.STROKE);
        circlePaint1.setStrokeWidth(10);
        circlePaint1.isAntiAlias();

        circlePaint2.setColor(getResources().getColor(R.color.circlePaint));
        circlePaint2.setStyle(Paint.Style.STROKE);
        circlePaint2.setStrokeWidth(15);
        circlePaint2.isAntiAlias();

        linePaint2.setColor(getResources().getColor(R.color.circlePaint));
        linePaint2.setStyle(Paint.Style.STROKE);
        linePaint2.setStrokeWidth(7);
        linePaint2.isAntiAlias();

        linePaint3.setColor(getResources().getColor(R.color.circlePaint));
        linePaint3.setStyle(Paint.Style.STROKE);
        linePaint3.setStrokeWidth(5);
        linePaint3.isAntiAlias();

        whitePaint1.setColor(getResources().getColor(R.color.circlePaint));
        whitePaint1.setStyle(Paint.Style.FILL);
        whitePaint1.isAntiAlias();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSize = canvas.getHeight();
        canvas.drawColor(getResources().getColor(R.color.canvasBg));
        //clock outline
        drawCircles(canvas);
        drawDivisions(canvas);
        drawSecondHand(canvas); // and minute hand
        canvas.drawCircle(canvasSize/2, canvasSize/2 , canvasSize*0.03F , whitePaint1);
        canvas.drawCircle(canvasSize/2, canvasSize/3 , canvasSize*0.01F , whitePaint1);
        //postInvalidate();
    }

    private void drawDivisions(Canvas canvas) {
        Float centre = canvasSize/2;
        Float centreMinute = canvasSize/3;
        double angleInRadians = 0;
        Float divX;
        Float divY;
        for(int i = 0; i<12; i++){
            divX = ((float) Math.cos(angleInRadians));
            divY = ((float) Math.sin(angleInRadians));
            canvas.drawLine( centre + (canvasSize*0.33F*divX), centre + (canvasSize*0.33F*divY),
                    centre + (canvasSize*0.4F*divX), centre + (canvasSize*0.4F*divY), linePaint2);
            canvas.drawLine(centre + (canvasSize*0.08F*divX), centreMinute + (canvasSize*0.08F*divY),
                    centre + (canvasSize*0.1F*divX), centreMinute + (canvasSize*0.1F*divY), linePaint3);
            angleInRadians += 3.14/30;
            for(int j=0; j<4; j++){
                divX = ((float) Math.cos(angleInRadians));
                divY = ((float) Math.sin(angleInRadians));
                canvas.drawLine( centre + (canvasSize*0.37F*divX), centre + (canvasSize*0.37F*divY),
                        centre + (canvasSize*0.4F*divX), centre + (canvasSize*0.4F*divY), linePaint3);
                angleInRadians += 3.14/30;
            }
        }
    }

    private void drawSecondHand(Canvas canvas) {
        Float centre = canvasSize/2;
        Float centreMinute = canvasSize/3;
        if(start){
            currentTime = System.currentTimeMillis();
            Long elapsedTime = currentTime - startTime;
            int secondsPassed = (int) (elapsedTime/1000);
            int angleInDegrees = (secondsPassed*6) - 90;
            int angleInDegreesMinute = ((secondsPassed*6)/60) - 90;
            double angleInRadians = (angleInDegrees*2*3.14)/360;
            double angleInRadiansMinute = (angleInDegreesMinute*2*3.14)/360;
            secondX = ((float) Math.cos(angleInRadians))*160F;
            secondY = ((float) Math.sin(angleInRadians))*160F;
            minuteX = ((float) Math.cos(angleInRadiansMinute))*30F;
            minuteY = ((float) Math.sin(angleInRadiansMinute))*30F;
            canvas.drawLine( centre, centre,  centre + secondX, centre + secondY, linePaint2);
            canvas.drawLine(centre, centreMinute, centre + minuteX, centreMinute + minuteY, linePaint2);
        }else {
            canvas.drawLine(centre, centre, centre + secondX, centre + secondY, linePaint2);
            canvas.drawLine(centre, centreMinute, centre + minuteX, centreMinute + minuteY, linePaint2);
        }
        postInvalidate();
    }

    private void drawCircles(Canvas canvas) {
        canvas.drawCircle(canvasSize/2, canvasSize/2 , canvasSize*0.4F , circlePaint2);
        canvas.drawCircle(canvasSize/2, canvasSize/3 , canvasSize*0.1F , circlePaint1);
    }

}
