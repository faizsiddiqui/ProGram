package app.program;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import app.fragments.Base.LanguageSelector;

/**
 * Not for public use
 * Created by FAIZ on 20-03-2015.
 */
public class SplashActivity extends FragmentActivity {

    public final static int SPLASH_TIME_OUT = 5000;
    public static final String PREFERENCES = "ProGramPrefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.getBoolean("first_time", true)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("first_time", false);
            editor.commit();
            if (findViewById(R.id.FullScreenFrame) != null) {
                if (savedInstanceState != null) {
                    LanguageSelector languageSelector = (LanguageSelector) getSupportFragmentManager().findFragmentByTag("FRAGMENT_LANGUAGE_SELECTOR");
                } else {
                    LanguageSelector languageSelector = new LanguageSelector();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.FullScreenFrame, languageSelector, "FRAGMENT_LANGUAGE_SELECTOR")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            }
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
