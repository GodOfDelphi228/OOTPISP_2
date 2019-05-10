package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;


public class RoundedRectangle extends Rectangle {

    private float radius;

    public RoundedRectangle(int startX, int startY, float radius, Paint paint) {
        super(startX, startY, paint);
        this.radius = radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rect, radius, radius, shapePaint);
    }


}
