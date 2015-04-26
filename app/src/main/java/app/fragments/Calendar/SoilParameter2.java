package app.fragments.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.program.CalendarActivity;
import app.program.R;

/**
 * Created by admin on 11-04-2015.
 */
public class SoilParameter2 extends Fragment {
    private Button btn5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_soil_parameter2, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Soil Parameters(cont.)");
        btn5 = (Button) view.findViewById(R.id.jumpbtn5);
        btn5.setOnClickListener(new ButtonEvent());
        return view;

    }

    private class ButtonEvent implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            SoilParameter3 param = new SoilParameter3();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, param)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
