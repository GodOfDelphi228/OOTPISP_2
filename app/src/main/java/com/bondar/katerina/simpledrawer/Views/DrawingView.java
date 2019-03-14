package com.bondar.katerina.simpledrawer.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bondar.katerina.simpledrawer.Shapes.Circle;
import com.bondar.katerina.simpledrawer.Shapes.Line;
import com.bondar.katerina.simpledrawer.Shapes.Oval;
import com.bondar.katerina.simpledrawer.Shapes.Polygon;
import com.bondar.katerina.simpledrawer.Shapes.Rectangle;
import com.bondar.katerina.simpledrawer.Shapes.RoundedRectangle;
import com.bondar.katerina.simpledrawer.Shapes.Shape;

import java.util.LinkedList;
import java.util.List;

public class DrawingView extends View {

    private static final String TAG = "DrawingView";
    private int currentShapeType;
    private Paint currentPaint;

    private List<Shape> shapesForDrawing;
    private Shape currentShape;

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        currentPaint = new Paint();
        currentPaint.setColor(Color.BLACK);
        currentPaint.setStyle(Paint.Style.FILL);
        shapesForDrawing = new LinkedList<>();
        currentShapeType = ShapeItem.SHAPE_CIRCLE;
    }

    public void setShapeDrawingColor(int color) {
        currentPaint.setColor(color);
    }

    public void setShapeDrawingStyle(int style) {
        if (style == PropertiesSelectionLayout.STYLE_FILLED)
            currentPaint.setStyle(Paint.Style.FILL);
        else
            currentPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Shape shape : shapesForDrawing) {
            shape.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentShape == null) {
                    currentShape = generateShape(x, y, currentShapeType);
                    shapesForDrawing.add(currentShape);
                    Log.d(TAG, "Shape added, shape count = " + shapesForDrawing.size());
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentShape != null) {
                    if (!(currentShape instanceof Polygon)){
                        currentShape.setEndPoint(x, y);
                    } else {
                        ((Polygon) currentShape).addPoint(x, y);
                    }
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (currentShape instanceof Polygon) {
                    ((Polygon)currentShape).addPoint(x, y);
                    invalidate();
                } else {
                    currentShape = null;
                }
                break;
        }
        return true;
    }

    private Shape generateShape(int x, int y, int shapeType) {
        Shape currentShape;
        switch (shapeType) {
            case ShapeItem.SHAPE_LINE:
                currentShape = new Line(x, y, currentPaint);
                break;

            case ShapeItem.SHAPE_RECTANGLE:
                currentShape = new Rectangle(x, y, currentPaint);
                break;

            case ShapeItem.SHAPE_ROUNDED_RECTANGLE:
                currentShape = new RoundedRectangle(x, y, 10, currentPaint);
                break;

            case ShapeItem.SHAPE_CIRCLE:
                currentShape = new Circle(x, y, currentPaint);
                break;

            case ShapeItem.SHAPE_OVAL:
                currentShape = new Oval(x, y, currentPaint);
                break;

            case ShapeItem.SHAPE_POLYGON:
                currentShape = new Polygon(x, y, currentPaint);
                break;

            default:
                currentShape = new Rectangle(x, y, currentPaint);
                break;
        }

        return currentShape;
    }

    public void confirmPolygon() {
        if (currentShape instanceof Polygon) {
            ((Polygon) currentShape).closePath();
        }
        currentShape = null;
        invalidate();
    }

    public Paint getCurrentPaint() {
        return currentPaint;
    }

    public void setShapeType(int shapeType) {
        currentShapeType = shapeType;
        currentShape = null;
    }

    public void clearView() {
        shapesForDrawing.clear();
        invalidate();
    }

    public void cancelLast() {
        int size = shapesForDrawing.size();
        if (size > 0) {
            shapesForDrawing.remove(size - 1);
            currentShape = null;
            invalidate();
        }
    }
}
