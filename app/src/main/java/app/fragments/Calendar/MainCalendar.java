package app.fragments.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import app.program.CalendarActivity;
import app.program.R;


/**
 * Created by apple on 4/2/2015./
 */
@SuppressWarnings("unused")
public class MainCalendar extends Fragment {

    CalendarView calendar;

    // This is the date picker used to select the date for our notification
    //private DatePicker picker;

    //public String[] color;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_fragment_main_calendar, container, false);
        calendar = (CalendarView) view.findViewById(R.id.maincalendar);
        ((CalendarActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_calendar);


        //get a reference to our date picker
        //picker = (DatePicker) view.findViewById(R.id.scheduleTimePicker);
           /* NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(getActivity());
           // mBuilder.setSmallIcon(R.drawable.image);
            mBuilder.setContentTitle("Notification Alert, Click Me!");
            mBuilder.setContentText("Hi.. Notification Details");

            Intent resultIntent= new Intent(getActivity(), MainCalendar.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
            stackBuilder.addParentStack(MainCalendar.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent= stackBuilder.getPendingIntent(
                    0, PendingIntent.FLAG_UPDATE_CURRENT );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            */

        initializeCalendar();
        return view;


    }

    public void initializeCalendar() {


        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                Toast.makeText(getActivity(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

            }

        });
    }

    // this is the onclick called from XML to set a new notification
        /*public void onDateSelectedButtonClick(View v) {
            //get the date from datepicker
            int day = picker.getDayOfMonth();
            int month = picker.getMonth();
            int year = picker.getYear();
            //create a new calendar set to the date chosen
            //we set the time to midnight (i.e the first minute of the day)
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            c.set(Calendar.Hour_of_day, 0);
            c.set(Calendar.minute, 0);
            c.set(Calendar.second, 0); }

        }
   */


}






