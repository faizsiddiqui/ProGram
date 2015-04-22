package app.fragments.SCalendar;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class Location extends Fragment {

    TextView locationPresent;
    Button locationChange, locationContinue;
    String latitude, longitude, state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_location_fragment, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Choose a State");
        locationPresent = (TextView) view.findViewById(R.id.location_present);
        locationChange = (Button) view.findViewById(R.id.calendar_location_change);
        locationContinue = (Button) view.findViewById(R.id.calendar_location_continue);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
                locationPresent.setText(GetState(latitude, longitude));
            }
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            public void onProviderEnabled(String s) {}
            public void onProviderDisabled(String s) {}
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        locationChange.setOnClickListener(new LocationChange());

        locationContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save results in calendar database
                state = locationPresent.getText().toString();
                if (!state.equals("Fetching current state.") && !state.equals("Can't get Address!")) {
                    SelectCrop crop = SelectCrop.newInstance(state);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.calendarFrame, crop)
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

    public String GetState(String lat, String lon) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
        String state;
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                state = returnedAddress.getAdminArea();
            } else {
                state = "No State Found!";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            state = "Can't get Address!";
        }
        return state;
    }
}
