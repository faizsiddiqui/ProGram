package app.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.adapters.CardView;
import app.library.VolleySingleton;
import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 22-02-2015.
 */
public class Schemes extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardView mAdapter;

    String[] titles, descriptions, images;
    String[] name, intro, icon;

    private static String KEY_ERROR = "error";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATE = "state";
    private static final String KEY_CENTRAL = "central";
    private static final String KEY_COMPONENTS = "components";
    private static final String KEY_INTRODUCTION = "introduction";
    private static final String KEY__TARGET = "target_group";
    private static final String KEY__ELIGIBILITY = "eligibility_criteria";
    private static final String KEY__SUPPORT = "support_provided";
    private static final String KEY__CONTACT = "contact_details";
    private static final String KEY_PUBLISHED = "published";
    private static final String KEY_RELEASED = "released";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.recycler_view, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_schemes);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String URL = "http://buykerz.com/program/v1/api/schemes";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String KEY_SUCCESS = "success";
                            String KEY_MSG = "message";
                            if (response.getString(KEY_SUCCESS) != null) {
                                Boolean res = response.getBoolean(KEY_SUCCESS);
                                if (res) {
                                    final ArrayList<String> titles = new ArrayList<String>();
                                    final ArrayList<String> description = new ArrayList<String>();
                                    final ArrayList<String> images = new ArrayList<String>();

                                    JSONArray schemes = response.getJSONArray("schemes");

                                    for (Integer i = 0; i <= schemes.length() - 1; i++) {
                                        JSONObject scheme = schemes.getJSONObject(i);
                                        String name = scheme.getString(KEY_NAME);
                                        String intro = scheme.getString(KEY_INTRODUCTION);
                                        String img = scheme.getString(KEY_IMAGE);
                                        titles.add(name);
                                        description.add(intro);
                                        images.add(img);
                                    }

                                    name = titles.toArray(new String[titles.size()]);
                                    intro = description.toArray(new String[description.size()]);
                                    icon = images.toArray(new String[images.size()]);

                                    mAdapter = new CardView(name, intro, icon);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mAdapter.SetOnItemClickListener(new CardView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            Toast.makeText(getActivity(), "Card clicked.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error fetching Schemes.", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
        return layout;
    }
}
