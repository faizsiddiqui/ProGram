package app.fragments.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import app.program.CalendarActivity;
import app.program.MainActivity;
import app.program.R;

/**
 * Created by apple on 3/21/2015.
 */

public class EnterLocation extends Fragment {
    private Spinner spinner;
    public String[] location_list;
    private Button btn2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_enter_location_fragment, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_enter_location);
         location_list = getResources().getStringArray(R.array.location_list);
         spinner = (Spinner) view.findViewById(R.id.location_spinner);
         btn2 = (Button) view.findViewById(R.id.jumpbtn2);
         btn2.setOnClickListener(new ButtonEvent());
         ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,location_list);
         dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(dataAdapter);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getActivity(), "Cannot proceed without entering location", Toast.LENGTH_SHORT).show();
            }
        });
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






