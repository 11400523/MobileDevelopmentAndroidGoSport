package facebook.example.com.gosport.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;
import modev.librarycalendar.CompactCalendarView;
import modev.librarycalendar.domain.Event;

@SuppressLint("ValidFragment")
public class CompactCalendarTab extends Fragment {

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private TextView toolbar;
    int userID;
    Context mContext;

    @SuppressLint("ValidFragment")
    public CompactCalendarTab (int id, Context context){
        this.userID = id;
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainTabView = inflater.inflate(R.layout.main_tab,container,false);

        final List<String> mutableBookings = new ArrayList<>();

        final ListView bookingsListView = mainTabView.findViewById(R.id.bookings_listview);

        final ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mutableBookings){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };

        bookingsListView.setAdapter(adapter);
        compactCalendarView = mainTabView.findViewById(R.id.compactcalendar_view);

        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.displayOtherMonthDays(false);
        compactCalendarView.invalidate();
        showEvents();

        //logEventsByMonth(compactCalendarView);

        // below line will display Sunday as the first day of the week
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);

        // disable scrolling calendar
        // compactCalendarView.shouldScrollMonth(false);

        // show days from other months as greyed out days
        compactCalendarView.displayOtherMonthDays(true);

        // show Sunday as first day of month
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);

        //set initial title
        toolbar = mainTabView.findViewById(R.id.monthyearTextView);
        toolbar.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                toolbar.setText(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                if (bookingsFromMap != null) {
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        // uncomment below to show indicators above small indicator events
        // compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);

        // uncomment below to open onCreate
        //openCalendarOnCreate(v);

        return mainTabView;
    }

    public void showEvents(){
        Locale locale = Locale.ENGLISH;
        dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy", locale);
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Paris");
        dateFormatForDisplaying.setTimeZone(timeZone);
        dateFormatForMonth.setTimeZone(timeZone);
        compactCalendarView.setLocale(timeZone, locale);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        loadEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        // Set to current day on resume to set calendar to latest day
        // toolbar.setTitle(dateFormatForMonth.format(new Date()));
    }

    private void loadEvents() {
        for (int i = 2018; i<2020; i++){
            for (int j = 0; j<12; j++){
                addEvents(j, i);
            }
        }

    }

    private void addEvents(int month, int year) {
        DBHandler db = new DBHandler(mContext);
        ArrayList<EventInformation> allEvents = db.getAllEventsOfUserOfMonthYear(userID, month+1, year);
        if (allEvents.size() != 0){
            currentCalender.setTime(new Date());
            currentCalender.set(Calendar.DAY_OF_MONTH, 1);
            currentCalender.set(Calendar.MONTH, month);
            currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
            currentCalender.set(Calendar.YEAR, year);
            Date firstDayOfMonth = currentCalender.getTime();
            ArrayList<EventInformation> eventsForCalendar = new ArrayList<EventInformation>();
            for (int i = 0; i < currentCalender.getActualMaximum(Calendar.DAY_OF_MONTH)-1; i++){
                for (EventInformation selectedEvent: allEvents) {
                    if(selectedEvent.getDay() == i){
                        currentCalender.setTime(firstDayOfMonth);
                        currentCalender.add(Calendar.DATE, i-1);
                        currentCalender.set(Calendar.HOUR_OF_DAY, selectedEvent.getHour());
                        currentCalender.set(Calendar.MINUTE, selectedEvent.getMinute());
                        selectedEvent.setTimeInMillis(currentCalender.getTimeInMillis());
                        boolean alreadyInList = true;
                        eventsForCalendar.add(selectedEvent);
                    }
                }
                if (eventsForCalendar.size() != 0){
                    List<Event> events = getEvents(eventsForCalendar);
                    eventsForCalendar.clear();
                    compactCalendarView.addEvents(events);
                    if(eventsForCalendar.size() >= allEvents.size()){
                        break;
                    }
                }
            }
        }
    }

    private List<Event> getEvents(ArrayList<EventInformation> events) {
        if( events.size() == 1){
                return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), events.get(0).getTimeInMillis(), events.get(0).getEventName() + " " + dateFormatForDisplaying.format(new Date(events.get(0).getTimeInMillis()))));
        } else if (events.size() == 2){
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), events.get(0).getTimeInMillis(), events.get(0).getEventName() + " " + dateFormatForDisplaying.format(new Date(events.get(0).getTimeInMillis()))),
                    new Event(Color.argb(255, 100, 68, 65), events.get(1).getTimeInMillis(), events.get(1).getEventName() + " " + dateFormatForDisplaying.format(new Date(events.get(1).getTimeInMillis()))));
        }else {
            List<Event> list = new ArrayList<Event>();
            int count = 0;
            for (EventInformation e: events) {
                if (list.size() == 0){
                    list.add(new Event(Color.argb(255, 169, 68, 65), e.getTimeInMillis(), e.getEventName() + " " + dateFormatForDisplaying.format(new Date(e.getTimeInMillis()))));
                } else if (list.size() == 1){
                    list.add(new Event(Color.argb(255, 100, 68, 65), e.getTimeInMillis(), e.getEventName() + " " + dateFormatForDisplaying.format(new Date(e.getTimeInMillis()))));
                } else{
                    list.add(new Event(Color.argb(255, 70, 68, 65), e.getTimeInMillis(), e.getEventName() + " " + dateFormatForDisplaying.format(new Date(e.getTimeInMillis()))));
                }
                count++;
            }
            return list;
        }
    }
}