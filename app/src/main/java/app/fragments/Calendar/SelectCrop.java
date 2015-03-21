package app.fragments.Calendar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import app.program.R;

/**
 * Created by apple on 3/18/2015.
 */
public class SelectCrop extends Fragment {

    private Spinner statePrompt;

    private float contentView;
    String [] district= {"Bhopal","Indore","Ujjain"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_select_crop_fragment, container, false);
        statePrompt = (Spinner) view.findViewById(R.id.statePrompt);

        return view;
    }
}



























