package app.fragments.Forum;

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
import app.adapters.HomeView;
import app.program.ForumActivity;
import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 11-03-2015.
 */
public class Forum extends Fragment implements HomeView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeView mAdapter;

    String[] forumTitle = {
            "Question/Answers",
            "Schemes",
            "Jobs",
            "Awards"
    };

    int[] forumImage = {
            R.mipmap.drawer_tutorial,
            R.mipmap.drawer_settings,
            R.mipmap.drawer_about,
            R.mipmap.drawer_about
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_forum);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter = new HomeView(forumTitle, forumImage);
        mAdapter.SetOnItemClickListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        getFloatingActionButtonView(view);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (forumTitle[position]) {
            case "Question/Answers":
                QuestionAnswer questionAnswer = new QuestionAnswer();
                getFragmentManager().beginTransaction()
                        .replace(R.id.forumFrame, questionAnswer)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Schemes":
                Schemes schemes = new Schemes();
                getFragmentManager().beginTransaction()
                        .replace(R.id.forumFrame, schemes)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Awards":
                Awards awards = new Awards();
                getFragmentManager().beginTransaction()
                        .replace(R.id.forumFrame, awards)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Jobs":
                Jobs jobs = new Jobs();
                getFragmentManager().beginTransaction()
                        .replace(R.id.forumFrame, jobs)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    } //onItemClick

    public void getFloatingActionButtonView(View view) {
        final FloatingActionButton askQuestion = (FloatingActionButton) view.findViewById(R.id.float_askQuestion);
        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskQuestion askQuestion = new AskQuestion();
                getFragmentManager().beginTransaction()
                        .replace(R.id.forumFrame, askQuestion)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
    } //getFloatingActionButtonView
}
