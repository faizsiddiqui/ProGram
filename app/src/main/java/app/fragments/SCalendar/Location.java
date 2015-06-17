package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Arrays;
import java.util.HashMap;

import app.library.DatabaseHandler;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class Location extends Fragment {

    TextView locationPresent;
    Button locationChange, locationContinue;
    String state;
    DatabaseHandler db;
    private static final String KEY_STATE = "state";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_location_fragment, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle(R.string.toolbar_choose_state);

        db = new DatabaseHandler(getActivity());
        locationPresent = (TextView) view.findViewById(R.id.location_present);
        locationChange = (Button) view.findViewById(R.id.calendar_location_change);
        locationContinue = (Button) view.findViewById(R.id.calendar_location_continue);

        if(db.isUserLoggedIn()) {
            final HashMap<String, String> user = db.getUserDetails();
            locationPresent.setText(user.get(KEY_STATE));
        }

        locationChange.setOnClickListener(new LocationChange());

        locationContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save results in calendar database
                state = locationPresent.getText().toString();
                if (!state.equals("Fetching current state.") && !state.equals("Can't get Address!")) {
                    SoilTestParameters param = SoilTestParameters.newInstance(state);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.calendarFrame, param)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "First Enter any location.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public class LocationChange implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new MaterialDialog.Builder(getActivity())
                    .title("States")
                    .items(R.array.location_list)
                    .itemsCallbackSingleChoice(getCurrentStateIndex(locationPresent.getText().toString()), new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            locationPresent.setText(text);
                        }
                    })
                    .positiveText("Select")
                    .show();
        }

        private int getCurrentStateIndex(String state) {
            return Arrays.asList((getResources().getStringArray(R.array.location_list))).indexOf(state);
        }
    }
}
