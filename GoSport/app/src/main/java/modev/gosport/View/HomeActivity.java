package modev.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import modev.gosport.Adapter.EventInformationAdapter;
import modev.gosport.Class.EventInformation;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;


public class HomeActivity extends AppCompatActivity {
    String userID;
    final DBHandler db = new DBHandler(this);
    private BottomNavigationView bottomNavigationView;
    private ArrayList<EventInformation> listAllEvents;
    private static EventInformationAdapter adapter;
    private Button makeEvent;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private EventInformation createEvent = new EventInformation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        userID = getIntent().getExtras().getString("user");
        listAllEvents = db.getAllEvents();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        makeEvent = (Button) findViewById(R.id.makeEventButton);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                Intent intent = new Intent(HomeActivity.this, CalendarEventsActivity.class);
                                intent.putExtra("user", userID);
                                startActivity(intent);
                                break;
                            case R.id.action_events:
                                break;
                            case R.id.action_profile:
                                intent = new Intent(HomeActivity.this, ProfileActivity.class);
                                intent.putExtra("user", userID);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });

        ListView listView=(ListView)findViewById(R.id.listViewExample);

        adapter= new EventInformationAdapter(listAllEvents,getApplicationContext(), userID);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });

        makeEvent.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, NewEventActivity.class);
                        intent.putExtra("user", userID);
                        startActivity(intent);
                    }
                });
    }
}
