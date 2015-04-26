package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.adapters.CalendarSelectCropCardView;
import app.fragments.Calendar.EnterMonth;
import app.library.CustomJsonObjectRequest;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {

    Button calendarSoilTest;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CalendarSelectCropCardView mAdapter;

    String state;

    String[] image, name;

    private static String URL = "http://buykerz.com/program/v1/api/getCrops";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATE = "state";

    public static SelectCrop newInstance(String state) {
        SelectCrop selectCrop = new SelectCrop();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STATE, state);
        selectCrop.setArguments(bundle);
        return selectCrop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            state = getArguments().getString(KEY_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Choose a crop");

        calendarSoilTest = (Button) view.findViewById(R.id.calendar_soil_test);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_select_crop);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        calendarSoilTest.setOnClickListener(new ToSoilTest());

        setAdapter();
        return view;
    }

    private void setAdapter() {
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        String KEY_SUCCESS = "success";
                        String KEY_MSG = "message";
                        if (response.getString(KEY_SUCCESS) != null) {
                            Boolean res = response.getBoolean(KEY_SUCCESS);
                            if (res) {
                                final ArrayList<String> images = new ArrayList<String>();
                                final ArrayList<String> names = new ArrayList<String>();

                                JSONArray crops = response.getJSONArray("crops");

                                for (Integer i = 0; i <= crops.length() - 1; i++) {
                                    JSONObject crop = crops.getJSONObject(i);
                                    String image = crop.getString(KEY_IMAGE);
                                    String name = crop.getString(KEY_NAME);

                                    images.add(image);
                                    names.add(name);
                                }

                                image = images.toArray(new String[images.size()]);
                                name = names.toArray(new String[names.size()]);

                                mAdapter = new CalendarSelectCropCardView(name, image);
                                mRecyclerView.setAdapter(mAdapter);
                                //mAdapter.SetOnItemClickListener(this);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error fetching crops.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_STATE, state);
                return params;
            }
        };
    }

    private class ToSoilTest implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EnterMonth month = new EnterMonth();
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, month)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
