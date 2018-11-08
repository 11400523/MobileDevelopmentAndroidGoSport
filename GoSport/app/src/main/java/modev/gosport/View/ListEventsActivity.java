package modev.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import modev.gosport.R;

public class ListEventsActivity extends FragmentActivity {
    //private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        Button makeEvent = (Button) findViewById(R.id.makeEventButton);
        makeEvent.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(ListEventsActivity.this, NewEventActivity.class);
                        startActivity(intent);
                    }
                });

        //DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail);
        //if (detailFragment == null) {
            /*bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                Intent intent = new Intent(ListEventsActivity.this, CalendarEventsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_events:
                                break;
                            case R.id.action_profile:
                                intent = new Intent(ListEventsActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });*/
        //}
    }
}
