package app.fragments.Forum;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.R;


/**
 * Created by apple on 4/27/2015.
 */
public class Learn_of extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.forum_learn_of,container,false);
        return v;
    }
}
