package modev.gosport.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CalendarView;

import modev.gosport.Adapter.ViewPagerAdapter;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;

public class CalendarEventsActivity extends AppCompatActivity {
    String userID;
    CalendarView calendarView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ViewPagerAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        DBHandler db = new DBHandler(this);
        userID = db.getUserID();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        //calendarView = (CalendarView) findViewById(R.id.calendarView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                break;
                            case R.id.action_events:
                                Intent intent = new Intent(CalendarEventsActivity.this, ListEventsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(CalendarEventsActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(), userID, this);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }
}
