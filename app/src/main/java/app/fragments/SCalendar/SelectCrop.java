package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.fragments.Calendar.EnterMonth;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {
    private Button btn2;
    private static final String LOCATION = "state";
    String state;

    public static SelectCrop newInstance(String state){
        SelectCrop selectCrop = new SelectCrop();
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION, state);
        selectCrop.setArguments(bundle);
        return selectCrop;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        if(getArguments() != null){
            state = getArguments().getString(LOCATION);
            ((CalendarActivity) getActivity()).setActionBarTitle("Choose a crop " + state);
            btn2 = (Button) view.findViewById(R.id.jmpbtn2);
            btn2.setOnClickListener(new ButtonEvent());
        }
        return view;
    }
    private class ButtonEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EnterMonth month = new EnterMonth();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, month)
                    .addToBackStack(null)
                    .commit();
        }
    }
};


