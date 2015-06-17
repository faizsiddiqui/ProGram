package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.adapters.ForumView;
import app.fragments.Calendar.EnterLocation;
import app.fragments.Calendar.SelectCrop1;
import app.fragments.Calendar.Suggestion;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Created by apple on 3/17/2015.
 */
public class Calendar extends Fragment implements ForumView.OnItemClickListener {

    private static Calendar instance;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    String[] calendarTitle = {
           "Location",
           "SoilTestParameters",
           "SelectCrop",
           "MainCalendar"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Calendar");
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.CalendarRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (calendarTitle[position]) {
            case "Location":
                EnterLocation enterLocation = new EnterLocation();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, enterLocation)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;

            case "SoilTestParameters":
                Suggestion suggest = new Suggestion();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, suggest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;

            case "SelectCrop":
                SelectCrop1 selectcrop = new SelectCrop1();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, selectcrop)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "MainCalendar":
                MainCalendar mainCalendar = new MainCalendar();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame,mainCalendar)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    public static Calendar getInstance() {
        return instance;
    }


}
