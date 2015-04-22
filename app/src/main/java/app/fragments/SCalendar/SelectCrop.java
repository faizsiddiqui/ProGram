package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.adapters.CalendarSelectCropCardView;
import app.fragments.Calendar.EnterMonth;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {

    Button calendarSoilTest;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CalendarSelectCropCardView mAdapter;

    private static final String STATE = "state";
    String state;

    public static SelectCrop newInstance(String state){
        SelectCrop selectCrop = new SelectCrop();
        Bundle bundle = new Bundle();
        bundle.putString(STATE, state);
        selectCrop.setArguments(bundle);
        return selectCrop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            state = getArguments().getString(STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Choose a crop");

        calendarSoilTest = (Button) view.findViewById(R.id.calendar_soil_test);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_select_crop);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        calendarSoilTest.setOnClickListener(new ToSoilTest());

        setAdapter();
        return view;
    }

    private void setAdapter() {

        String[] name = {"Crop 1", "Crop 2"};
        String[] images = {"", ""};

        mAdapter = new CalendarSelectCropCardView(name, images);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class ToSoilTest implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EnterMonth month = new EnterMonth();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, month)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
