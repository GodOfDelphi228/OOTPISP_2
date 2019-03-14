package com.bondar.katerina.simpledrawer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.bondar.katerina.simpledrawer.Fragments.DrawingFragment;
import com.bondar.katerina.simpledrawer.Views.PropertiesSelectionLayout;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private DrawingFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_item_clear:
                fragment.clearFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragment = new DrawingFragment();
        fragmentTransaction.add(R.id.fragment_container_main, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onColorSelected(int dialogId, int color) {
        fragment.onPropertyChanged(PropertiesSelectionLayout.TYPE_COLOR, color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
