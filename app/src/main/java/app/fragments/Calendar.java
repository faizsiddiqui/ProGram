package app.fragments;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import app.fragments.Recommendedcrop;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.adapters.CardView;
import app.program.MainActivity;
import app.program.R;
/**
 * Created by apple on 3/17/2015.
 */
public class Calendar extends Fragment implements CardView.OnItemClickListener {


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CardView mAdapter;

    String[] calendarTitle = {
            "Recommended crop",
            "Select crop"
    };
    String[] calendarText = {
            "In progress",
            "In progress"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Calendar");
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.calendarRecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter();

        return view;


    }

    private void setAdapter() {
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (calendarTitle[position]) {
            case "Recommended crops":
                Recommendedcrop recommendedcrop = new Recommendedcrop();
                getFragmentManager().beginTransaction()
                        .replace(R.id.MainFrame, recommendedcrop)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Select crop":
                Selectcrop selectcrop = new Selectcrop();
                getFragmentManager().beginTransaction()
                        .replace(R.id.MainFrame, selectcrop)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
