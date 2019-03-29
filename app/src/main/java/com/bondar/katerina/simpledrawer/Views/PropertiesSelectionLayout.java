package com.bondar.katerina.simpledrawer.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.bondar.katerina.simpledrawer.R;

public class PropertiesSelectionLayout extends LinearLayout implements View.OnClickListener {

    public static final int TYPE_COLOR = 0;
    public static final int TYPE_STYLE = 1;

    public static final int STYLE_FILLED = 0;
    public static final int STYLE_STROKE = 1;

    private PropertiesListener mCallBack;
    private Button mColorButton;
    private ImageView mStyleImageView;
    private ImageView mConfirm;
    private ImageView mBack;
    private ImageView mForward;
    private Fragment fragment;

    private int shapeStyle;

    public PropertiesSelectionLayout(Context context) {
        super(context);
        init();
    }

    public PropertiesSelectionLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PropertiesSelectionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PropertiesSelectionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.properties_linear_layout, this);
        mColorButton = findViewById(R.id.properties_color_button);
        mStyleImageView = findViewById(R.id.properties_style_image_view);
        mConfirm = findViewById(R.id.properties_polygon_confirm);
        mBack = findViewById(R.id.properties_back);
        mForward = findViewById(R.id.properties_forward);

        mStyleImageView.setImageResource(R.drawable.fill);
        shapeStyle = STYLE_FILLED;

        mColorButton.setOnClickListener(this);
        mStyleImageView.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mForward.setOnClickListener(this);
    }

    public void setCurrentColor(int color) {
        mColorButton.setBackgroundColor(color);
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
        try {
            mCallBack = (PropertiesListener) fragment;
        } catch (ClassCastException e) {
            throw new RuntimeException(fragment.toString() + " should implements PropertiesListener");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.properties_color_button:
                if (fragment.getActivity() != null) {
                    ColorPickerDialog.newBuilder().show(fragment.getActivity());
                }
                break;

            case R.id.properties_style_image_view:
                shapeStyle = (shapeStyle + 1) % 2;
                mCallBack.onPropertyChanged(TYPE_STYLE, shapeStyle);
                notifyDataChanges(TYPE_STYLE, shapeStyle);
                break;

            case R.id.properties_polygon_confirm:
                mCallBack.onConfirmClicked();
                break;

            case R.id.properties_back:
                mCallBack.onBackClicked();
                break;

            case R.id.properties_forward:
                mCallBack.onForwardClicked();
                break;
        }
    }

    private void notifyDataChanges(int propertyType, int code) {
        switch (propertyType) {
            case TYPE_STYLE:
                if (code == STYLE_FILLED) {
                    mStyleImageView.setImageResource(R.drawable.fill);
                } else {
                    mStyleImageView.setImageResource(R.drawable.stroke);
                }
        }
    }

    public void setConfirmationVisible(boolean isVisible) {
        if (isVisible) {
            mConfirm.setVisibility(VISIBLE);
        } else {
            mConfirm.setVisibility(GONE);
        }
    }

    public interface PropertiesListener {
        void onPropertyChanged(int propertyType, int code);
        void onConfirmClicked();
        void onBackClicked();
        void onForwardClicked();
    }
}
