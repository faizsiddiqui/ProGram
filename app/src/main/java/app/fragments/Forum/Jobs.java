package app.fragments.Forum;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import app.adapters.CardView;
import app.adapters.ForumDetailPostView;
import app.adapters.ForumPostsCardView;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;
import app.widgets.LinearLayoutManagerRecyclerView;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Created by apple on 3/17/2015.
 */
public class Jobs extends Fragment implements ForumPostsCardView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumPostsCardView mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog pDialog;

    String[] name, state, location, offeredByCompany, image, description, lastDate, contact, publishedDate;

    private static String URL = "http://buykerz.com/program/v1/api/jobs";
    public static final String KEY_ID = "id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NAME = "name";
    public static final String KEY_STATE = "state";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_OFFERED_BY = "offered_by";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LAST_DATE = "last_date";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_PUBLISHED = "published";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_jobs);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading jobs...");
        pDialog.setCancelable(false);

        setAdapter(true);
        getFloatingActionButtonView(view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(false);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        return view;
    }

    private void setAdapter(boolean checkCache) {
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
        if (!pDialog.isShowing())
            pDialog.show();
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
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if (response.getString(KEY_SUCCESS) != null) {
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if (res) {

                    final ArrayList<String> names = new ArrayList<String>();
                    final ArrayList<String> images = new ArrayList<String>();
                    final ArrayList<String> states = new ArrayList<String>();
                    final ArrayList<String> locations = new ArrayList<String>();
                    final ArrayList<String> offeredByCompanies = new ArrayList<String>();
                    final ArrayList<String> descriptions = new ArrayList<String>();
                    final ArrayList<String> lastDates = new ArrayList<String>();
                    final ArrayList<String> contacts = new ArrayList<String>();
                    final ArrayList<String> publishedDates = new ArrayList<String>();

                    JSONArray jobs = response.getJSONArray("jobs");

                    for (Integer i = 0; i <= jobs.length() - 1; i++) {
                        JSONObject job = jobs.getJSONObject(i);

                        String name = job.getString(KEY_NAME);
                        String image = job.getString(KEY_IMAGE);
                        String state = job.getString(KEY_STATE);
                        String location = job.getString(KEY_LOCATION);
                        String offeredByCompany = job.getString(KEY_OFFERED_BY);
                        String description = job.getString(KEY_DESCRIPTION);
                        String lastDate = job.getString(KEY_LAST_DATE);
                        String contact = job.getString(KEY_CONTACT);
                        String publishedDate = job.getString(KEY_PUBLISHED);

                        images.add(image);
                        names.add(name);
                        states.add(state);
                        locations.add(location);
                        offeredByCompanies.add(offeredByCompany);
                        descriptions.add(description);
                        lastDates.add(lastDate);
                        contacts.add(contact);
                        publishedDates.add(publishedDate);
                    }

                    image = images.toArray(new String[images.size()]);
                    name = names.toArray(new String[names.size()]);
                    state = states.toArray(new String[states.size()]);
                    location = locations.toArray(new String[locations.size()]);
                    offeredByCompany = offeredByCompanies.toArray(new String[offeredByCompanies.size()]);
                    description = descriptions.toArray(new String[descriptions.size()]);
                    lastDate = lastDates.toArray(new String[lastDates.size()]);
                    contact = contacts.toArray(new String[contacts.size()]);
                    publishedDate = publishedDates.toArray(new String[publishedDates.size()]);

                    mAdapter = new ForumPostsCardView(getActivity(), name, image, state, publishedDate);
                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
                    alphaAdapter.setDuration(1000);
                    mRecyclerView.setAdapter(alphaAdapter);
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
        JobsPost post = JobsPost.newInstance(image[position], name[position], state[position],
                location[position], offeredByCompany[position], description[position],
                lastDate[position], contact[position], publishedDate[position]);
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

    public static class JobsPost extends Fragment {
        NetworkImageView mImage;
        ImageLoader mImageLoader;
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        ForumDetailPostView mAdapter;
        TextView postName, postState;

        String[] titles, descriptions;

        String name, state, location, offeredByCompany, image, description, lastDate, contact, publishedDate;

        public static JobsPost newInstance(String image, String name, String state,
                                           String location, String offeredByCompany, String description,
                                           String lastDate, String contact, String publishedDate){
            JobsPost post = new JobsPost();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_IMAGE, image);
            bundle.putString(KEY_NAME, name);
            bundle.putString(KEY_STATE, state);
            bundle.putString(KEY_LOCATION, location);
            bundle.putString(KEY_OFFERED_BY, offeredByCompany);
            bundle.putString(KEY_DESCRIPTION, description);
            bundle.putString(KEY_LAST_DATE, lastDate);
            bundle.putString(KEY_CONTACT, contact);
            bundle.putString(KEY_PUBLISHED, publishedDate);
            post.setArguments(bundle);
            return post;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImageLoader = VolleySingleton.getInstance().getImageLoader();
            if (getArguments() != null) {
                image = getArguments().getString(KEY_IMAGE);
                name = getArguments().getString(KEY_NAME);
                state = getArguments().getString(KEY_STATE);
                location = getArguments().getString(KEY_LOCATION);
                offeredByCompany = getArguments().getString(KEY_OFFERED_BY);
                description = getArguments().getString(KEY_DESCRIPTION);
                lastDate = getArguments().getString(KEY_LAST_DATE);
                contact = getArguments().getString(KEY_CONTACT);
                publishedDate = getArguments().getString(KEY_PUBLISHED);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.forum_post_detail_fragment, container, false);
            ((ForumActivity) getActivity()).setActionBarTitle(name);

            mImage = (NetworkImageView) view.findViewById(R.id.post_image);
            postName = (TextView) view.findViewById(R.id.post_name);
            postState = (TextView) view.findViewById(R.id.post_category);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.detail_list);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManagerRecyclerView(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            titles = new String[]{
                    KEY_OFFERED_BY, KEY_LOCATION, KEY_DESCRIPTION,
                    KEY_CONTACT
            };

            descriptions = new String[]{
                    offeredByCompany, location, description,
                    contact
            };

            mImage.setImageUrl(image, mImageLoader);
            postName.setText(name);
            postState.setText(state);
            mAdapter = new ForumDetailPostView(titles, descriptions);
            mRecyclerView.setAdapter(mAdapter);

            return view;
        }
    }
}
