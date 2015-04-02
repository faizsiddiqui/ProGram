package app.fragments.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.MainActivity;
import app.program.R;

/**
 * Created by apple on 4/2/2015.
 */
public class MainCalendar extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_enter_location_fragment, container, false);
        ((MainActivity) getActivity(null)).setActionBarTitle(R.string.toolbar_text_calendar);
        return view;
    }

    public Object getActivity(Object activity) {
        return activity;
    }
}
