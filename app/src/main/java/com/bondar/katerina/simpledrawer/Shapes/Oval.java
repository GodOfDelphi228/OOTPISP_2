package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Oval extends Rectangle {

    public Oval(int startX, int startY, Paint paint) {
        super(startX, startY, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(rect, shapePaint);
    }
}
