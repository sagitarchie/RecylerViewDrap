package org.archie.app.recyclerviewdragdrop;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/**
 * utils for storing Map<String,String> to file
 * Created by archie on 15/11/24.
 */
public class SharedPreferencesUtils {

    public static void save(String fileName,Map<String,String> saveMap) {
        SharedPreferences sp = getSharedPreferenceInstance(fileName);
        SharedPreferences.Editor editor = sp.edit();
        for (Map.Entry<String,String> entry : saveMap.entrySet()) {
            editor.putString(entry.getKey(),entry.getValue());
        }
        editor.apply();
    }

    public static String getValue(String fileName,String key, String defaultValue) {
        SharedPreferences sp = getSharedPreferenceInstance(fileName);
        return sp.getString(key, defaultValue);
    }

    public static void remove(String fileName) {
        SharedPreferences sp = getSharedPreferenceInstance(fileName);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences getSharedPreferenceInstance(String fileName) {
        return MyApp.getInstance().getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }
}
