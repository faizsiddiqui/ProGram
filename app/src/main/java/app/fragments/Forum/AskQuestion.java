package app.fragments.Forum;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.library.CustomJsonObjectRequest;
import app.library.DatabaseHandler;
import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 06-03-2015.
 */
public class AskQuestion extends Fragment {
    private AppCompatEditText questionTitle, questionDescription;
    private Button post;
    private String Title, Description;
    private ProgressDialog pDialog;

    DatabaseHandler db;

    private static String URL = "http://buykerz.com/program/v1/api/question";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.forum_ask_question_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_ask_question);
        db = new DatabaseHandler(getActivity());
        questionTitle = (AppCompatEditText) layout.findViewById(R.id.questionTitle);
        questionDescription = (AppCompatEditText) layout.findViewById(R.id.questionDescription);
        post = (Button) layout.findViewById(R.id.questionPost);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = questionTitle.getText().toString();
                Description = questionDescription.getText().toString();
                if (!db.isUserLoggedIn() || Title.isEmpty() || Description.isEmpty()) {
                    Toast.makeText(getActivity(), "Login/Title/Description Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    postQuestion(); //post question to server
                }
            }
        });
        return layout;
    }

    private void postQuestion() {
        if (!pDialog.isShowing())
            pDialog.show();
        final HashMap<String, String> user = db.getUserDetails();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    parseJsonFeed(response);
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error posting question.", Toast.LENGTH_SHORT).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", Title);
                params.put("description", Description);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", user.get("api_key"));
                return params;
            }
        };
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if (response.getString(KEY_SUCCESS) != null) {
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if (res) {
                    Toast.makeText(getActivity(), response.getString(KEY_MSG), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.getString(KEY_MSG), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), response.getString(KEY_MSG), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
