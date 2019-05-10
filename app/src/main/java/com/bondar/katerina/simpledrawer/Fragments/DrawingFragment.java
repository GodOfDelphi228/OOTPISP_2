package com.bondar.katerina.simpledrawer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bondar.katerina.simpledrawer.Adapters.ShapeSelectionAdapter;
import com.bondar.katerina.simpledrawer.R;
import com.bondar.katerina.simpledrawer.Repository.PreferencesRepository;
import com.bondar.katerina.simpledrawer.Shapes.Circle;
import com.bondar.katerina.simpledrawer.Shapes.Line;
import com.bondar.katerina.simpledrawer.Shapes.Oval;
import com.bondar.katerina.simpledrawer.Shapes.Rectangle;
import com.bondar.katerina.simpledrawer.Shapes.RoundedRectangle;
import com.bondar.katerina.simpledrawer.Shapes.Shape;
import com.bondar.katerina.simpledrawer.Shapes.Triangle;
import com.bondar.katerina.simpledrawer.Views.DrawingView;
import com.bondar.katerina.simpledrawer.Views.PropertiesSelectionLayout;
import java.util.LinkedList;
import java.util.List;

public class DrawingFragment extends Fragment
        implements PropertiesSelectionLayout.PropertiesListener, ShapeSelectionAdapter.OnShapeSelectListener{

    private DrawingView drawingView;
    private PropertiesSelectionLayout propertiesLayout;
    private RecyclerView shapeSelection;
    private ShapeSelectionAdapter adapter;

    public DrawingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);
        drawingView = view.findViewById(R.id.drawing_view);
        propertiesLayout = view.findViewById(R.id.properties_layout);
        shapeSelection  = view.findViewById(R.id.shape_selection_recycler_view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreState();
    }

    private void restoreState() {
        String picture = PreferencesRepository.getInstance(getContext()).getString("picture", "");
        drawingView.setPicture(getShapesFor(picture));
    }

    private void init() {
        int initialColor = Color.BLACK;
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        shapeSelection.setLayoutManager(horizontalLayoutManager);
        Paint paint = drawingView.getCurrentPaint();
        adapter = new ShapeSelectionAdapter(this, paint);
        shapeSelection.setHasFixedSize(true);
        shapeSelection.setAdapter(adapter);

        propertiesLayout.setFragment(this);
        propertiesLayout.setConfirmationVisible(false);
        getLayoutInflater().inflate(R.layout.properties_linear_layout, propertiesLayout, false);
        setColor(initialColor);
    }


    public void clearFragment() {
        drawingView.clearView();
    }

    public void setColor(int color) {
        propertiesLayout.setCurrentColor(color);
        drawingView.setShapeDrawingColor(color);
    }

    @Override
    public void onPropertyChanged(int propertyType, int code) {
        adapter.notifyDataSetChanged();
        switch (propertyType) {
            case PropertiesSelectionLayout.TYPE_COLOR:
                setColor(code);
                break;

            case PropertiesSelectionLayout.TYPE_STYLE:
                drawingView.setShapeDrawingStyle(code);
                break;
        }
    }

    @Override
    public void onConfirmClicked() {
        drawingView.confirmPolygon();
    }

    @Override
    public void onShapeSelected(int shapeType) {
        drawingView.setShapeType(shapeType);
    }

    @Override
    public void onBackClicked() {
        drawingView.cancelLast();
    }

    @Override
    public void onForwardClicked() {
        drawingView.getBack();
    }

    @Override
    public void onEditClicked(int type) {
        drawingView.setEditType(type);
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = PreferencesRepository.getInstance(getContext()).edit();
        String serValue =  getTextSerializeFor(drawingView.getPicture());
        Log.d("serValue", serValue);
        editor.putString("picture", serValue);
        editor.apply();
    }

    private String getTextSerializeFor(List<Shape> shapes) {
        String result = "";
        for (Shape shape : shapes) {
            if (shape instanceof Triangle) {
                Triangle triangle = (Triangle)shape;
                result += "triangle" + "@(" + triangle.getStartPoint().x + ";" + triangle.getStartPoint().y + ")" + " "
                        + "(" + triangle.getSecondPoint().x + ";" + triangle.getSecondPoint().y + ")" + " "
                        + "(" + triangle.getEndPoint().x + ";" + triangle.getEndPoint().y + ")" + "@" + triangle.getShapePaint().getColor();
            } else if (shape instanceof Rectangle) {
                Rectangle cur = (Rectangle) shape;
                result += "rectangle" + "@(" + cur.getStartPoint().x + ";" + cur.getStartPoint().y + ")" + " "
                        + "(" + cur.getEndPoint().x + ";" + cur.getEndPoint().y + ")" + "@" + cur.getShapePaint().getColor();
            } else if (shape instanceof RoundedRectangle) {
                RoundedRectangle cur = (RoundedRectangle) shape;
                result += "rounded_rectangle" + "@(" + cur.getStartPoint().x + ";" + cur.getStartPoint().y + ")" + " "
                        + "(" + cur.getEndPoint().x + ";" + cur.getEndPoint().y + ")" + "@" + cur.getShapePaint().getColor();
            } else if (shape instanceof Oval) {
                Oval cur = (Oval) shape;
                result += "oval" + "@(" + cur.getStartPoint().x + ";" + cur.getStartPoint().y + ")" + " "
                        + "(" + cur.getEndPoint().x + ";" + cur.getEndPoint().y + ")" + "@" + cur.getShapePaint().getColor();
            } else if (shape instanceof Line) {
                Line cur = (Line) shape;
                result += "line" + "@(" + cur.getStartPoint().x + ";" + cur.getStartPoint().y + ")" + " "
                        + "(" + cur.getEndPoint().x + ";" + cur.getEndPoint().y + ")" + "@" + cur.getShapePaint().getColor();
            } else if (shape instanceof Circle) {
                Circle cur = (Circle) shape;
                result += "circle" + "@(" + cur.getStartPoint().x + ";" + cur.getStartPoint().y + ")" + " "
                        + "(" + cur.getEndPoint().x + ";" + cur.getEndPoint().y + ")" + "@" + cur.getShapePaint().getColor();
            }
            result += "&";
        }
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private List<Shape> getShapesFor(String ser) {
        List<Shape> shapes = new LinkedList<>();
        String[] strShapes = ser.split("&");
        for (String strShape : strShapes) {
            if (strShape.length() > 0) {
                shapes.add(createShapeFor(strShape));
            }
        }
        return shapes;
    }

    private Shape createShapeFor(String strShape) {
        String[] typeAndDots = strShape.split("@");
        String type = typeAndDots[0];
        if (type.equals("triangle")) {
            Paint paint = new Paint();
            paint.setColor(Integer.parseInt(typeAndDots[2]));
            List<Point> points = getPointsFor(typeAndDots[1]);
            Triangle result = new Triangle(points.get(0).x, points.get(0).y, paint);
            result.setSecondPoint(points.get(1).x, points.get(1).y);
            result.setEndPoint(points.get(2).x, points.get(2).y);
            return result;
        } else if (type.equals("rectangle")) {
            Paint paint = new Paint();
            paint.setColor(Integer.parseInt(typeAndDots[2]));
            List<Point> points = getPointsFor(typeAndDots[1]);
            Rectangle result = new Rectangle(points.get(0).x, points.get(0).y, paint);
            result.setEndPoint(points.get(1).x, points.get(1).y);
            return result;
        } else if (type.equals("rounded_rectangle")) {
            Paint paint = new Paint();
            paint.setColor(Integer.parseInt(typeAndDots[2]));
            List<Point> points = getPointsFor(typeAndDots[1]);
            RoundedRectangle result = new RoundedRectangle(points.get(0).x, points.get(0).y, 10, paint);
            result.setEndPoint(points.get(1).x, points.get(1).y);
            return result;
        } else if (type.equals("oval")) {
            Paint paint = new Paint();
            paint.setColor(Integer.parseInt(typeAndDots[2]));
            List<Point> points = getPointsFor(typeAndDots[1]);
            Oval result = new Oval(points.get(0).x, points.get(0).y,  paint);
            result.setEndPoint(points.get(1).x, points.get(1).y);
            return result;
        } else if (type.equals("line")) {
            Paint paint = new Paint();
            paint.setColor(Integer.parseInt(typeAndDots[2]));
            List<Point> points = getPointsFor(typeAndDots[1]);
            Line result = new Line(points.get(0).x, points.get(0).y,  paint);
            result.setEndPoint(points.get(1).x, points.get(1).y);
            return result;
        } else if (type.equals("circle")) {
            Paint paint = new Paint();
            paint.setColor(Integer.parseInt(typeAndDots[2]));
            List<Point> points = getPointsFor(typeAndDots[1]);
            Circle result = new Circle(points.get(0).x, points.get(0).y, paint);
            result.setEndPoint(points.get(1).x, points.get(1).y);
            return result;
        }
        return null;
    }

    private List<Point> getPointsFor(String strPoints) {
        List<Point> points = new LinkedList<>();
        String[] pointsArr = strPoints.split(" ");
        for (String strPoint : pointsArr) {
            String withoutBrackets = strPoint.substring(1, strPoint.length() - 1);
            String[] coordinates = withoutBrackets.split(";");
            points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
        }
        return points;
    }
}
