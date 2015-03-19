package app.fragments.Calendar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import app.program.R;

import static android.R.layout.simple_spinner_item;

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

        View view = inflater.inflate(R.layout.select_crop_fragment, container, false);
        statePrompt = (Spinner) view.findViewById(R.id.statePrompt);

        return view;
    }
}



























