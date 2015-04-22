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
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {

    Button soilTest;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CalendarSelectCropCardView mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_select_crop);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter();

        return view;
    }

    private void setAdapter() {

        String[] name = {"Crop 1", "Crop 2"};
        String[] images = {"https://d13yacurqjgara.cloudfront.net/users/134564/screenshots/2025052/dbbb1.png",
                "https://d13yacurqjgara.cloudfront.net/users/134564/screenshots/2025052/dbbb1.png"};

        mAdapter = new CalendarSelectCropCardView(name, name);
        mRecyclerView.setAdapter(mAdapter);
    }
}
