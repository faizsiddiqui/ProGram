package app.fragments.Calendar;

  import android.support.v4.app.Fragment;
  import android.os.Bundle;
  import android.support.annotation.Nullable;
  import android.support.v7.widget.LinearLayoutManager;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.AdapterView;
  import android.widget.ArrayAdapter;
  import android.widget.Spinner;
  import android.widget.Toast;

  import app.program.MainActivity;
  import app.program.R;

/**
 * Created by apple on 3/21/2015.
 */
public class EnterLocation extends Fragment {

    private float contentView;
    private Spinner locationPrompt;
    private Spinner spinner;

    public Spinner getlocationPrompt() {
        return locationPrompt;
    }

    String [] locations = {
            "loc 1",
            "loc 2"
    };

    String [] EnterlocationTitle = {
            "Enter location",
            "Recommended Crops"
    };

    String [] EnterlocationText = {
            "In Progress",
            "In Progress"
    };




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_enter_location_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_enter_location);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        spinner = (Spinner) view.findViewById(R.id.location_spinner);
        // RecyclerView RecyclerView = (RecyclerView) view.findViewById(R.id.CalendarRecyclerView);
        //  RecyclerView.setHasFixedSize(true);
        //  RecyclerView.setLayoutManager(LayoutManager);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getActivity(), "Cannot proceed without entering location", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}






