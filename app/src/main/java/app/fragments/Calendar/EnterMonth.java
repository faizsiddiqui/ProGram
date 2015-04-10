package app.fragments.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import app.program.MainActivity;
import app.program.R;

/**
 * Created by apple on 4/1/2015.
 */
public class EnterMonth extends Fragment {
    public String[] Month_list;
    private Spinner spinner;
    private ImageView image;
    public TypedArray Month_image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_enter_month_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.calendar_enter_month);
        image = (ImageView)view.findViewById(R.id.months_image);
        Month_image = getResources().obtainTypedArray(R.array.Month_image);
        Month_list = getResources().getStringArray(R.array.Month_List);
        spinner = (Spinner) view.findViewById(R.id.month_spinner);
        image.setImageResource(Month_image.getResourceId(spinner.getSelectedItemPosition(), -1));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,Month_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getActivity(), "Month should be entered to generate calendar", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
