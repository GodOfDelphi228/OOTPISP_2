package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class Shape {

    Point startPoint;
    Paint shapePaint; //кисточка
    Point endPoint;

    Shape(int x, int y, Paint paint) {
        startPoint = new Point(x, y);
        shapePaint = new Paint(paint);
        endPoint = new Point(x, y);
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setShapeColor(int shapeColor) {
        shapePaint.setColor(shapeColor);
    }

    public abstract void draw(Canvas canvas);

    public void setEndPoint(int x, int y) {
        endPoint.set(x, y);
    }

    public void setShapePaintStyle(Paint.Style style) {
        shapePaint.setStyle(style);
    }
}
