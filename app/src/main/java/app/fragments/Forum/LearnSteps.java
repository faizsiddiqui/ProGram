package app.fragments.Forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import app.adapters.ForumPostsCardView;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Created by apple on 4/27/2015.
 */
public class LearnSteps extends Fragment implements ForumPostsCardView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumPostsCardView mAdapter;

    String[] name, detail, image;

    private static String URL = "http://buykerz.com/program/v1/api/learn/steps";
    private static final String KEY_STEP_ID = "id";
    public static final String KEY_STEP_NAME = "step_name";
    public static final String KEY_STEP_DETAIL = "step_details";
    public static final String KEY_STEP_IMAGE = "image_url";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_learn_steps, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.learn_steps);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter(true);

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
                Toast.makeText(getActivity(), "Error fetching steps.", Toast.LENGTH_SHORT).show();
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
                    final ArrayList<String> details = new ArrayList<String>();
                    final ArrayList<String> images = new ArrayList<String>();

                    JSONArray steps = response.getJSONArray("steps");

                    for (Integer i = 0; i <= steps.length() - 1; i++) {
                        JSONObject step = steps.getJSONObject(i);
                        String name = step.getString(KEY_STEP_NAME);
                        String detail = step.getString(KEY_STEP_DETAIL);
                        String image = step.getString(KEY_STEP_IMAGE);

                        names.add(name);
                        details.add(detail);
                        images.add(image);
                    }

                    name = names.toArray(new String[names.size()]);
                    detail = details.toArray(new String[details.size()]);
                    image = images.toArray(new String[images.size()]);

                    mAdapter = new ForumPostsCardView(getActivity(), name, image, detail, false);
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
        LearnStepPost post = LearnStepPost.newInstance(image[position], name[position], detail[position]);
        getFragmentManager().beginTransaction()
                .replace(R.id.forumFrame, post)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public static class LearnStepPost extends Fragment {

        NetworkImageView mImage;
        ImageLoader mImageLoader;
        TextView postName, postDetail;

        String name, image, detail;

        public static LearnStepPost newInstance(String image, String name, String detail) {
            LearnStepPost post = new LearnStepPost();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_STEP_IMAGE, image);
            bundle.putString(KEY_STEP_NAME, name);
            bundle.putString(KEY_STEP_DETAIL, detail);
            post.setArguments(bundle);
            return post;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImageLoader = VolleySingleton.getInstance().getImageLoader();
            if (getArguments() != null) {
                image = getArguments().getString(KEY_STEP_IMAGE);
                name = getArguments().getString(KEY_STEP_NAME);
                detail = getArguments().getString(KEY_STEP_DETAIL);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.forum_learn_of_step_post, container, false);
            ((ForumActivity) getActivity()).setActionBarTitle(name);
            mImage = (NetworkImageView) view.findViewById(R.id.post_image);
            postName = (TextView) view.findViewById(R.id.post_name);
            postDetail = (TextView) view.findViewById(R.id.post_detail);

            mImage.setImageUrl(image, mImageLoader);
            postName.setText(name);
            postDetail.setText(detail);
            return view;
        }
    }
}
