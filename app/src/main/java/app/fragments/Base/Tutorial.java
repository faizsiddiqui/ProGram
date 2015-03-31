package app.fragments.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 28-03-2015.
 */
public class Tutorial extends Fragment {

    private ViewPager mViewPager;
    private TutorialView mAdapter;
    Button mNext;

    private ArrayList<Integer> mTitles;
    private ArrayList<Integer> mDescription;

    LinearLayout mDotsLayout;
    private int mDotsCount;
    private TextView[] mDots;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_tutorial_fragment, container, false);
        initViews(view);

        mNext = (Button) view.findViewById(R.id.tutorial_next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = mViewPager.getCurrentItem() + 1;
                if(next == 2) {
                    Intent home = new Intent(getActivity(), MainActivity.class);
                    startActivity(home);
                    getActivity().finish();
                }
                else {
                    mViewPager.setCurrentItem(next, true);
                }
            }
        });

        setViewPagerItemsWithAdapter(view);
        setUiPageViewController(view);
        return view;
    }

    private void setViewPagerItemsWithAdapter(View view) {
        mAdapter = new TutorialView();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(tutorialPagerChangeListener);
    }

    ViewPager.OnPageChangeListener tutorialPagerChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            for (int i = 0; i < mDotsCount; i++) {
                mDots[i].setTextColor(getResources().getColor(R.color.colorText));
                mDots[i].setTextSize(30);

            }
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setUiPageViewController(View view) {
        mDotsLayout = (LinearLayout) view.findViewById(R.id.tutorial_pager_count_dots);
        mDotsCount = mAdapter.getCount();
        mDots = new TextView[mDotsCount];

        for (int i = 0; i < mDotsCount; i++) {
            mDots[i] = new TextView(getActivity());
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(30);
            mDots[i].setTextColor(getResources().getColor(R.color.colorText));
            mDotsLayout.addView(mDots[i]);
        }
        mDots[0].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void initViews(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.tutorial_pager);
        mTitles = new ArrayList<Integer>();
        mDescription = new ArrayList<Integer>();
        mTitles.add(R.string.tutorial_title_learn);
        mTitles.add(R.string.tutorial_title_employment);
        mDescription.add(R.string.tutorial_description_learn);
        mDescription.add(R.string.tutorial_description_employment);
    }

    public class TutorialView extends PagerAdapter {

        private LayoutInflater inflater;

        TextView titles, description;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.base_tutorial_view, container, false);

            titles = (TextView) view.findViewById(R.id.tutorial_title);
            description = (TextView) view.findViewById(R.id.tutorial_description);

            titles.setText(mTitles.get(position));
            description.setText(mDescription.get(position));
            ((ViewPager) container).addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View)object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            ((ViewPager) container).removeView(view);
        }
    }

}