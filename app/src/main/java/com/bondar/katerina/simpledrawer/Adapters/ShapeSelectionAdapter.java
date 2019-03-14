package com.bondar.katerina.simpledrawer.Adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bondar.katerina.simpledrawer.R;
import com.bondar.katerina.simpledrawer.Views.ShapeItem;

public class ShapeSelectionAdapter extends RecyclerView.Adapter<ShapeSelectionAdapter.ViewHolder> {

    private int[] shapes = new int[6];
    private Paint paint;
    private Fragment parent;

    public ShapeSelectionAdapter(Fragment parent, Paint paint) {
        this.paint = paint;
        this.parent = parent;
        //Initializing array of shapes
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = i;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shape_selection_item, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shapeItem.draw(shapes[position]);
    }

    @Override
    public int getItemCount() {
        return shapes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card;
        ShapeItem shapeItem;

        ViewHolder(View item) {
            super(item);
            card = item.findViewById(R.id.shape_selection_item);
            shapeItem = item.findViewById(R.id.shape_item_view);
            shapeItem.init(paint);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Adapter position is a shape type
            if (parent instanceof OnShapeSelectListener) {
                ((OnShapeSelectListener)parent).onShapeSelected(getAdapterPosition());
            }
        }
    }

    public interface OnShapeSelectListener {
        void onShapeSelected(int shapeType);
    }
}
