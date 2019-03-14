package com.bondar.katerina.simpledrawer.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class ShapeItem extends View {

    public static final int SHAPE_LINE = 0;
    public static final int SHAPE_RECTANGLE = 1;
    public static final int SHAPE_ROUNDED_RECTANGLE = 2;
    public static final int SHAPE_CIRCLE = 3;
    public static final int SHAPE_OVAL = 4;
    public static final int SHAPE_POLYGON = 5;

    private Paint paint;
    private int currentShape;
    private RectF rect;
    private Path polygonPath;

    public ShapeItem(Context context) {
        super(context);
    }

    public ShapeItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ShapeItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Paint paint) {
        this.paint = paint;
        rect = new RectF();
        polygonPath = new Path();
    }

    public void draw(int shapeType) {
        this.currentShape = shapeType;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        int left = width / 10;
        int top = height / 10;
        int right = width - left;
        int bottom = height - top;
        rect.set(left, top, right, bottom);
        switch (currentShape) {
            case SHAPE_LINE:
                canvas.drawLine(left, bottom, right, top, paint);
                break;

            case SHAPE_RECTANGLE:
                canvas.drawRect(rect, paint);
                break;

            case SHAPE_ROUNDED_RECTANGLE:
                canvas.drawRoundRect(rect, 10, 10, paint);
                break;

            case SHAPE_CIRCLE:
                canvas.drawCircle((left + right) / 2, (top + bottom) / 2, (bottom - top) / 2, paint);
                break;

            case SHAPE_OVAL:
                canvas.drawOval(rect, paint);
                break;

            case SHAPE_POLYGON:
                polygonPath.moveTo(left, bottom);
                polygonPath.lineTo(left, top);
                polygonPath.lineTo(4 * left, 4 * top);
                polygonPath.lineTo(right, 4 * top);
                polygonPath.close();
                canvas.drawPath(polygonPath, paint);
        }
    }
}
