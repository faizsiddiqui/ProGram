package app.fragments.Forum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.widgets.SlidingTabLayout;
import app.adapters.LearnViewPagerAdapter;
import app.program.ForumActivity;
import app.program.R;

/**
 * Created by apple on 4/27/2015.
 */
public class Learn extends Fragment {

    ViewPager pager;
    LearnViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Learn OF", "Learn Steps"};
    int NumberOfTabs = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_learn, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle("Learn");

        adapter = new LearnViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, NumberOfTabs);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });
        tabs.setViewPager(pager);
        return view;
    }
}

