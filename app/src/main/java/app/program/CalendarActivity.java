package app.program;

import android.os.Bundle;
import android.view.MenuItem;

import app.fragments.Calendar.Calendar;

/**
 * Not for public use
 * Created by FAIZ on 10-04-2015.
 */
public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(findViewById(R.id.calendarFrame) != null){
            if (savedInstanceState != null) {
                Calendar calendar = (Calendar) getSupportFragmentManager().findFragmentByTag("FRAGMENT_CALENDAR");
            } else {
                Calendar calendar = new Calendar();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.calendarFrame, calendar, "FRAGMENT_CALENDAR")
                        .commit();
            }
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
