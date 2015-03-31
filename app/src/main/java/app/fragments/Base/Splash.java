package app.fragments.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import app.library.VolleySingleton;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 20-03-2015.
 */
public class Splash extends Fragment {

    NetworkImageView mBackgroundImage;
    Button mContinue;
    ImageLoader mImageLoader;

    String imageURL = ""; //http://i.imgur.com/4RxBcn5.jpg

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_splash, container, false);
        mBackgroundImage = (NetworkImageView) view.findViewById(R.id.splash_background_image);
        mBackgroundImage.setImageUrl(imageURL, mImageLoader);
        mContinue = (Button) view.findViewById(R.id.splash_continue);
        mContinue.setOnClickListener(new View.OnClickListener() {
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
}
