package app.fragments;

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
            "Forum",
            "Schemes"
    };

    int[] image = {
            R.mipmap.profile,
            R.mipmap.profile,
            R.mipmap.profile
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_home);
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
        switch (home_text[position]) {
            case "Calendar":
                break;
            case "Forum":
                break;
            case "Schemes":
                Schemes schemes = new Schemes();
                getFragmentManager().beginTransaction()
                        .replace(R.id.MainFrame, schemes)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
