package app.fragments.Forum;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import app.adapters.SchemesCardView;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

/**
 * Not for public use
 * Created by FAIZ on 22-02-2015.
 */
public class Schemes extends Fragment implements SchemesCardView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SchemesCardView mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    String[] image, name, category, introduction, component, support, eligibility, contact, releaseDate;

    private static String URL = "http://buykerz.com/program/v1/api/schemes";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATE = "state";
    private static final String KEY_CENTRAL = "central";
    private static final String KEY_COMPONENTS = "components";
    private static final String KEY_INTRODUCTION = "introduction";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_ELIGIBILITY = "eligibility_criteria";
    private static final String KEY_SUPPORT = "support_provided";
    private static final String KEY_CONTACT = "contact_details";
    private static final String KEY_PUBLISHED = "published";
    private static final String KEY_RELEASED = "released";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_schemes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter(true); //set Adapter with cache
        getFloatingActionButtonView(view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(false); //set Adapter without cache
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        return view;
    }

    private void setAdapter(Boolean checkCache) {
        if (checkCache) {
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
                requestWhenCacheMiss();
            }
        } else {
            requestWhenCacheMiss();
        }
    }

    private void requestWhenCacheMiss() {
        JsonObjectRequest request = new JsonObjectRequest(Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error fetching Schemes.", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if (response.getString(KEY_SUCCESS) != null) {
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if (res) {
                    final ArrayList<String> images = new ArrayList<String>();
                    final ArrayList<String> names = new ArrayList<String>();
                    final ArrayList<String> categories = new ArrayList<String>();
                    final ArrayList<String> introductions = new ArrayList<String>();
                    final ArrayList<String> components = new ArrayList<String>();
                    final ArrayList<String> supports = new ArrayList<String>();
                    final ArrayList<String> eligibilitys = new ArrayList<String>();
                    final ArrayList<String> contacts = new ArrayList<String>();
                    final ArrayList<String> dates = new ArrayList<String>();

                    JSONArray schemes = response.getJSONArray("schemes");

                    for (Integer i = 0; i <= schemes.length() - 1; i++) {
                        JSONObject scheme = schemes.getJSONObject(i);
                        String image = scheme.getString(KEY_IMAGE);
                        String name = scheme.getString(KEY_NAME);
                        String category = scheme.getString(KEY_CATEGORY);
                        String introduction = scheme.getString(KEY_INTRODUCTION);
                        String component = scheme.getString(KEY_COMPONENTS);
                        String support = scheme.getString(KEY_SUPPORT);
                        String eligibility = scheme.getString(KEY_ELIGIBILITY);
                        String contact = scheme.getString(KEY_CONTACT);
                        String release = scheme.getString(KEY_RELEASED);

                        images.add(image);
                        names.add(name);
                        categories.add(category);
                        introductions.add(introduction);
                        components.add(component);
                        supports.add(support);
                        eligibilitys.add(eligibility);
                        contacts.add(contact);
                        dates.add(release);
                    }

                    image = images.toArray(new String[images.size()]);
                    name = names.toArray(new String[names.size()]);
                    category = categories.toArray(new String[categories.size()]);
                    introduction = introductions.toArray(new String[introductions.size()]);
                    component = components.toArray(new String[components.size()]);
                    support = supports.toArray(new String[supports.size()]);
                    eligibility = eligibilitys.toArray(new String[eligibilitys.size()]);
                    contact = contacts.toArray(new String[contacts.size()]);
                    releaseDate = dates.toArray(new String[dates.size()]);

                    mAdapter = new SchemesCardView(name, image, category, releaseDate);
                    SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
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
        SchemePost post = SchemePost.newInstance(image[position], name[position], category[position], introduction[position], component[position], support[position], eligibility[position], contact[position]);
        getFragmentManager().beginTransaction()
                .replace(R.id.forumFrame, post)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public void getFloatingActionButtonView(View view) {
        final FloatingActionButton askQuestion = (FloatingActionButton) view.findViewById(R.id.float_askQuestion);
        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskQuestion askQuestion = new AskQuestion();
                getFragmentManager().beginTransaction()
                        .replace(R.id.forumFrame, askQuestion)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
