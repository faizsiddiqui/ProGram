package app.library;

import android.content.Context;
import android.content.SharedPreferences;

import app.program.BaseActivity;

/**
 * Not for public use
 * Created by FAIZ on 19-02-2015.
 */
public class Preferences {

    private static final String PREFERENCES = BaseActivity.PREFERENCES;

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }
}
