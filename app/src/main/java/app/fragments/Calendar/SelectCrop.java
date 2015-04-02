package app.fragments.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import app.program.R;

/**
 * Created by apple on 3/18/2015.
 */
public class SelectCrop extends Fragment {



    private Spinner cropPrompt;

    private float contentView;
    String [] crop= {"wheat","rice","maize"};


    public Spinner getCropPrompt() {
        return cropPrompt;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_select_crop_fragment, container, false);
        //cropPrompt = (Spinner) view.findViewById(R.id.cropPrompt);
        return view;
    }
}



























