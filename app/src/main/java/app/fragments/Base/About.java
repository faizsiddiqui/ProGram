package app.fragments.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.adapters.ForumPostsCardView;
import app.program.OtherActivity;
import app.program.R;
import app.widgets.LinearLayoutManagerRecyclerView;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Not for public use
 * Created by FAIZ on 29-04-2015.
 */
public class About extends Fragment {

    RecyclerView mDeveloperRecyclerView, mOpenSourceRecyclerView;
    RecyclerView.LayoutManager mDevLayoutManger, mOpenSourceLayoutMangaer;
    ForumPostsCardView mDeveloperAdapter, mOpenSourceAdapter;

    String[] imageDev, nameDev, positionDev, imageOpenSource, nameOpenSource, createdByOpenSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageDev = new String[]{
                "http://pbs.twimg.com/profile_images/430081238980763649/puqbBiKH_400x400.jpeg",
                "http://pbs.twimg.com/profile_images/430081238980763649/puqbBiKH_400x400.jpeg"
        };

        nameDev = new String[]{
                "Faiz Siddiqui", "Faiz Siddiqui"
        };

        positionDev = new String[]{
                "Developer", "Developer"
        };

        imageOpenSource = new String[]{
                "http://pbs.twimg.com/profile_images/430081238980763649/puqbBiKH_400x400.jpeg",
                "http://pbs.twimg.com/profile_images/430081238980763649/puqbBiKH_400x400.jpeg",
                "http://pbs.twimg.com/profile_images/430081238980763649/puqbBiKH_400x400.jpeg"
        };

        nameOpenSource = new String[]{
                "FloatingActionButton", "RecyclerView Animators", "Material Dialogs"
        };

        createdByOpenSource = new String[]{
                "Jerzy Chalupski", "Daichi Furiya", "Aidan Follestad"
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_about, container, false);
        ((OtherActivity) getActivity()).setActionBarTitle("About");

        mDevLayoutManger = new LinearLayoutManagerRecyclerView(getActivity());
        mDeveloperAdapter = new ForumPostsCardView(getActivity(), nameDev, imageDev, positionDev, false);
        AlphaInAnimationAdapter alphaDeveloperAdapter = new AlphaInAnimationAdapter(mDeveloperAdapter);
        alphaDeveloperAdapter.setDuration(1000);
        mDeveloperRecyclerView = (RecyclerView) view.findViewById(R.id.developer_list);
        mDeveloperRecyclerView.setHasFixedSize(true);
        mDeveloperRecyclerView.setLayoutManager(mDevLayoutManger);
        mDeveloperRecyclerView.setAdapter(alphaDeveloperAdapter);

        mOpenSourceLayoutMangaer = new LinearLayoutManagerRecyclerView(getActivity());
        mOpenSourceAdapter = new ForumPostsCardView(getActivity(), nameOpenSource, imageOpenSource, createdByOpenSource, false);
        AlphaInAnimationAdapter alphaOpenSourceAdapter = new AlphaInAnimationAdapter(mOpenSourceAdapter);
        alphaOpenSourceAdapter.setDuration(1000);
        mOpenSourceRecyclerView = (RecyclerView) view.findViewById(R.id.open_source_list);
        mOpenSourceRecyclerView.setHasFixedSize(true);
        mOpenSourceRecyclerView.setLayoutManager(mOpenSourceLayoutMangaer);
        mOpenSourceRecyclerView.setAdapter(alphaOpenSourceAdapter);

        return view;
    }
}
