package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;


public class Line extends Shape {

    public Line(int x, int y, Paint paint) {
        super(x, y, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, shapePaint);
    }
}
