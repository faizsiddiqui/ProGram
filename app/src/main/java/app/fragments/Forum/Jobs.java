package app.fragments.Forum;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import app.adapters.CardView;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.MainActivity;
import app.program.R;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Created by apple on 3/17/2015.
 */
public class Jobs extends Fragment implements CardView.OnItemClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CardView mAdapter;

    String[] name, intro, icon;

    private static String URL = "http://buykerz.com/program/v1/api/schemes";
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_jobs);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter();

        return view;
    }

    private void setAdapter() {
        Cache cache = VolleySingleton.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            JsonObjectRequest request = new JsonObjectRequest(Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    parseJsonFeed(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error fetching jobs", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if (response.getString(KEY_SUCCESS) != null) {
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if (res) {
                    final ArrayList<String> titles = new ArrayList<String>();
                    final ArrayList<String> description = new ArrayList<String>();
                    final ArrayList<String> images = new ArrayList<String>();

                    JSONArray jobs = response.getJSONArray("schemes");

                    for (Integer i = 0; i <= jobs.length() - 1; i++) {
                        JSONObject job = jobs.getJSONObject(i);
                        String name = job.getString(KEY_NAME);
                        String intro = job.getString(KEY_INTRODUCTION);
                        String img = job.getString(KEY_IMAGE);
                        titles.add(name);
                        description.add(intro);
                        images.add(img);
                    }

                    name = titles.toArray(new String[titles.size()]);
                    intro = description.toArray(new String[description.size()]);
                    icon = images.toArray(new String[images.size()]);

                    mAdapter = new CardView(name, intro, icon);
                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
                    alphaAdapter.setDuration(1000);
                    mRecyclerView.setAdapter(alphaAdapter);
                    mAdapter.SetOnItemClickListener(this);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
    }
}
