package app.program;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import app.fragments.Home;
import app.fragments.NavigationDrawer;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (findViewById(R.id.MainFrame) != null) {
            if (savedInstanceState != null) {
                Home fragmentHome = (Home) getSupportFragmentManager().findFragmentByTag("FRAGMENT_HOME");
            } else {
                Home fragmentHome = new Home();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.MainFrame, fragmentHome, "FRAGMENT_HOME")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }

        //drawer settings
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

    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
