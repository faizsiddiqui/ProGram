package app.fragments.Base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.program.MainActivity;
import app.program.R;
import app.program.SplashActivity;

/**
 * Not for public use
 * Created by FAIZ on 20-03-2015.
 */
public class Splash extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_splash, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }, SplashActivity.SPLASH_TIME_OUT);
        return view;
    }
}
