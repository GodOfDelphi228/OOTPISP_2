package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Triangle extends Shape {

    private Point secondPoint;

    public Triangle(int x, int y, Paint paint) {
        super(x, y, paint);
    }

    @Override
    public Point getStartPoint() {
        return super.getStartPoint();
    }

    @Override
    public void setStartPoint(Point startPoint) {
        super.setStartPoint(startPoint);
    }

    @Override
    public void setShapeColor(int shapeColor) {
        super.setShapeColor(shapeColor);
    }

    @Override
    public void draw(Canvas canvas) {
        Point a = startPoint;
        Point b = secondPoint;
        Point c = endPoint;
        if (a != null && b != null && c != null) {
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(a.x, a.y);
            path.lineTo(b.x, b.y);
            path.lineTo(c.x, c.y);
            path.lineTo(a.x, a.y);
            path.close();
            canvas.drawPath(path, shapePaint);
        }

    }

    public void setSecondPoint(int x, int y) {
        secondPoint = new Point(x, y);
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    @Override
    public void setEndPoint(int x, int y) {
        super.setEndPoint(x, y);
    }

}
