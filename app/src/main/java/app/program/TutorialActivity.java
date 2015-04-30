package app.program;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Not for public use
 * Created by FAIZ on 07-04-2015.
 */
public class TutorialActivity extends Activity {

    private ViewPager mViewPager;
    private TutorialView mAdapter;
    Button mNext;

    private ArrayList<Integer> mImages, mTitles, mDescription;

    LinearLayout mDotsLayout;
    private int mDotsCount;
    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        initViews();

        mNext = (Button) findViewById(R.id.tutorial_next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = mViewPager.getCurrentItem() + 1;
                if (next == 2) {
                    String caller = getIntent().getStringExtra("caller");
                    if (caller.equals("SplashActivity")) {
                        Intent home = new Intent(TutorialActivity.this, MainActivity.class);
                        startActivity(home);
                    }
                    finish();
                } else {
                    mViewPager.setCurrentItem(next, true);
                }
            }
        });
        setViewPagerItemsWithAdapter();
        setUiPageViewController();
    }

    private void setUiPageViewController() {
        mDotsLayout = (LinearLayout) findViewById(R.id.tutorial_pager_count_dots);
        mDotsCount = mAdapter.getCount();
        mDots = new TextView[mDotsCount];

        for (int i = 0; i < mDotsCount; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(30);
            mDots[i].setTextColor(getResources().getColor(R.color.colorText));
            mDotsLayout.addView(mDots[i]);
        }
        mDots[0].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void setViewPagerItemsWithAdapter() {
        mAdapter = new TutorialView();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(tutorialPagerChangeListener);
    }

    ViewPager.OnPageChangeListener tutorialPagerChangeListener = new ViewPager.OnPageChangeListener() {

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

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.tutorial_pager);
        mImages = new ArrayList<Integer>();
        mTitles = new ArrayList<Integer>();
        mDescription = new ArrayList<Integer>();

        mImages.add(R.mipmap.ic_forum_learn);
        mImages.add(R.mipmap.ic_forum_learn);
        mImages.add(R.mipmap.ic_home_calendar);
        mImages.add(R.mipmap.ic_float_q_a);
        mImages.add(R.mipmap.ic_forum_scheme);
        mImages.add(R.mipmap.ic_forum_job);

        mTitles.add(R.string.tutorial_title_learn);
        mTitles.add(R.string.tutorial_title_SelectCrop);
        mTitles.add(R.string.tutorial_title_Calender);
        mTitles.add(R.string.tutorial_title_QuestionAndAnswers);
        mTitles.add(R.string.tutorial_title_SchemesAndAwards);
        mTitles.add(R.string.tutorial_title_employment);

        mDescription.add(R.string.tutorial_description_learn);
        mDescription.add(R.string.tutorial_description_SelectCrop);
        mDescription.add(R.string.tutorial_description_Calender);
        mDescription.add(R.string.tutorial_description_QuestionAndAnswers);
        mDescription.add(R.string.tutorial_description_SchemesAndAwards);
        mDescription.add(R.string.tutorial_description_employment);

    }

    public class TutorialView extends PagerAdapter {

        private LayoutInflater inflater;
        TextView titles, description;
        ImageView images;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.activity_tutorial_view, container, false);

            images = (ImageView) view.findViewById(R.id.tutorial_image);
            titles = (TextView) view.findViewById(R.id.tutorial_title);
            description = (TextView) view.findViewById(R.id.tutorial_description);

            images.setBackgroundResource(mImages.get(position));
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
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            ((ViewPager) container).removeView(view);
        }
    }
}
