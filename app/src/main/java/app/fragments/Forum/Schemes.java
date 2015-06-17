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
import app.adapters.ForumDetailPostView;
import app.adapters.ForumPostsCardView;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;
import app.widgets.LinearLayoutManagerRecyclerView;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

/**
 * Not for public use
 * Created by FAIZ on 22-02-2015.
 */
public class Schemes extends Fragment implements ForumPostsCardView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumPostsCardView mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog pDialog;

    String[] image, name, category, introduction, component, support, eligibility, contact, releaseDate;

    private static String URL = "http://buykerz.com/program/v1/api/schemes";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_STATE = "state";
    public static final String KEY_CENTRAL = "central";
    public static final String KEY_COMPONENTS = "components";
    public static final String KEY_INTRODUCTION = "introduction";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_ELIGIBILITY = "eligibility_criteria";
    public static final String KEY_SUPPORT = "support_provided";
    public static final String KEY_CONTACT = "contact_details";
    public static final String KEY_PUBLISHED = "published";
    public static final String KEY_RELEASED = "released";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_schemes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading schemes...");
        pDialog.setCancelable(false);

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
        if (!pDialog.isShowing())
            pDialog.show();
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

                    mAdapter = new ForumPostsCardView(getActivity(), name, image, category, releaseDate);
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

    public static class SchemePost extends Fragment {

        NetworkImageView schemeImage;
        ImageLoader mImageLoader;
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        ForumDetailPostView mAdapter;
        TextView schemeName, schemeCategory;

        String[] titles, descriptions;

        String name, image, category, introduction, component, support, eligibility, contact;

        public static SchemePost newInstance(String image, String name, String category,
                                             String introduction, String component, String support, String eligibility, String contact) {
            SchemePost post = new SchemePost();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_IMAGE, image);
            bundle.putString(KEY_NAME, name);
            bundle.putString(KEY_CATEGORY, category);
            bundle.putString(KEY_INTRODUCTION, introduction);
            bundle.putString(KEY_COMPONENTS, component);
            bundle.putString(KEY_SUPPORT, support);
            bundle.putString(KEY_ELIGIBILITY, eligibility);
            bundle.putString(KEY_CONTACT, contact);
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
                category = getArguments().getString(KEY_CATEGORY);
                introduction = getArguments().getString(KEY_INTRODUCTION);
                component = getArguments().getString(KEY_COMPONENTS);
                support = getArguments().getString(KEY_SUPPORT);
                eligibility = getArguments().getString(KEY_ELIGIBILITY);
                contact = getArguments().getString(KEY_CONTACT);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.forum_post_detail_fragment, container, false);
            ((ForumActivity) getActivity()).setActionBarTitle(name);
            schemeImage = (NetworkImageView) view.findViewById(R.id.post_image);
            schemeName = (TextView) view.findViewById(R.id.post_name);
            schemeCategory = (TextView) view.findViewById(R.id.post_category);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.detail_list);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManagerRecyclerView(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            titles = new String[]{
                    "INTRODUCTION", "COMPONENTS",
                    "SUPPORT PROVIDED", "ELIGIBILITY",
                    "CONTACT DETAILS"
            };

            descriptions = new String[]{
                    introduction, component,
                    support, eligibility, contact
            };

            schemeImage.setImageUrl(image, mImageLoader);
            schemeName.setText(name);
            schemeCategory.setText(category);
            mAdapter = new ForumDetailPostView(titles, descriptions);
            mRecyclerView.setAdapter(mAdapter);

            return view;
        }
    }
}
