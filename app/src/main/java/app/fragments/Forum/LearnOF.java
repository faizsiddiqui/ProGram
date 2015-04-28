package app.fragments.Forum;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import app.fragments.Base.Home;
import app.library.VolleySingleton;
import app.program.R;

/**
 * Created by apple on 4/27/2015.
 */
public class LearnOF extends Fragment {

    NetworkImageView learnOFImage;
    ImageLoader mImageLoader;
    TextView learnOFDescription;

    private static String URL = "http://buykerz.com/program/v1/api/learn";
    public static final String KEY_OF_DETAIL = "OF_details";
    public static final String KEY_OF_DETAIL_HINDI = "OF_details_hindi";
    public static final String KEY_VIDEO = "video";

    String image, video, detail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        image = "http://buykerz.com/program/assets/images/learn/learn_OF.jpg";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_learn_of, container, false);
        learnOFImage = (NetworkImageView) view.findViewById(R.id.learn_of_image);
        learnOFDescription = (TextView) view.findViewById(R.id.learn_of_description);

        learnOFImage.setImageUrl(image, mImageLoader);
        setData(true);

        return view;
    }

    private void setData(boolean checkCache) {
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
                Toast.makeText(getActivity(), "Error fetching Learn.", Toast.LENGTH_SHORT).show();
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
                    video = response.getString(KEY_VIDEO);
                    detail = response.getString(KEY_OF_DETAIL);

                    learnOFDescription.setText(detail);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
