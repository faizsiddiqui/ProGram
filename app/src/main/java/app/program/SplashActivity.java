package app.program;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import java.util.Locale;

import app.fragments.Base.LanguageSelector;

/**
 * Not for public use
 * Created by FAIZ on 20-03-2015.
 */
public class SplashActivity extends FragmentActivity {

    Locale mLocale;
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
            String lang = sharedpreferences.getString("language", "en");

            mLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration configuration = res.getConfiguration();
            configuration.locale = mLocale;
            res.updateConfiguration(configuration, dm);

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
