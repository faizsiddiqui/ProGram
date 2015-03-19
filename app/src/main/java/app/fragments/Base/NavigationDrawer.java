package app.fragments.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.HashMap;

import app.adapters.NavigationView;
import app.library.DatabaseHandler;
import app.library.Preferences;
import app.library.VolleySingleton;
import app.program.R;
import app.widgets.LinearLayoutManagerRecyclerView;

/**
 * Not for public use
 * Created by FAIZ on 20-02-2015.
 */
public class NavigationDrawer extends Fragment implements NavigationView.OnItemClickListener {

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private boolean isDrawerOpened = false;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private ActionBarDrawerToggle mDrawerToggle;

    DatabaseHandler db;
    String[] navigationRowText;
    Integer[] navigationRowImage;
    ImageLoader mImageLoader;

    RecyclerView navigationRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    NavigationView navigationAdapter;

    public NavigationDrawer() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        mUserLearnedDrawer = Boolean.valueOf(Preferences.readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
        db = new DatabaseHandler(getActivity());

        NetworkImageView user_image = (NetworkImageView) view.findViewById(R.id.navigation_profile_image);
        TextView user_name = (TextView) view.findViewById(R.id.navigation_profile_name_text);
        TextView user_mail = (TextView) view.findViewById(R.id.navigation_profile_email_text);

        if (db.isUserLoggedIn()) {
            final HashMap<String, String> user = db.getUserDetails();
            user_image.setImageUrl(user.get("image"), mImageLoader);
            user_name.setText(user.get("name"));
            user_mail.setText(user.get("email"));
        } else {
            FrameLayout navLogin = (FrameLayout) view.findViewById(R.id.navbar_login);
            navLogin.getLayoutParams().height = 0;
            navLogin.setVisibility(View.INVISIBLE);
        }

        navigationRowText = new String[]{
                "Tutorials", "Update",
                "Settings", "Help",
                "About"};

        navigationRowImage = new Integer[]{
                R.mipmap.drawer_tutorial, R.mipmap.drawer_update,
                R.mipmap.drawer_settings, R.mipmap.drawer_help,
                R.mipmap.drawer_about};

        navigationAdapter = new NavigationView(getActivity(), navigationRowText, navigationRowImage);
        mLayoutManager = new LinearLayoutManager(getActivity());

        navigationRecyclerView = (RecyclerView) view.findViewById(R.id.navigationRecyclerView);
        navigationRecyclerView.setHasFixedSize(false);
        navigationRecyclerView.setLayoutManager(mLayoutManager);
        navigationAdapter.SetOnItemClickListener(this);
        navigationRecyclerView.setAdapter(navigationAdapter);

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
    }

    public void setup(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {
        View mDrawerView = getActivity().findViewById(fragmentID);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    Preferences.saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, true + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) drawerLayout.openDrawer(mDrawerView);
        drawerLayout.setDrawerListener(mDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}