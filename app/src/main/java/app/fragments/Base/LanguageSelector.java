package app.fragments.Base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
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

/**
 * Not for public use
 * Created by FAIZ on 24-03-2015.
 */
public class LanguageSelector extends Fragment {

    Locale mLocale;
    Button mLanguageSelector;

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
        Splash splash = new Splash();
        getFragmentManager().beginTransaction()
                .replace(R.id.FullScreenFrame, splash)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
