package app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.TintEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 06-03-2015.
 */
public class AskQuestion extends Fragment {
    private TintEditText questionTitle, questionDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.ask_question, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_question);
        questionTitle = (TintEditText) layout.findViewById(R.id.questionTitle);
        questionDescription = (TintEditText) layout.findViewById(R.id.questionDescription);
        return layout;
    }
}
