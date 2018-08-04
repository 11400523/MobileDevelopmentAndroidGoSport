package facebook.example.com.gosport.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CalendarView;

import facebook.example.com.gosport.Adapter.ViewPagerAdapter;
import facebook.example.com.gosport.R;

public class CalendarEvents extends AppCompatActivity {
    int userID;
    CalendarView calendarView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ViewPagerAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);


        userID = getIntent().getExtras().getInt("user");
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
                                Intent intent = new Intent(CalendarEvents.this, Home.class);
                                intent.putExtra("user", userID);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(CalendarEvents.this, Profile.class);
                                intent.putExtra("user", userID);
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

















        /*DateTimeFormatter dateFormatter
                = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFormatter = DateTimeFormatter.ofPattern("d-MMMM-uuuu", Locale.ENGLISH);
        }
        String stringDate = "28-July-2018";
        long millisecondsSinceEpoch = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            millisecondsSinceEpoch = LocalDate.parse(stringDate, dateFormatter)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli();
        }
        calendarView.setDate(millisecondsSinceEpoch);
        stringDate = "29-July-2018";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            millisecondsSinceEpoch = LocalDate.parse(stringDate, dateFormatter)
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli();
        }*/

    }
}
