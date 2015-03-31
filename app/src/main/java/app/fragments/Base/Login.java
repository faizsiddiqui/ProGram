package app.fragments.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.TintEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import app.library.DatabaseHandler;
import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 16-03-2015.
 */
public class Login extends Fragment {

    TintEditText loginId, loginPassword;
    Button loginButton;
    String id, password;
    DatabaseHandler db;

    private static String URL = "http://buykerz.com/program/v1/api/login";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_API = "api_key";
    private static final String KEY_JOINED = "joined";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(R.string.toolbar_login);
        loginId = (TintEditText) view.findViewById(R.id.login_id);
        loginPassword = (TintEditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = loginId.getText().toString();
                password = loginPassword.getText().toString();
                if(id.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), "Id or Password field empty.", Toast.LENGTH_SHORT).show(); 
                } else {
                    login();
                    getActivity().getFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }

    private void login(){
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonFeed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Logging in.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJsonFeed(JSONObject response){
        try {
            String KEY_SUCCESS = "success";
            String KEY_MSG = "message";
            if(response.getString(KEY_SUCCESS) != null){
                Boolean res = response.getBoolean(KEY_SUCCESS);
                if(res){
                    db = new DatabaseHandler(getActivity());
                    db.logoutUser();
                    JSONObject json_user = response.getJSONObject("user");
                    /*db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_MOBILE),
                            json_user.getString(KEY_EMAIL), json_user.getString(KEY_LOCATION),
                            response.getString(KEY_ID), json_user.getString(KEY_API),
                            json_user.getString(KEY_JOINED)); */
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
