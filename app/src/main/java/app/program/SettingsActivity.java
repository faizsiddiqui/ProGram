package app.program;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;

/**
 * Not for public use
 * Created by FAIZ on 29-03-2015.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setActionBarTitle("Settings");
            getFragmentManager().beginTransaction()
                    .add(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_settings;
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        public static final String KEY_PREF_LANGUAGE = "language";

        public SettingsFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(KEY_PREF_LANGUAGE)) {
                String lang = sharedPreferences.getString(key, "en");

                Locale mLocale = new Locale(lang);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration configuration = res.getConfiguration();
                configuration.locale = mLocale;
                res.updateConfiguration(configuration, dm);
            }
            restartActivity();
        }

        private void restartActivity() {
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }
    }
}
