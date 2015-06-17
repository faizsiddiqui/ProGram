package app.program;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import app.fragments.Base.Home;
import app.fragments.Base.NavigationDrawer;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (findViewById(R.id.MainFrame) != null) {
            if (savedInstanceState != null) {
                Home home = (Home) getSupportFragmentManager().findFragmentByTag("FRAGMENT_HOME");
            } else {
                Home home = new Home();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.MainFrame, home, "FRAGMENT_HOME")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }

        NavigationDrawer drawerFragment = (NavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //mDrawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        drawerFragment.setup(R.id.navigation_drawer_fragment, mDrawerLayout, toolbar);
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
