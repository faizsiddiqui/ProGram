package app.program;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import app.fragments.Base.Splash;

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
                    Splash splash = (Splash) getSupportFragmentManager().findFragmentByTag("FRAGMENT_SPLASH");
                } else {
                    Splash splash = new Splash();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.FullScreenFrame, splash, "FRAGMENT_SPLASH")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
