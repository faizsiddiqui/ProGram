package app.fragments.SCalendar;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import app.adapters.CalendarCropView;
import app.library.CropHandler;
import app.library.CustomJsonObjectRequest;
import app.library.VolleySingleton;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 17-04-2015.
 */
public class SelectCrop extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CalendarCropView mAdapter;
    private ProgressDialog pDialog;

    CropHandler ch;

    String selectedState;
    String[] parameters = new String[]{"pH", "electrical_conductivity", "organic_carbon", "nitrogen", "phosphorus",
            "potassium", "zinc", "copper", "iron", "manganese", "sulphur"};
    double parameterMarks[] = new double[]{10, 9.2, 9, 5, 4.7, 4.5, 2, 1.9, 1.8, 1.7, 1.6};
    double[] userValues, marks;
    int length;
    double y, k;

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

    public static SelectCrop newInstance(String selectedState, double userValues[]) {
        SelectCrop selectCrop = new SelectCrop();
        Bundle bundle = new Bundle();
        bundle.putString("state", selectedState);
        bundle.putDoubleArray("userValues", userValues);
        selectCrop.setArguments(bundle);
        return selectCrop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedState = getArguments().getString("state");
            userValues = getArguments().getDoubleArray("userValues");
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
                    new Sort(response).execute();
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

    class Sort extends AsyncTask<Void, Void, Wrapper> {

        JSONObject response;

        public Sort(JSONObject response) {
            this.response = response;
        }

        @Override
        protected Wrapper doInBackground(Void... voids) {
            return parseJsonFeed(response);
        }

        @Override
        protected void onPostExecute(Wrapper data) {
            super.onPostExecute(data);
            if (pDialog.isShowing())
                pDialog.dismiss();
            mAdapter = new CalendarCropView(getActivity(), data.name, data.image, data.marks);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.SetOnItemClickListener(new GoToMainCalendar());
        }

        private Wrapper parseJsonFeed(JSONObject response) {
            Wrapper data = new Wrapper();
            try {
                String KEY_SUCCESS = "success";
                String KEY_MSG = "message";
                if (response.getString(KEY_SUCCESS) != null) {
                    Boolean res = response.getBoolean(KEY_SUCCESS);
                    if (res) {
                        final ArrayList<String> ids = new ArrayList<>();
                        final ArrayList<String> images = new ArrayList<>();
                        final ArrayList<String> names = new ArrayList<>();

                        JSONArray crops = response.getJSONArray("crops");
                        marks = new double[crops.length()];

                        for (Integer i = 0; i <= crops.length() - 1; i++) {

                            marks[i] = 0;
                            JSONObject crop = crops.getJSONObject(i);
                            String id = crop.getString(KEY_ID);
                            String image = crop.getString(KEY_CROP_IMAGE);
                            String name = crop.getString(KEY_CROP_NAME);

                            ids.add(id);
                            images.add(image);
                            names.add(name);

                            for (int j = 0; j < 11; j++) {

                                JSONArray paramData = crop.getJSONArray(parameters[j]);
                                Double lowestRange = paramData.getDouble(0);
                                Double lowRange = paramData.getDouble(1);
                                Double mid = paramData.getDouble(2);
                                Double highRange = paramData.getDouble(3);
                                Double highestRange = paramData.getDouble(4);
                                Double multiplierMin = paramData.getDouble(5);
                                Double multiplierMax = paramData.getDouble(6);

                                if (userValues[j] != 999) {
                                    if (userValues[j] < lowRange && userValues[j] > highRange) {
                                        if (userValues[j] < mid) {
                                            k = 1 / ((lowestRange - mid) * (lowestRange - mid));
                                        } else {
                                            k = 1 / ((highestRange - mid) * (highestRange - mid));
                                        }
                                    } else {
                                        if (userValues[i] < mid) {
                                            k = (multiplierMin - 1) / ((lowRange - mid) * (lowRange - mid));
                                        } else {
                                            k = (multiplierMax - 1) / ((highRange - mid) * (highRange - mid));
                                        }
                                    }
                                    y = 1 - (((userValues[j] - mid) * (userValues[j] - mid)) / k);
                                    marks[i] += parameterMarks[j] * y;
                                }
                            }
                        }

                        id = ids.toArray(new String[ids.size()]);
                        image = images.toArray(new String[images.size()]);
                        name = names.toArray(new String[names.size()]);

                        quickSort(0, id.length - 1);

                        data.name = name;
                        data.image = image;
                        data.marks = marks;

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        private void quickSort(int lowerIndex, int higherIndex) {
            int i = lowerIndex + 1;
            int j = higherIndex;
            double pivot = marks[0];
            while (i <= j) {
                while (marks[i] < pivot) {
                    i++;
                }
                while (marks[j] > pivot) {
                    j--;
                }
                if (i <= j) {
                    exchangeNumbers(i, j);
                    i++;
                    j--;
                }
            }

            exchangeNumbers(lowerIndex, j);

            if (lowerIndex < j) {
                quickSort(lowerIndex, j - 1);
            }
            if (i < higherIndex) {
                quickSort(i, higherIndex);
            }
        }

        private void exchangeNumbers(int i, int j) {
            String tempData;
            double tempMarks = marks[i];
            marks[i] = marks[j];
            marks[j] = tempMarks;

            tempData = id[i];
            id[i] = id[j];
            id[j] = tempData;

            tempData = image[i];
            image[i] = image[j];
            image[j] = tempData;

            tempData = name[i];
            name[i] = name[j];
            name[j] = tempData;
        }
    }

    public class GoToMainCalendar implements CalendarCropView.OnItemClickListener {
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
                                    String sday, smonth, syear, eday, emonth, eyear, evname, evdesc;
                                    sday = String.valueOf(calendar.get(Calendar.DATE));
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
                    params.put("crop_id", selectCropId);
                    return params;
                }
            };
            VolleySingleton.getInstance().addToRequestQueue(request);
        }
    }

    private class Wrapper {
        double[] marks;
        String[] image, name;
    }

}