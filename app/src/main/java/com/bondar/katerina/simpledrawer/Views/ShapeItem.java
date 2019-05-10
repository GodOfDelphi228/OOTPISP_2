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
    public static final int SHAPE_TRIANGLE = 5;

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

            case SHAPE_TRIANGLE:
                polygonPath.reset();
                polygonPath.setFillType(Path.FillType.EVEN_ODD);
                polygonPath.moveTo(left + 80, top); // Top
                polygonPath.lineTo(left , top + 100); // Bottom left
                polygonPath.lineTo(left + 150, top + 100); // Bottom right
                polygonPath.lineTo(left + 80, top); // Back to Top
                polygonPath.close();
                canvas.drawPath(polygonPath, paint);
        }
    }
}
