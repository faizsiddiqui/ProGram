package app.fragments.SCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import app.library.VolleySingleton;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {

    TextView calendarSoilTest;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CalendarSelectCropCardView mAdapter;

    String selectedState;

    String[] image, name;

    private static String URL = "http://buykerz.com/program/v1/api/crops";
    private static final String KEY_ID = "id";
    private static final String KEY_CROP_NAME = "crop_name";
    private static final String KEY_CROP_VARIETY_ID = "variety_id";
    private static final String KEY_CROP_VARIETY_NAME = "variety_name";
    private static final String KEY_CROP_IMAGE = "image";
    private static final String KEY_CROP_STATES = "states";
    private static final String KEY_SOWING_START = "sowing_start";
    private static final String KEY_SOWING_CONDITION = "sowing_condition";
    private static final String KEY_MATURITY = "maturity";
    private static final String KEY_YIELD = "yield";
    private static final String KEY_ORIGINATOR = "originator";
    private static final String KEY_RELEASE_YEAR = "release_year";
    private static final String KEY_PH = "pH";
    private static final String KEY_EC = "electrical_conductivity";
    private static final String KEY_OC = "organic_carbon";
    private static final String KEY_N = "nitrogen";
    private static final String KEY_P = "phosphorus";
    private static final String KEY_K = "potassium";
    private static final String KEY_ZN = "zinc";
    private static final String KEY_CU = "copper";
    private static final String KEY_FE = "iron";
    private static final String KEY_MN = "manganese";
    private static final String KEY_B = "boron";
    private static final String KEY_S = "sulphur";
    private static final String KEY_CROP_TYPE = "crop_type";

    public static SelectCrop newInstance(String selectedState) {
        SelectCrop selectCrop = new SelectCrop();
        Bundle bundle = new Bundle();
        bundle.putString("state", selectedState);
        selectCrop.setArguments(bundle);
        return selectCrop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedState = getArguments().getString("state");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Choose a crop");

        calendarSoilTest = (TextView) view.findViewById(R.id.calendar_soil_test);

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
                                    String image = crop.getString(KEY_CROP_IMAGE);
                                    String name = crop.getString(KEY_CROP_NAME);

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
                params.put("state", selectedState);
                return params;
            }
        };
        VolleySingleton.getInstance().addToRequestQueue(request);
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
