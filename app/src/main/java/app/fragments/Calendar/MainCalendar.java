package app.fragments.Calendar;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import app.program.CalendarActivity;
import app.program.R;


/**
 * Created by apple on 4/2/2015./
 */
@SuppressWarnings("unused")
public class MainCalendar extends Fragment {

    CalendarView calendar;
    TextView notification;
    private int notificationID = 100;
    private int numMessages = 0;
    Button button;
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

        initializeCalendar();
        TextView notification = (TextView) view.findViewById(R.id.text);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNotification();

            }
        });

        Button button = (Button)view.findViewById(R.id.new_event);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "hello", Toast.LENGTH_LONG).show();
                //addCalendarEvent();
            }
        });

        return view;


    }

    public void initializeCalendar() {


        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                Toast.makeText(getActivity(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
     addCalendarEvent();
            }


        });

    }

    public void addCalendarEvent() {
    Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
    startActivity(intent);}





    protected void displayNotification(){

       Log.i("Start", "Notification");
       NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
       mBuilder.setContentTitle("Notification Alert, Click Me!");
       mBuilder.setContentText("Hi.. Notification Details");
       mBuilder.setTicker("New Message Alert");

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);

        mBuilder.setNumber(++numMessages);                        //to increase no of notifications!
       /*Intent resultIntent = new Intent(getActivity(), SoilParameter1.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(SoilParameter1.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent); */



       mBuilder.setDefaults(Notification.DEFAULT_SOUND);
       NotificationManager mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
       mNotificationManager.notify(notificationID, mBuilder.build());

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






