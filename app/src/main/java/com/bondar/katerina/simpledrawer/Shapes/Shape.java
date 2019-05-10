package com.bondar.katerina.simpledrawer.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.bondar.katerina.simpledrawer.Interfaces.IEditable;
import com.bondar.katerina.simpledrawer.Interfaces.ISelectable;

import java.io.Serializable;

public class Shape  implements IEditable, ISelectable {

    Point startPoint;
    Paint shapePaint;
    Point endPoint;

    Shape(int x, int y, Paint paint) {
        startPoint = new Point(x, y);
        shapePaint = new Paint(paint);
        endPoint = new Point(x, y);
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setShapeColor(int shapeColor) {
        shapePaint.setColor(shapeColor);
    }

    public void draw(Canvas canvas){}

    public void setEndPoint(int x, int y) {
        endPoint.set(x, y);
    }

    public void setShapePaint(Paint paint) {
        this.shapePaint = paint;
    }

    public Paint getShapePaint() {
        return shapePaint;
    }
}
