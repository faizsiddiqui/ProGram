package app.fragments.Base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

import app.program.R;
import app.program.SplashActivity;

/**
 * Not for public use
 * Created by FAIZ on 24-03-2015.
 */
public class LanguageSelector extends Fragment {

    Locale mLocale;
    Button mLanguageSelector;

    private static final int LANGUAGE_SETTINGS = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_language_selector_fragment, container, false);
        mLanguageSelector = (Button) view.findViewById(R.id.language_button);
        mLanguageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage();
            }
        });
        return view;
    }

    private void selectLanguage() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.language_lang)
                .items(R.array.app_language)
                .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Toast.makeText(getActivity(), text + " selected.", Toast.LENGTH_SHORT).show();
                        if (which == 0) {
                            setLocale("hi");
                        } else if (which == 1) {
                            setLocale("en");
                        }
                    }
                })
                .positiveText(R.string.next)
                .show();
    }

    private void setLocale(String lang) {
        mLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();
        configuration.locale = mLocale;
        res.updateConfiguration(configuration, dm);

        Fragment languageSelector = getFragmentManager().findFragmentByTag("FRAGMENT_LANGUAGE_SELECTOR");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(languageSelector);
        transaction.attach(languageSelector);
        transaction.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash splash = new Splash();
                getFragmentManager().beginTransaction()
                        .replace(R.id.FullScreenFrame, splash)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        }, 1000 ); //SplashActivity.SPLASH_TIME_OUT
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LANGUAGE_SETTINGS:
                showUserSettings();
                break;

        }
    }

    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPrefs.getString("pref_language", "NULL");
    }
}
