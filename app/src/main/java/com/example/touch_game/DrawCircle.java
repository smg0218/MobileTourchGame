package com.example.touch_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class DrawCircle extends View {
    public static float x, y, radius;
    private Paint[] mForegrounds={
            //여기에 색깔 추가
            makePaint(Color.RED)
    };

    private static Random r = new Random();

    private Paint makePaint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return (p);
    }

    public DrawCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCircle(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        x = r.nextInt(width);
        y = r.nextInt(height);
        radius = r.nextInt(150 - 20 + 1) + 20; //원의 반지름, (최대크기 - 최소크기 + 최솟값) + 최소크기
        Paint circleColor=mForegrounds[r.nextInt(mForegrounds.length)];
        canvas.drawCircle(x,y,radius,circleColor);

    }
}
