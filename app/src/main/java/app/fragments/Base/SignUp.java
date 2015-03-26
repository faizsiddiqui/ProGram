package app.fragments.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.widget.TintEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
 * Created by FAIZ on 18-03-2015.
 */
public class SignUp extends Fragment {

    TintEditText signUpName, signUpMobile, signUpPassword, signUpEmail, signUpLocation;
    TextView signUpLogin;
    Button signUp;
    String name, mobile, password, email, location;

    DatabaseHandler db;

    private static String URL = "http://buykerz.com/program/v1/api/register";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_API = "api_key";
    private static final String KEY_ID = "id";
    private static final String KEY_JOINED = "joined";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_sign_up_fragment, container, false);
        signUpName = (TintEditText) view.findViewById(R.id.sign_up_name);
        signUpMobile = (TintEditText) view.findViewById(R.id.sign_up_mobile);
        signUpPassword = (TintEditText) view.findViewById(R.id.sign_up_password);
        signUpEmail = (TintEditText) view.findViewById(R.id.sign_up_email);
        signUpLocation = (TintEditText) view.findViewById(R.id.sign_up_location);
        
        signUp = (Button) view.findViewById(R.id.sign_up_button);
        signUpLogin = (TextView) view.findViewById(R.id.sign_up_login);
        
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), MainActivity.class);
                startActivity(home);
                getActivity().finish();
               /*name = signUpName.getText().toString();
                mobile = signUpMobile.getText().toString();
                password = signUpPassword.getText().toString();
                email = signUpEmail.getText().toString();
                location = signUpLocation.getText().toString();
                if(name.isEmpty() || mobile.isEmpty() || password.isEmpty() || email.isEmpty() || location.isEmpty()){
                    Toast.makeText(getActivity(), "Some field is missing.", Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                    getActivity().getFragmentManager().popBackStack();
                } */
            }
        });

        signUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                getFragmentManager().beginTransaction()
                        .replace(R.id.FullScreenFrame, login)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
        
        return view;
    }

    private void signUp() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonFeed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Signing in.", Toast.LENGTH_SHORT).show();
            }
        });    
    }

    private void parseJsonFeed(JSONObject response) {
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
