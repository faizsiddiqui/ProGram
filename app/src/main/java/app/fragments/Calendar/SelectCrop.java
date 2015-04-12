package app.fragments.Calendar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import app.program.CalendarActivity;
import app.program.MainActivity;
import app.program.R;

/**
 * Created by apple on 3/18/2015.
 */
public class SelectCrop extends Fragment {
    private Spinner cropPrompt;
    public String[] india_crops;
    private Button btn1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_select_crop_fragment, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Select Crop");
        india_crops = getResources().getStringArray(R.array.india_crops);
        cropPrompt = (Spinner) view.findViewById(R.id.crop_spinner);
        btn1 = (Button) view.findViewById(R.id.jumpbtn);
        btn1.setOnClickListener(new ButtonEvent());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, india_crops);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropPrompt.setAdapter(dataAdapter);
        cropPrompt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getActivity(), "Have to enter a crop", Toast.LENGTH_SHORT).show();
            }
        });


        return view;


    }
    private class ButtonEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            EnterLocation location = new EnterLocation();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, location)
                    .addToBackStack(null)
                    .commit();

        }
    }
}


























