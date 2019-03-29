package com.bondar.katerina.simpledrawer.Fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bondar.katerina.simpledrawer.Adapters.ShapeSelectionAdapter;
import com.bondar.katerina.simpledrawer.R;
import com.bondar.katerina.simpledrawer.Views.DrawingView;
import com.bondar.katerina.simpledrawer.Views.PropertiesSelectionLayout;
import com.bondar.katerina.simpledrawer.Views.ShapeItem;

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
        if (shapeType == ShapeItem.SHAPE_POLYGON) {
            propertiesLayout.setConfirmationVisible(true);
        } else {
            propertiesLayout.setConfirmationVisible(false);
        }
    }

    @Override
    public void onBackClicked() {
        drawingView.cancelLast();
    }

    @Override
    public void onForwardClicked() {
        drawingView.getBack();
    }
}
