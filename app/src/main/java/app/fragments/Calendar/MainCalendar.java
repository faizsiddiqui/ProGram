   package app.fragments.Calendar;

    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v4.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.DatePicker;
    import android.widget.Toast;

    import app.program.CalendarActivity;
    import app.program.MainActivity;
    import app.program.R;


    /**
    * Created by apple on 4/2/2015.
    */
   @SuppressWarnings("unused")
   public class MainCalendar extends Fragment {
        // This is the date picker used to select the date for our notification
        private DatePicker picker;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.calendar_enter_location_fragment, container, false);
            ((CalendarActivity) getActivity()).setActionBarTitle(R.string.toolbar_text_calendar);
            //get a reference to our date picker
            picker = (DatePicker) view.findViewById(R.id.scheduleTimePicker);
            return view;

        }

            // this is the onclick called from XML to set a new notification

        public void onDateSelectedButtonClick(View v) {
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
            c.set(Calendar.second, 0);

        }


    }


