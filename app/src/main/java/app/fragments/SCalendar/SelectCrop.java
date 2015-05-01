package app.fragments.SCalendar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import app.adapters.ForumPostsCardView;
import app.library.CropHandler;
import app.library.CustomJsonObjectRequest;
import app.library.VolleySingleton;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment implements ForumPostsCardView.OnItemClickListener {

    TextView calendarSoilTest;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ForumPostsCardView mAdapter;
    private ProgressDialog pDialog;

    CropHandler ch;

    String selectedState, ph, ec, oc, n, p, k, z, c, i, m, s;

    String[] id, image, name;

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

    public static SelectCrop newInstance(String selectedState ,String ph, String ec, String oc, String n, String p, String k,String z,String c, String i, String m, String s) {
        SelectCrop selectCrop = new SelectCrop();
        Bundle bundle = new Bundle();
        bundle.putString("state", selectedState);
        bundle.putString("pH",ph);
        bundle.putString("electrical_conductivity",ec);
        bundle.putString("organic_carbon",oc);
        bundle.putString("nitrogen",n);
        bundle.putString("phosphorus",p);
        bundle.putString("potassium",k);
        bundle.putString("zinc",z);
        bundle.putString("copper",c);
        bundle.putString("iron",i);
        bundle.putString("manganese",m);
        bundle.putString("sulphur",s);
        selectCrop.setArguments(bundle);
        return selectCrop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedState = getArguments().getString("state");
            ph = getArguments().getString("pH");
            ec = getArguments().getString("electrical_conductivity");
            oc = getArguments().getString("organic_carbon");
            n = getArguments().getString("nitrogen");
            p = getArguments().getString("phosphorus");
            k = getArguments().getString("potassium");
            z = getArguments().getString("zinc");
            c = getArguments().getString("copper");
            i = getArguments().getString("iron");
            m = getArguments().getString("manganese");
            s = getArguments().getString("sulphur");
        }

        ch = new CropHandler(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading crops...");
        pDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_calendar_select_crop, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle(R.string.toolbar_choose_crop);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.calendar_select_crop);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter();
        return view;
    }

    private void setAdapter() {
        if (!pDialog.isShowing())
            pDialog.show();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error fetching crops.", Toast.LENGTH_SHORT).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
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

    private void parseJsonFeed(JSONObject response) {
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if (response.getString(KEY_SUCCESS) != null) {
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if (res) {
                    final ArrayList<String> ids = new ArrayList<String>();
                    final ArrayList<String> images = new ArrayList<String>();
                    final ArrayList<String> names = new ArrayList<String>();

                    JSONArray crops = response.getJSONArray("crops");

                    for (Integer i = 0; i <= crops.length() - 1; i++) {
                        JSONObject crop = crops.getJSONObject(i);
                        String id = crop.getString(KEY_ID);
                        String image = crop.getString(KEY_CROP_IMAGE);
                        String name = crop.getString(KEY_CROP_NAME);

                        ids.add(id);
                        images.add(image);
                        names.add(name);
                    }

                    id = ids.toArray(new String[ids.size()]);
                    image = images.toArray(new String[images.size()]);
                    name = names.toArray(new String[names.size()]);

                    mAdapter = new ForumPostsCardView(getActivity(), name, image, name, false);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.SetOnItemClickListener(this);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {
        pDialog.setMessage("Creating calendar...");
        if (!pDialog.isShowing())
            pDialog.show();

        String SELECTED_CROP_URL = "http://buykerz.com/program/v1/api/selectCrop";
        String SELECTED_CROP_NAME = "crop_name";
        String SELECTED_CROP_VARIETY_ID = "variety_id";
        String SELECTED_CROP_VARIETY_NAME = "variety_name";
        String SELECTED_CROP_DURATION = "time_duration";
        String SELECTED_CROP_DURATION_SOWING = "sowing";
        String SELECTED_CROP_DURATION_IRRIGATION = "irrigation";
        String SELECTED_CROP_DURATION_FERTILIZATION = "fertilization";
        String SELECTED_CROP_DURATION_PESTICIDE = "pesticide";
        String SELECTED_CROP_DURATION_HARVESTING = "harvesting";

        final String selectCropId = id[position];

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Method.POST, SELECTED_CROP_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        String KEY_SUCCESS = "success";
                        String KEY_MSG = "message";
                        if (response.getString(KEY_SUCCESS) != null) {
                            Boolean res = response.getBoolean(KEY_SUCCESS);
                            if (res) {
                                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                                String sday, smonth, syear , eday, emonth, eyear, evname, evdesc;
                                sday= String.valueOf(calendar.get(Calendar.DATE));
                                smonth = String.valueOf(calendar.get(Calendar.MONTH));
                                syear = String.valueOf(calendar.get(Calendar.YEAR));
                                eday = "5";
                                emonth = "5";
                                eyear = "2014";
                                evname = "Sowing";
                                evdesc = "Today is sowing day";
                                //ch.addCrop(sday, smonth, syear, eday, emonth, eyear, evname, evdesc);
                                //HashMap<String, String> cropDetails = ch.getCropDetails();
                                //String calendarId = cropDetails.get("id");
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                MainCalendar cal = new MainCalendar();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.calendarFrame, cal)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error fetching crop details.", Toast.LENGTH_SHORT).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("crop_id", "1");
                return params;
            }
        };
        VolleySingleton.getInstance().addToRequestQueue(request);
    }
}