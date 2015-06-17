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

import com.afollestad.materialdialogs.MaterialDialog;
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
 * Created by FAIZ on 18-03-2015.
 */
public class SignUp extends Fragment {

    AppCompatEditText signUpName, signUpMobile, signUpPassword, signUpEmail, signUpAge;
    AppCompatButton signUpState, signUpCategory, signUpGender, signUp;
    TextView signUpLogin;

    private ProgressDialog pDialog;

    String name, mobile, password, email, state, category, gender, age;

    DatabaseHandler db;

    private static String URL = "http://buykerz.com/program/v1/api/register";
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
        View view = inflater.inflate(R.layout.base_sign_up_fragment, container, false);
        signUpName = (AppCompatEditText) view.findViewById(R.id.sign_up_name);
        signUpMobile = (AppCompatEditText) view.findViewById(R.id.sign_up_mobile);
        signUpPassword = (AppCompatEditText) view.findViewById(R.id.sign_up_password);
        signUpEmail = (AppCompatEditText) view.findViewById(R.id.sign_up_email);
        signUpAge = (AppCompatEditText) view.findViewById(R.id.sign_up_age);
        signUpState = (AppCompatButton) view.findViewById(R.id.sign_up_state);
        signUpCategory = (AppCompatButton) view.findViewById(R.id.sign_up_category);
        signUpGender = (AppCompatButton) view.findViewById(R.id.sign_up_gender);

        signUp = (AppCompatButton) view.findViewById(R.id.sign_up_button);
        signUpLogin = (TextView) view.findViewById(R.id.sign_up_login);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.dialog_please_wait));
        pDialog.setCancelable(false);

        signUpState.setOnClickListener(new SetState());
        signUpCategory.setOnClickListener(new SetCategory());
        signUpGender.setOnClickListener(new SetGender());
        signUpLogin.setOnClickListener(new SignUpToLogin());

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = signUpName.getText().toString();
                mobile = signUpMobile.getText().toString();
                password = signUpPassword.getText().toString();
                email = signUpEmail.getText().toString();
                state = signUpState.getText().toString();
                category = signUpCategory.getText().toString();
                gender = signUpGender.getText().toString();
                age = signUpAge.getText().toString();
                if (name.isEmpty() || mobile.isEmpty() || password.isEmpty() || email.isEmpty() ||
                        age.isEmpty() || state.isEmpty() || category.isEmpty()
                        || gender.isEmpty()) {
                    Toast.makeText(getActivity(), "Some fields are missing.", Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                }
            }
        });
        return view;
    }

    private void signUp() {
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
                Toast.makeText(getActivity(), "Error Signing up.", Toast.LENGTH_SHORT).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAME, name);
                params.put(KEY_MOBILE, mobile);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_STATE, state);
                params.put(KEY_CATEGORY, category);
                params.put(KEY_GENDER, gender);
                params.put(KEY_AGE, age);
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

    public class SetState implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new MaterialDialog.Builder(getActivity())
                    .title("States")
                    .items(R.array.location_list)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            signUpState.setText(text);
                        }
                    })
                    .positiveText("Select")
                    .show();
        }
    }

    private class SetCategory implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.sign_up_category)
                    .items(R.array.sign_up_category)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            signUpCategory.setText(text);
                        }
                    })
                    .positiveText("Select")
                    .show();
        }
    }

    private class SetGender implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.sign_up_gender)
                    .items(R.array.sign_up_gender)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            signUpGender.setText(text);
                        }
                    })
                    .positiveText("Select")
                    .show();
        }
    }

    private class SignUpToLogin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Login login = new Login();
            getFragmentManager().beginTransaction()
                    .replace(R.id.FullScreenFrame, login)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
