package app.fragments.Base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import app.program.R;
import app.program.TutorialActivity;

/**
 * Not for public use
 * Created by FAIZ on 16-03-2015.
 */
public class Login extends Fragment {

    AppCompatEditText loginId, loginPassword;
    TextView loginSignUp;
    AppCompatButton loginButton;
    private ProgressDialog pDialog;

    String id, password;

    DatabaseHandler db;

    private static String URL = "http://buykerz.com/program/v1/api/login";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STATE = "state";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_API = "api_key";
    private static final String KEY_JOINED = "joined";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_login_fragment, container, false);
        loginId = (AppCompatEditText) view.findViewById(R.id.login_id);
        loginPassword = (AppCompatEditText) view.findViewById(R.id.login_password);

        loginButton = (AppCompatButton) view.findViewById(R.id.login_button);
        loginSignUp = (TextView) view.findViewById(R.id.login_sign_up);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.dialog_please_wait));
        pDialog.setCancelable(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = loginId.getText().toString();
                password = loginPassword.getText().toString();
                if (id.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Id or Password field empty.", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

        loginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp signUp = new SignUp();
                getFragmentManager().beginTransaction()
                        .replace(R.id.FullScreenFrame, signUp)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void login() {
        if (!pDialog.isShowing())
            pDialog.show();

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonFeed(response);
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Logging in.", Toast.LENGTH_SHORT).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_MOBILE, id);
                params.put(KEY_PASSWORD, password);
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
                    db = new DatabaseHandler(getActivity());
                    db.logoutUser();
                    JSONObject json_user = response.getJSONObject("user");
                    db.addUser(response.getString(KEY_ID), json_user.getString(KEY_NAME), json_user.getString(KEY_MOBILE),
                            json_user.getString(KEY_EMAIL), json_user.getString(KEY_STATE), json_user.getString(KEY_CATEGORY),
                            json_user.getString(KEY_GENDER), json_user.getString(KEY_AGE), json_user.getString(KEY_API), json_user.getString(KEY_JOINED));
                    Toast.makeText(getActivity(), response.getString(KEY_MSG), Toast.LENGTH_SHORT).show();
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Intent tutorial = new Intent(getActivity(), TutorialActivity.class);
                    tutorial.putExtra("caller", "SplashActivity");
                    startActivity(tutorial);
                    getActivity().finish();
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