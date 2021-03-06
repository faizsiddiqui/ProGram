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

import app.adapters.HomeView;
import app.program.ForumActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 11-03-2015.
 */
public class Forum extends Fragment implements HomeView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeView mAdapter;

    String learn, questionAnswer, schemes, jobs, spotlight, awards;

    String[] forumTitle;

    int[] forumImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        learn = getString(R.string.forum_learn);
        questionAnswer = getString(R.string.forum_questionAnswer);
        schemes = getString(R.string.forum_schemes);
        jobs = getString(R.string.forum_jobs);
        spotlight = getString(R.string.forum_spotlight);
        awards = getString(R.string.forum_awards);

        forumTitle = new String[] {
                learn, questionAnswer,
                schemes, jobs,
                spotlight, awards
        };

        forumImage = new int[] {
                R.mipmap.ic_forum_learn,
                R.mipmap.ic_forum_q_a,
                R.mipmap.ic_forum_scheme,
                R.mipmap.ic_forum_job,
                R.mipmap.ic_forum_spotlight,
                R.mipmap.ic_forum_award
        };
    }

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
        if(forumTitle[position].equals(learn)) {
            Learn learn = new Learn();
            getFragmentManager().beginTransaction()
                    .replace(R.id.forumFrame, learn)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (forumTitle[position].equals(questionAnswer)) {
            QuestionAnswer questionAnswer = new QuestionAnswer();
            getFragmentManager().beginTransaction()
                    .replace(R.id.forumFrame, questionAnswer)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (forumTitle[position].equals(schemes)) {
            Schemes schemes = new Schemes();
            getFragmentManager().beginTransaction()
                    .replace(R.id.forumFrame, schemes)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (forumTitle[position].equals(jobs)) {
            Jobs jobs = new Jobs();
            getFragmentManager().beginTransaction()
                    .replace(R.id.forumFrame, jobs)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (forumTitle[position].equals(spotlight)) {
            Spotlight spotlight = new Spotlight();
            getFragmentManager().beginTransaction()
                    .replace(R.id.forumFrame, spotlight)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (forumTitle[position].equals(awards)) {
            Awards awards = new Awards();
            getFragmentManager().beginTransaction()
                    .replace(R.id.forumFrame, awards)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
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
