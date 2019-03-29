package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Rectangle extends Shape {

    protected RectF rect;

    public Rectangle(int startX, int startY, Paint paint) {
        super(startX, startY, paint);
        rect = new RectF();
    }

    @Override
    public void setEndPoint(int x, int y) {
        super.setEndPoint(x, y);
        int x1 = Math.min(startPoint.x, endPoint.x);
        int y1 = Math.min(startPoint.y, endPoint.y);
        int x2 = Math.max(startPoint.x, endPoint.x);
        int y2 = Math.max(startPoint.y, endPoint.y);
        rect.set(x1, y1, x2, y2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect, shapePaint);
    }
}
