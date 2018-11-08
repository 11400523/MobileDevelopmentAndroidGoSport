package modev.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import modev.gosport.Class.EventInformation;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;

public class DetailActivity extends FragmentActivity {
    final DBHandler db = new DBHandler(this);
    int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Intent intent = getIntent();
        eventID = getIntent().getExtras().getInt("event");
        EventInformation event = db.getEvent(eventID);

        TextView title = (TextView) findViewById(R.id.titleTextView);
        TextView location = (TextView) findViewById(R.id.locationTextView);
        TextView date = (TextView) findViewById(R.id.dateTextView);
        TextView info = (TextView) findViewById(R.id.infoTextView);
        final CheckBox going = (CheckBox) findViewById(R.id.chkIos);
        Button back = (Button) findViewById(R.id.backButton);

        title.setText(event.getEventName());
        location.setText(event.getLocation());
        date.setText(event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
        info.setText(event.getExtraInfo());
        ArrayList<EventInformation> test = db.getAllEventsOfUserIDandEventID(db.getUserID(), eventID);
        if (test.size() == 0) {
            going.setChecked(false);
            going.setText("Maybe can you join this event?");
        } else {
            going.setChecked(true);
            going.setText("You are going");
        }

        going.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Going();
                    going.setChecked(true);
                    going.setText("You are going");

                } else {
                    NotGoing();
                    going.setChecked(false);
                    going.setText("Maybe can you join this event?");
                }
            }
        });

        back.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(DetailActivity.this, ListEventsActivity.class);
                        startActivity(intent);
                    }
                });
    }


    public void Going() {
        db.addUserForEvent(db.getUserID(), eventID);
    }

    public void NotGoing() {
        db.delete_byID(db.getUserID(), eventID);
    }

}
