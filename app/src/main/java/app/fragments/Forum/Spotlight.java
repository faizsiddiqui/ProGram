package app.fragments.Forum;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.volley.Request;
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

import app.adapters.ForumDetailPostView;
import app.adapters.ForumPostsCardView;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;
import app.widgets.LinearLayoutManagerRecyclerView;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Not for public use
 * Created by FAIZ on 26-04-2015.
 */
public class Spotlight extends Fragment implements ForumPostsCardView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumPostsCardView mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog pDialog;

    String[] image, title, state, farmerDetail, farmerStory, publishedDate;

    private static String URL = "http://buykerz.com/program/v1/api/spotlight";
    private static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_STATE = "state";
    public static final String KEY_FARMER_DETAIL = "farmer_details";
    public static final String KEY_DESCRIPTION = "success_story";
    public static final String KEY_PUBLISHED = "published";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_spotlight);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading stories...");
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
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
                Toast.makeText(getActivity(), "Error fetching stories.", Toast.LENGTH_SHORT).show();
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
                    final ArrayList<String> titles = new ArrayList<String>();
                    final ArrayList<String> states = new ArrayList<String>();
                    final ArrayList<String> farmerDetails = new ArrayList<String>();
                    final ArrayList<String> farmerStories = new ArrayList<String>();
                    final ArrayList<String> publishedDates = new ArrayList<String>();

                    JSONArray stories = response.getJSONArray("stories");

                    for (Integer i = 0; i <= stories.length() - 1; i++) {
                        JSONObject story = stories.getJSONObject(i);

                        String image = story.getString(KEY_IMAGE);
                        String title = story.getString(KEY_TITLE);
                        String state = story.getString(KEY_STATE);
                        String farmerDetail = story.getString(KEY_FARMER_DETAIL);
                        String farmerStory = story.getString(KEY_DESCRIPTION);
                        String publishedDate = story.getString(KEY_PUBLISHED);

                        images.add(image);
                        titles.add(title);
                        states.add(state);
                        farmerDetails.add(farmerDetail);
                        farmerStories.add(farmerStory);
                        publishedDates.add(publishedDate);

                    }

                    image = images.toArray(new String[images.size()]);
                    title = titles.toArray(new String[titles.size()]);
                    state = states.toArray(new String[states.size()]);
                    farmerDetail = farmerDetails.toArray(new String[farmerDetails.size()]);
                    farmerStory = farmerStories.toArray(new String[farmerStories.size()]);
                    publishedDate = publishedDates.toArray(new String[publishedDates.size()]);

                    mAdapter = new ForumPostsCardView(getActivity(), title, image, state, publishedDate);
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
        SpotlightPost post = SpotlightPost.newInstance(image[position], title[position], state[position],
                farmerDetail[position], farmerStory[position], publishedDate[position]);
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

    public static class SpotlightPost extends Fragment {

        NetworkImageView mImage;
        ImageLoader mImageLoader;
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        ForumDetailPostView mAdapter;
        TextView postName, postState;

        String[] titles, descriptions;

        String title, image, state, farmerDetail, farmerStory, publishedDate;

        public static SpotlightPost newInstance(String image, String title, String state,
                                                String farmerDetail, String farmerStory, String publishedDate) {
            SpotlightPost post = new SpotlightPost();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_IMAGE, image);
            bundle.putString(KEY_TITLE, title);
            bundle.putString(KEY_STATE, state);
            bundle.putString(KEY_FARMER_DETAIL, farmerDetail);
            bundle.putString(KEY_DESCRIPTION, farmerStory);
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
                title = getArguments().getString(KEY_TITLE);
                state = getArguments().getString(KEY_STATE);
                farmerDetail = getArguments().getString(KEY_FARMER_DETAIL);
                farmerStory = getArguments().getString(KEY_DESCRIPTION);
                publishedDate = getArguments().getString(KEY_PUBLISHED);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.forum_post_detail_fragment, container, false);
            ((ForumActivity) getActivity()).setActionBarTitle(title);

            mImage = (NetworkImageView) view.findViewById(R.id.post_image);
            postName = (TextView) view.findViewById(R.id.post_name);
            postState = (TextView) view.findViewById(R.id.post_category);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.detail_list);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManagerRecyclerView(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            titles = new String[]{
                    KEY_FARMER_DETAIL, KEY_DESCRIPTION
            };

            descriptions = new String[]{
                    farmerDetail, farmerStory
            };

            mImage.setImageUrl(image, mImageLoader);
            postName.setText(title);
            postState.setText(state);
            mAdapter = new ForumDetailPostView(titles, descriptions);
            mRecyclerView.setAdapter(mAdapter);

            return view;
        }
    }

}
