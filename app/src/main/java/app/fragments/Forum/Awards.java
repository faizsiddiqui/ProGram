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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import app.adapters.CardView;
import app.library.VolleySingleton;
import app.program.MainActivity;
import app.program.R;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Not for public use
 * Created by FAIZ on 12-03-2015.
 */
public class Awards extends Fragment implements CardView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardView mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    String[] titles, descriptions, images;

    private static String URL = "http://buykerz.com/program/v1/api/awards";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_PRIZE = "prize";
    private static final String KEY_STATE = "state";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PUBLISHED = "published";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_awards);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setAdapter();
        getFloatingActionButtonView(view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
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
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
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
                    Toast.makeText(getActivity(), "Error fetching Awards.", Toast.LENGTH_SHORT).show();
                }
            });
            VolleySingleton.getInstance().addToRequestQueue(jsonReq);
        }
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if (response.getString(KEY_SUCCESS) != null) {
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if (res) {
                    final ArrayList<String> names = new ArrayList<String>();
                    final ArrayList<String> intro = new ArrayList<String>();
                    final ArrayList<String> icon = new ArrayList<String>();

                    JSONArray awards = response.getJSONArray("awards");

                    for (Integer i = 0; i <= awards.length() - 1; i++) {
                        JSONObject award = awards.getJSONObject(i);
                        String title = award.getString(KEY_NAME);
                        String description = award.getString(KEY_DESCRIPTION);
                        String image = award.getString(KEY_IMAGE);
                        names.add(title);
                        intro.add(description);
                        icon.add(image);
                    }

                    titles = names.toArray(new String[names.size()]);
                    descriptions = intro.toArray(new String[names.size()]);
                    images = icon.toArray(new String[names.size()]);

                    mAdapter = new CardView(titles, descriptions, images);
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
        Post postAward = Post.newInstance(titles[position], descriptions[position], images[position]);
        getFragmentManager().beginTransaction()
                .replace(R.id.MainFrame, postAward)
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
                        .replace(R.id.MainFrame, askQuestion)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
