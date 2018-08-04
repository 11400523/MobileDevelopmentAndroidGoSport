package facebook.example.com.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import facebook.example.com.gosport.Class.EventInformation;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class Event extends AppCompatActivity {
    int eventID;
    int userID;
    final DBHandler db = new DBHandler(this);
    private EventInformation event;
    private TextView title, location, date, info;
    private CheckBox going;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);
        userID = getIntent().getExtras().getInt("user");
        eventID = getIntent().getExtras().getInt("event");
        event = db.getEvent(eventID);

        title = (TextView) findViewById(R.id.titleTextView);
        location = (TextView) findViewById(R.id.locationTextView);
        date = (TextView) findViewById(R.id.dateTextView);
        info = (TextView) findViewById(R.id.infoTextView);
        going = (CheckBox) findViewById(R.id.chkIos);
        back = (Button) findViewById(R.id.backButton);


        title.setText(event.getEventName());
        location.setText(event.getLocation());
        date.setText(event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
        info.setText(event.getExtraInfo());
        ArrayList<EventInformation> test = db.getAllEventsOfUserIDandEventID(userID, eventID);
        if (test.size() == 0){
            going.setChecked(false);
            going.setText("Maybe can you join this event?");
        }else{
            going.setChecked(true);
            going.setText("You are going");
        }




        going.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked){
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
                        Intent intent = new Intent(Event.this, Home.class);
                        intent.putExtra("user", userID);
                        startActivity(intent);
                    }
                });
    }

    public void Going(){
        db.addUserForEvent(userID, eventID);
    }

    public void NotGoing(){
        db.delete_byID(userID, eventID);
    }
}
