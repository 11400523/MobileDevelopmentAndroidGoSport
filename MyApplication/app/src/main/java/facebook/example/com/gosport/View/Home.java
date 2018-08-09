package facebook.example.com.gosport.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import facebook.example.com.gosport.Adapter.EventInformationAdapter;
import facebook.example.com.gosport.Class.EventInformation;
import facebook.example.com.gosport.Class.RegisterUser;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class Home extends AppCompatActivity {
    int userID;
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

        userID = getIntent().getExtras().getInt("user");
        RegisterUser logedInUser = db.getUserRegister(userID);
        listAllEvents = db.getAllEvents();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        makeEvent = (Button) findViewById(R.id.makeEventButton);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_calendar:
                                Intent intent = new Intent(Home.this, CalendarEvents.class);
                                intent.putExtra("user", userID);
                                startActivity(intent);
                                break;
                            case R.id.action_events:
                                break;
                            case R.id.action_profile:
                                intent = new Intent(Home.this, Profile.class);
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
                Intent intent = new Intent(Home.this, Event.class);
                startActivity(intent);
            }
        });

        makeEvent.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        CreateNewEvent();
                        }
                });
    }

    public void CreateNewEvent(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Home.this);
        alertBuilder.setTitle("Event name");

        final EditText input = new EditText(Home.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("EventName");
        input.setMaxLines(1);
        alertBuilder.setView(input);

        alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            //                    @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String[] eventName = new String[1];
                eventName[0] = input.getText().toString();
                createEvent.setEventName(eventName[0]);
                getLocation();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    public  void getLocation(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Home.this);
        alertBuilder.setTitle("Location");

        final EditText input = new EditText(Home.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Location");
        input.setMaxLines(1);
        alertBuilder.setView(input);

        alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            //                    @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String[] location = new String[1];
                location[0] = input.getText().toString();
                createEvent.setLocation(location[0]);
                getExtraInfo();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    public void getExtraInfo(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Home.this);
        alertBuilder.setTitle("Extra information");

        final EditText input = new EditText(Home.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Extra info");
        //input.setMaxLines(1);
        alertBuilder.setView(input);

        alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            //                    @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String[] info = new String[1];
                info[0] = input.getText().toString();
                createEvent.setExtraInfo(info[0]);
                getDate();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }


    public void getDate(){
        //final String[] returnvalue = new String[1];
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Home.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //returnvalue[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        createEvent.setYear(year);
                        createEvent.setMonth(monthOfYear + 1);
                        createEvent.setDay(dayOfMonth);
                        getTime();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void getTime(){
        final Calendar k = Calendar.getInstance();
        mHour = k.get(Calendar.HOUR_OF_DAY);
        mMinute = k.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(Home.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        createEvent.setHour(hourOfDay);
                        createEvent.setMinute(minute);
                        makeEvent();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void makeEvent(){
        EventInformation e = createEvent;
        db.addEvent(createEvent);
        recreate();
    }
}

