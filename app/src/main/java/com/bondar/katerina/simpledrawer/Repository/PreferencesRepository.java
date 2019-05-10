package com.bondar.katerina.simpledrawer.Repository;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesRepository {

    private static SharedPreferences sharedPreferences = null;

    public static SharedPreferences getInstance(Context context) {
        if (sharedPreferences != null) {
            return sharedPreferences;
        } else {
            sharedPreferences = context.getSharedPreferences(context
                    .getApplicationInfo().name, Context.MODE_PRIVATE);
            return sharedPreferences;
        }
    }
}
