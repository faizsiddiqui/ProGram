package app.fragments.Calendar;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import static android.app.PendingIntent.getActivity;

/**
 * Created by apple on 4/1/2015.
 */
public class EnterMonth {

    private Spinner spinner;
    private char months;
    private ImageView appImageView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.calendar_enter_month);
        appImageView = (ImageView) view.findViewById(R.id.imageView);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        spinner = (Spinner) view.findViewById(R.id.month_spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,months);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getActivity(), "Month should be entered to generate calendar", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



}
