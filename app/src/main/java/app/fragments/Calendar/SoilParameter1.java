package app.fragments.Calendar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.Fragment;


import app.adapters.CardView;
import app.program.CalendarActivity;
import app.program.MainActivity;
import app.program.R;

/**
 * Created by admin on 10-04-2015.
 */
public class SoilParameter1 extends Fragment {
    private Button btn4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_soil_parameter1, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Soil Parameters");
        btn4 = (Button) view.findViewById(R.id.jumpbtn4);
        btn4.setOnClickListener(new ButtonEvent());
        return view;

    }

    private class ButtonEvent implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            SoilParameter2 param = new SoilParameter2();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, param)
                    .addToBackStack(null)
                    .commit();

        }
    }
}
