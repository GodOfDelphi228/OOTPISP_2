package com.bondar.katerina.simpledrawer.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bondar.katerina.simpledrawer.Interfaces.IEditable;
import com.bondar.katerina.simpledrawer.Interfaces.ISelectable;
import com.bondar.katerina.simpledrawer.Shapes.Circle;
import com.bondar.katerina.simpledrawer.Shapes.Line;
import com.bondar.katerina.simpledrawer.Shapes.Oval;
import com.bondar.katerina.simpledrawer.Shapes.Polygon;
import com.bondar.katerina.simpledrawer.Shapes.Rectangle;
import com.bondar.katerina.simpledrawer.Shapes.RoundedRectangle;
import com.bondar.katerina.simpledrawer.Shapes.Shape;
import com.bondar.katerina.simpledrawer.Shapes.Triangle;
import java.util.LinkedList;
import java.util.List;

public class DrawingView extends View {

    private static final String TAG = "DrawingView";
    private int currentShapeType;
    private Paint currentPaint;
    private Paint highlightPaint1;
    private Paint highlightPaint2;
    private List<Shape> shapesForDrawing;
    private List<Shape> shapesForForward;
    private Shape currentShape;
    private Shape shapeInEdit;
    private int triangleCounter = 0;
    private int editType = 0;

    private int startX = 0;
    private int startY = 0;

    private int startShapeX = 0;
    private int startShapeY = 0;

    private int endShapeX = 0;
    private int endShapeY = 0;

    private int secondShapeX = 0;
    private int secondShapeY = 0;

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
        highlightPaint1 = new Paint();
        highlightPaint1.setColor(Color.RED);
        highlightPaint1.setStyle(Paint.Style.STROKE);
        highlightPaint1.setStrokeWidth(10f);
        highlightPaint2 = new Paint();
        highlightPaint2.setColor(Color.GREEN);
        highlightPaint2.setStyle(Paint.Style.STROKE);
        highlightPaint2.setStrokeWidth(10f);
        currentPaint.setStyle(Paint.Style.FILL);
        shapesForDrawing = new LinkedList<>();
        shapesForForward = new LinkedList<>();
        currentShapeType = ShapeItem.SHAPE_CIRCLE;
    }

    public void setEditType(int editType) {
        this.editType = editType;
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
        if (shapeInEdit != null) {
            if (editType == 1) {
                if (shapeInEdit instanceof ISelectable) {
                    Paint temp = shapeInEdit.getShapePaint();
                    shapeInEdit.setShapePaint(highlightPaint1);
                    shapeInEdit.draw(canvas);
                    shapeInEdit.setShapePaint(temp);
                }
            } else if (editType == 2) {
                if (shapeInEdit instanceof IEditable) {
                    Paint temp = shapeInEdit.getShapePaint();
                    shapeInEdit.setShapePaint(highlightPaint2);
                    shapeInEdit.draw(canvas);
                    shapeInEdit.setShapePaint(temp);
                }
            }
        }
        for (Shape shape: shapesForDrawing) {
            shape.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (editType == 1 || editType == 2) {
                    startX = x;
                    startY = y;
                    shapeInEdit = getShapeByCoordinates(x, y);
                    if (shapeInEdit != null) {
                        startShapeX = shapeInEdit.getStartPoint().x;
                        startShapeY = shapeInEdit.getStartPoint().y;
                        endShapeX = shapeInEdit.getEndPoint().x;
                        endShapeY = shapeInEdit.getEndPoint().y;
                        if (shapeInEdit instanceof Triangle) {
                            Triangle triangle = (Triangle)shapeInEdit;
                            secondShapeX = triangle.getSecondPoint().x;
                            secondShapeY = triangle.getSecondPoint().y;
                        }
                    }
                    invalidate();
                } else {
                    if (currentShape == null) {
                        currentShape = generateShape(x, y, currentShapeType);
                        shapesForDrawing.add(currentShape);
                        Log.d(TAG, "Shape added, shape count = " + shapesForDrawing.size());
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (shapeInEdit != null) {
                    if (editType == 1 && shapeInEdit instanceof ISelectable) {
                        int dx = x - startX;
                        int dy = y - startY;
                        shapeInEdit.setStartPoint(new Point(startShapeX + dx, startShapeY + dy));
                        shapeInEdit.setEndPoint(endShapeX + dx, endShapeY + dy);
                        if (shapeInEdit instanceof Triangle) {
                            Triangle triangle = (Triangle) shapeInEdit;
                            triangle.setSecondPoint(secondShapeX + dx, secondShapeY + dy);
                        }
                        invalidate();
                    } else if (editType == 2  && shapeInEdit instanceof IEditable) {
                        if (shapeInEdit instanceof Triangle) {
                            Triangle triangle = (Triangle) shapeInEdit;
                            if (calcDis(triangle.getStartPoint().x, triangle.getStartPoint().y, x, y) < 100) {
                                triangle.setStartPoint(new Point(x, y));
                            } else if (calcDis(triangle.getSecondPoint().x, triangle.getSecondPoint().y, x, y) < 100) {
                                triangle.setSecondPoint(x, y);
                            } else if (calcDis(triangle.getEndPoint().x, triangle.getEndPoint().y, x, y) < 100) {
                                triangle.setEndPoint(x, y);
                            }
                            shapeInEdit = triangle;
                        } else {
                            shapeInEdit.setEndPoint(x, y);
                            shapeInEdit.setShapePaint(currentPaint);
                        }
                        Paint newPaint = new Paint();
                        newPaint.setColor(currentPaint.getColor());
                        shapeInEdit.setShapePaint(newPaint);
                        invalidate();
                    }
                } else {
                    if (currentShape != null) {
                        if (!(currentShape instanceof Polygon)) {
                            currentShape.setEndPoint(x, y);
                        } else {
                            ((Polygon) currentShape).addPoint(x, y);
                        }
                        invalidate();
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (currentShape instanceof Triangle)  {
                    Triangle current = (Triangle) currentShape;
                    if (triangleCounter == 1) {
                        current.setSecondPoint(x, y);
                        triangleCounter++;
                    } else if (triangleCounter == 2) {
                        current.setEndPoint(x, y);
                        currentShape = null;
                        invalidate();
                        triangleCounter = 0;
                    } else {
                        triangleCounter++;
                    }

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

            case ShapeItem.SHAPE_TRIANGLE:
                currentShape = new Triangle(x, y, currentPaint);
                break;

            default:
                currentShape = new Rectangle(x, y, currentPaint);
                break;
        }
        shapesForForward.clear();
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
            shapesForForward.add(shapesForDrawing.get(size - 1));
            shapesForDrawing.remove(size - 1);
            currentShape = null;
            invalidate();
        }
    }

    public void getBack() {
        int size = shapesForForward.size();
        if (size > 0) {
            shapesForDrawing.add(shapesForForward.get(size - 1));
            currentShape = shapesForForward.get(size - 1);
            shapesForForward.remove(size - 1);
            invalidate();
        }
    }

    private Shape getShapeByCoordinates(int x, int y) {
        for (Shape shape : shapesForDrawing) {
            if ((x > shape.getStartPoint().x && y > shape.getStartPoint().y &&
                    x < shape.getEndPoint().x && y < shape.getEndPoint().y) || (x > shape.getEndPoint().x && y > shape.getEndPoint().y &&
                    x < shape.getStartPoint().x && y < shape.getStartPoint().y)) {
                return shape;
            }
            if (shape instanceof Triangle) {
                Triangle triangle = (Triangle) shape;
                if (x > min(triangle.getStartPoint().x, triangle.getSecondPoint().x, triangle.getEndPoint().x) &&
                        y > min(triangle.getStartPoint().y, triangle.getSecondPoint().y, triangle.getEndPoint().y) &&
                        x < max(triangle.getStartPoint().x, triangle.getSecondPoint().x, triangle.getEndPoint().x) &&
                        y < max(triangle.getStartPoint().y, triangle.getSecondPoint().y, triangle.getEndPoint().y)) {
                    return shape;
                }
            }
        }
        return null;
    }

    public int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    private int calcDis(int x1,int y1,int x2,int y2) {
        return (int)(Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
    }

    public List<Shape> getPicture() {
        return shapesForDrawing;
    }

    public void setPicture(List<Shape> picture) {
        shapesForDrawing = picture;
    }
}
