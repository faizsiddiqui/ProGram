package app.fragments.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.adapters.HomeView;
import app.program.BaseActivity;
import app.program.CalendarActivity;
import app.program.ForumActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 20-02-2015.
 */
public class Home extends Fragment implements HomeView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeView mAdapter;

    String calendar, forum, weather, market;
    String[] home_text;
    int[] image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = getString(R.string.home_calendar);
        forum = getString(R.string.home_forum);
        weather = getString(R.string.home_weather);
        market = getString(R.string.home_market);

        home_text = new String[]{
                calendar, forum,
                weather, market
        };

        image = new int[]{
                R.mipmap.ic_home_calendar, R.mipmap.ic_home_forum,
                R.mipmap.ic_home_forum, R.mipmap.ic_home_forum
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.base_home_fragment, container, false);
        ((BaseActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_home);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter = new HomeView(home_text, image);
        mAdapter.SetOnItemClickListener(this);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.homeGrid);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (home_text[position].equals(calendar)) {
            Intent calendar = new Intent(getActivity(), CalendarActivity.class);
            startActivity(calendar);
        } else if(home_text[position].equals(forum)) {
            Intent forum = new Intent(getActivity(), ForumActivity.class);
            startActivity(forum);
        } else {
            Toast.makeText(getActivity(), "Module under construction", Toast.LENGTH_SHORT).show();
        }
    }
}
