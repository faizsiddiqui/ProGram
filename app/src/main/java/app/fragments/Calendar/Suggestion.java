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
 * Created by admin on 12-04-2015.
 */
public class Suggestion extends Fragment {

    private Button btns;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_suggestion, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Suggested Crops");

        btns = (Button) view.findViewById(R.id.jumpbtns);
        btns.setOnClickListener(new ButtonEvent());
        return view;
    }

    private class ButtonEvent implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            SoilParameter1 param = new SoilParameter1();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, param)
                    .addToBackStack(null)
                    .commit();

        }
    }


}

