package app.fragments.Calendar;


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
import app.program.CalendarActivity;
import app.program.R;

/**
 * Created by apple on 3/17/2015.
 */
public class Calendar extends Fragment implements ForumView.OnItemClickListener {

    public static int Hour_of_day;
    public static int minute;
    public static int second;
    private static Calendar instance;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumView mAdapter;

    String[] calendarTitle = {
            // "Recommended Crop",
            "Select Crop",
            "Enter Location",
            "Enter Month",
            "Suggestions",
            "Soil Parameter 1",
            "Soil Parameter 2",
            "Soil Parameter 3"
    };

    String[] calendarText = {
            "Completed",
            "Completed",
            "Completed",
            "In Progress",
            "Completed",
            "In Progress",
            "In Progress"
    };

    int[] image = {
            R.mipmap.ic_base_login_sign_up,
            R.mipmap.ic_base_login_sign_up,
            R.mipmap.ic_base_login_sign_up,
            R.mipmap.ic_base_login_sign_up,
            R.mipmap.ic_base_login_sign_up,
            R.mipmap.ic_base_login_sign_up,
            R.mipmap.ic_base_login_sign_up

    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Calendar");
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ForumView(calendarTitle, calendarText, image);
        mAdapter.SetOnItemClickListener(this);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.CalendarRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (calendarTitle[position]) {
            //   case "Recommended Crops":
            //  RecommendedCrop recommendedCrop = new RecommendedCrop();
            //  getFragmentManager().beginTransaction()
            //   .replace(R.id.calendarFrame, recommendedCrop)
            //   .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            //   .addToBackStack(null)
            //   .commit();
            //   break;
            case "Select Crop":
                SelectCrop selectcrop = new SelectCrop();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, selectcrop)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Enter Location":
                EnterLocation enterLocation = new EnterLocation();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, enterLocation)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Enter Month":
                EnterMonth enterMonth = new EnterMonth();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, enterMonth)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Suggestions":
                Suggestion suggest = new Suggestion();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, suggest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Soil Parameter 1":
                SoilParameter1 sp1 = new SoilParameter1();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, sp1)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Soil Parameter 2":
                SoilParameter2 sp2 = new SoilParameter2();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, sp2)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Soil Parameter 3":
                SoilParameter3 sp3 = new SoilParameter3();
                getFragmentManager().beginTransaction()
                        .replace(R.id.calendarFrame, sp3)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
     public void set(int year, int month, int day) {
    }

    public void set(int hour_of_day, int i) {
    }

    public static Calendar getInstance() {
        return instance;
    }


}
