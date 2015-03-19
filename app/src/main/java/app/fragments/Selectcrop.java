package app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import app.program.R;

import static android.R.layout.simple_spinner_item;

/**
 * Created by apple on 3/18/2015.
 */
public class Selectcrop extends Fragment implements AdapterView.OnItemSelectedListener{
 Spinner spinner = (Spinner) findViewById(R.id.spinner1);
    private float contentView;
 String [] district= {"bhopal","indore","ujjain"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.calendar_fragment);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.district, simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());


}

    private void findViewById(int spinner1) {
    }

    public void setContentView(int contentView) {
        this.contentView = contentView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"you selected", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



























