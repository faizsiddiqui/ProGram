package app.fragments.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 18-03-2015.
 */
public class SignUp extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_login_fragment, container, false);
        return view;
    }
}
