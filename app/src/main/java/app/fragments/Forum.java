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

import com.getbase.floatingactionbutton.FloatingActionButton;

import app.adapters.ForumView;
import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 11-03-2015.
 */
public class Forum extends Fragment implements ForumView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumView mAdapter;

    String[] forumTitle = {
            "Question/Answers",
            "Schemes",
            "Awards"
    };

    String[] forumText = {
            "IN PROGRESS",
            "COMPLETED",
            "NOT STARTED"
    };

    int[] image = {
            R.mipmap.drawer_tutorial,
            R.mipmap.drawer_settings,
            R.mipmap.drawer_about
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_forum);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mAdapter = new ForumView(forumTitle, forumText, image);
        mAdapter.SetOnItemClickListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumGrid);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final FloatingActionButton askQuestion = (FloatingActionButton) view.findViewById(R.id.float_askQuestion);
        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskQuestion askQuestion = new AskQuestion();
                getFragmentManager().beginTransaction()
                    .replace(R.id.MainFrame, askQuestion)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (forumTitle[position]) {
            case "Question/Answers":
                AskQuestion askQuestion = new AskQuestion();
                getFragmentManager().beginTransaction()
                    .replace(R.id.MainFrame, askQuestion)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
                break;
            case "Schemes":
                Schemes schemes = new Schemes();
                getFragmentManager().beginTransaction()
                    .replace(R.id.MainFrame, schemes)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
                break;
            case "Awards":
                break;
        }
    }
}
