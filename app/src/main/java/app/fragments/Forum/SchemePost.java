package app.fragments.Forum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import app.library.VolleySingleton;
import app.program.ForumActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 23-04-2015.
 */
public class SchemePost extends Fragment {

    NetworkImageView schemeImage;
    ImageLoader mImageLoader;
    TextView schemeName, schemeCategory, schemeIntroduction, schemeComponent,
            schemeSupport, schemeEligibility, schemeContact;

    String name, image, category, introduction, component, support, eligibility, contact;

    private static final String POST_NAME = "name";
    private static final String POST_IMAGE = "image";
    private static final String POST_CATEGORY = "category";
    private static final String POST_INTRODUCTION = "introduction";
    private static final String POST_COMPONENT = "component";
    private static final String POST_SUPPORT = "support";
    private static final String POST_ELIGIBILITY = "eligibility";
    private static final String POST_CONTACT = "contact";

    public static SchemePost newInstance(String image, String name, String category,
                                         String introduction, String component, String support, String eligibility, String contact) {
        SchemePost post = new SchemePost();
        Bundle bundle = new Bundle();
        bundle.putString(POST_IMAGE, image);
        bundle.putString(POST_NAME, name);
        bundle.putString(POST_CATEGORY, category);
        bundle.putString(POST_INTRODUCTION, introduction);
        bundle.putString(POST_COMPONENT, component);
        bundle.putString(POST_SUPPORT, support);
        bundle.putString(POST_ELIGIBILITY, eligibility);
        bundle.putString(POST_CONTACT, contact);
        post.setArguments(bundle);
        return post;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if (getArguments() != null) {
            image = getArguments().getString(POST_IMAGE);
            name = getArguments().getString(POST_NAME);
            category = getArguments().getString(POST_CATEGORY);
            introduction = getArguments().getString(POST_INTRODUCTION);
            component = getArguments().getString(POST_COMPONENT);
            support = getArguments().getString(POST_SUPPORT);
            eligibility = getArguments().getString(POST_ELIGIBILITY);
            contact = getArguments().getString(POST_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_scheme_post_fragment, container, false);
        ((ForumActivity) getActivity()).setActionBarTitle(name);
        schemeImage = (NetworkImageView) view.findViewById(R.id.scheme_image);
        schemeName = (TextView) view.findViewById(R.id.scheme_name);
        schemeCategory = (TextView) view.findViewById(R.id.scheme_category);
        schemeIntroduction = (TextView) view.findViewById(R.id.scheme_introduction);
        schemeComponent = (TextView) view.findViewById(R.id.scheme_component);
        schemeSupport = (TextView) view.findViewById(R.id.scheme_support_provided);
        schemeEligibility = (TextView) view.findViewById(R.id.scheme_eligibility);
        schemeContact = (TextView) view.findViewById(R.id.scheme_contact_details);

        schemeImage.setImageUrl(image, mImageLoader);
        schemeName.setText(name);
        schemeCategory.setText(category);
        schemeIntroduction.setText(introduction);
        schemeComponent.setText(component);
        schemeSupport.setText(support);
        schemeEligibility.setText(eligibility);
        schemeContact.setText(contact);
        return view;
    }
}
