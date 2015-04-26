package app.program;

import android.os.Bundle;
import android.view.MenuItem;

import app.fragments.SCalendar.Location;

/**
 * Not for public use
 * Created by FAIZ on 10-04-2015.
 */
public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (findViewById(R.id.calendarFrame) != null) {
            if (savedInstanceState != null) {
                Location location = (Location) getSupportFragmentManager().findFragmentByTag("FRAGMENT_LOCATION");
            } else {
                Location location = new Location();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.calendarFrame, location, "FRAGMENT_LOCATION")
                        .commit();
            }

            /*if (savedInstanceState != null) {
                SelectCrop crop = (SelectCrop) getSupportFragmentManager().findFragmentByTag("FRAGMENT_SELECT_CROP");
            } else {
                SelectCrop crop = new SelectCrop();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.calendarFrame, crop, "FRAGMENT_SELECT_CROP")
                        .commit();
            } */

        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_calendar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    return true;
                } else finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
