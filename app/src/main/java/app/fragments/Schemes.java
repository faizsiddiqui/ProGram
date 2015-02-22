package app.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Not for public use
 * Created by FAIZ on 22-02-2015.
 */
public class Schemes extends Fragment implements CardView.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardView mAdapter;

    String[] titles, descriptions, images;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.recycler_view, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_schemes);
        titles = new String[]{
                "Title"
        };

        descriptions = new String[]{
                "Description"
        };
        images = new String[]{
                "https://d13yacurqjgara.cloudfront.net/users/115224/screenshots/1942394/together_1x.jpg"
        };
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CardView(titles, descriptions, images);
        mAdapter.SetOnItemClickListener(this);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "Card clicked.", Toast.LENGTH_SHORT).show();
    }
}
