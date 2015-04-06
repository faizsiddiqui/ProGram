package app.fragments.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.adapters.HomeView;
import app.fragments.Calendar.Calendar;
import app.fragments.Forum.Forum;
import app.program.BaseActivity;
import app.program.ForumActivity;
import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 20-02-2015.
 */
public class Home extends Fragment implements HomeView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeView mAdapter;

    String[] home_text = {
            "Calendar",
            "Forum"
    };

    int[] image = {
            R.mipmap.home_calendar,
            R.mipmap.home_forum
    };

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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left);
        switch (home_text[position]) {
            case "Calendar":
                Calendar calendar = new Calendar();
                transaction.replace(R.id.MainFrame, calendar)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Forum":
                Intent forum = new Intent(getActivity(), ForumActivity.class);
                startActivity(forum);
                break;
        }
    }
}
