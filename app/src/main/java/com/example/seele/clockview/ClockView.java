package com.example.seele.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by CJJ on 2017/4/13.
 */

public class ClockView extends View {

    private static final String TAG = "ClockView";
    private Paint outerPaint;
    private Paint markerPaint;
    private Paint mPaintText;
    private Paint anchorPaint;
    private Paint mStickPaint;
    private Calendar calendar;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(4);
        outerPaint.setColor(Color.BLACK);

        anchorPaint = new Paint();
        anchorPaint.setStyle(Paint.Style.FILL);
        anchorPaint.setColor(Color.RED);

        markerPaint = new Paint();
        markerPaint.setStrokeWidth(2);
        markerPaint.setColor(Color.BLACK);

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setStrokeWidth(10);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(20);


        mStickPaint = new Paint();
        mStickPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mStickPaint.setStrokeWidth(10);
        mStickPaint.setColor(Color.BLACK);

        outerPaint.setAntiAlias(true);
        anchorPaint.setAntiAlias(true);
        markerPaint.setAntiAlias(true);
        mStickPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //draw the clock
        //make the center of this view the new pivot
        // noticed that all transform is applied to the coordination system rather than the canvas
        canvas.translate(width / 2, height / 2);
        float radius = width / 2 - outerPaint.getStrokeWidth() / 2;
        canvas.drawCircle(0, 0, radius, outerPaint);

        canvas.save();
//        canvas.drawCircle(0, 0, 5, anchorPaint);
        for (int i = 1; i <= 60; i++) {
            Log.i(TAG, "onDraw: " + i);
            canvas.rotate(360 / 60);
            if (i % 5 == 0) {
                //hour marker
                canvas.drawLine(0, 0 - radius, 0, 0 - radius + 15, markerPaint);
                canvas.save();
                canvas.rotate(-6 * i);
                int y = (int) ((radius - 35) * Math.sin((i * 6) * Math.PI / 180 - Math.PI / 2));
                int x = (int) ((radius - 35) * Math.cos((i * 6) * Math.PI / 180 - Math.PI / 2));
                canvas.drawText(i / 5 + "", x, y, mPaintText);
                canvas.restore();
            } else {
                canvas.drawLine(0, 0 - radius, 0, 0 - radius + 7, markerPaint);
            }

        }
        canvas.restore();

        //draw minute、second 、hour bar!
        calendar = Calendar.getInstance(Locale.CHINA);
        int hour = calendar.get(Calendar.HOUR);
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);

        Log.i(TAG, "onDraw: hour:" + hour + "---second:" + second + "---minute:" + minute);

        float hourDeg = (hour % 12) * 30 + minute / 60f * 30 + second / 60f / 60f * 30;
        float minuteDeg = minute * 6 + second / 60f * 6;
        float secDeg = second * 6;

        canvas.save();
        canvas.rotate(hourDeg);
        mStickPaint.setStrokeWidth(9);
        canvas.drawLine(0, 0 + radius * 0.12f, 0, 0 - getMeasuredWidth() / 2 * 0.6f, mStickPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(minuteDeg);
        mStickPaint.setStrokeWidth(5);
        canvas.drawLine(0, 0 + radius * 0.18f, 0, 0 - 0 - getMeasuredWidth() / 2 * 0.8f, mStickPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(secDeg);
        mStickPaint.setStrokeWidth(2);
        canvas.drawLine(0, 0 + radius * 0.25f, 0, 0 - getMeasuredWidth() / 2 * 0.93f, mStickPaint);
        canvas.restore();
        postInvalidateDelayed(500);

    }
}
