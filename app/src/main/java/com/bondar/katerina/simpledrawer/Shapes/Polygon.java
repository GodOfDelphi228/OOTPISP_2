package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;


public class Polygon extends Shape {
    private Path polyPath;
    private Paint.Style style;

    public Polygon(int x, int y, Paint paint) {
        super(x, y, paint);
        style = paint.getStyle();
        shapePaint.setStyle(Paint.Style.STROKE);
        polyPath = new Path();
        polyPath.reset();
        polyPath.moveTo(x, y);
    }

    public void addPoint(int x, int y) {
        polyPath.lineTo(x, y);
    }

    public void closePath() {
        polyPath.close();
        shapePaint.setStyle(style);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(polyPath, shapePaint);
    }
}
