package app.program;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

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

        public static final String KEY_PREF_LANGUAGE = "pref_language";

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
                Toast.makeText(getActivity(), sharedPreferences.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
