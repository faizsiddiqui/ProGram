package app.fragments.Forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import app.library.VolleySingleton;
import app.program.MainActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 15-03-2015.
 */
public class Post extends Fragment {

    private static final String POST_TITLE = "title";
    private static final String POST_DESCRIPTION = "description";
    private static final String POST_IMAGE = "image";
    String title,description,image;

    TextView titleView, descriptionView;
    NetworkImageView imageView;
    ImageLoader mImageLoader;

    public static Post newInstance(String title, String description, String image){
        Post post = new Post();
        Bundle bundle = new Bundle();
        bundle.putString(POST_TITLE, title);
        bundle.putString(POST_DESCRIPTION, description);
        bundle.putString(POST_IMAGE, image);
        post.setArguments(bundle);
        return post;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if(getArguments() != null){
            title = getArguments().getString(POST_TITLE);
            description = getArguments().getString(POST_DESCRIPTION);
            image = getArguments().getString(POST_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_post_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(title);

        titleView = (TextView) view.findViewById(R.id.postTitle);
        descriptionView = (TextView) view.findViewById(R.id.postDescription);
        imageView = (NetworkImageView) view.findViewById(R.id.postImage);

        imageView.setImageUrl(image, mImageLoader);
        titleView.setText(title);
        descriptionView.setText(description);

        return view;
    }
}