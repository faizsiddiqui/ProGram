package app.fragments.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 30-04-2015.
 */
public class Help extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_splash, container, false);
        return view;
    }
}
