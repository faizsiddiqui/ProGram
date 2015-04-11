package app.fragments.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.CalendarActivity;
import app.program.R;

/**
 * Created by admin on 11-04-2015.
 */
public class SoilParameter3 extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.calendar_soil_parameter3,container,false);
        ((CalendarActivity)getActivity()).setActionBarTitle("Soil Parameter(cont.)");
        return view;

    }
}
