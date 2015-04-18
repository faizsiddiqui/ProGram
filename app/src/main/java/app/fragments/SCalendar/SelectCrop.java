package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        return view;
    }
}
