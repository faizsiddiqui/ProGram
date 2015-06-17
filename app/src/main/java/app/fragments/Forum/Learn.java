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

    ViewPager mViewPager;
    LearnViewPagerAdapter mViewPageAdapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Introduction", "Steps"};
    int numberOfTabs = 2;

    View view;

    @Override
    public void onResume() {
        super.onResume();
        initialize(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forum_learn, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_learn);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        mViewPageAdapter = new LearnViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, numberOfTabs);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPageAdapter);

        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        tabs.setViewPager(mViewPager);
    }
}

