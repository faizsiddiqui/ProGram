package app.fragments.Forum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.adapters.LearnViewPagerAdapter;
import app.program.ForumActivity;
import app.program.R;
import app.widgets.SlidingTabLayout;

/**
 * Created by apple on 4/27/2015.
 */
public class Learn extends Fragment {

    ViewPager pager;
    LearnViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Introduction", "Steps"};
    int numberOfTabs = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_learn, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle("Learn");

        adapter = new LearnViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, numberOfTabs);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        tabs.setViewPager(pager);
        return view;
    }
}

