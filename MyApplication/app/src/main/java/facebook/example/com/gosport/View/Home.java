package facebook.example.com.gosport.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import facebook.example.com.gosport.Adapter.EventInformationAdapter;
import facebook.example.com.gosport.Class.EventInformation;
import facebook.example.com.gosport.Class.RegisterUser;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class Home extends AppCompatActivity {
    int userID;
    final DBHandler db = new DBHandler(this);
    //private ListView lstResult;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<EventInformation> listAllEvents;
    private static EventInformationAdapter adapter;
    //private Button makeEvent;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String txtDate;
    private String txtTime;
    final String[] returnvalue = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        userID = getIntent().getExtras().getInt("user");
        RegisterUser logedInUser = db.getUserRegister(userID);
        listAllEvents = db.getAllEvents();
        //userTextview.setText(logedInUser.getFirstName() + logedInUser.getLastName() + logedInUser.getId());
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        //final Activity ShowResults = this;
        //lstResult = (ListView) findViewById(R.id.lstResult);
        //makeEvent = (Button) findViewById(R.id.makeEventButton);


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

        /*makeEvent.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        AsyncTaskTime time = new AsyncTaskTime();
                        time.execute(new String testtest);


                            //String date = getDate();



                        }

                });*/
    }

    /*public String getDate(){
        final String[] returnvalue = new String[1];
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
                        returnvalue[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        return returnvalue[0];
    }

    private class AsyncTaskTime extends AsyncTask {

        @Override
        protected String doInBackground(Object[] objects) {
            // Get Current Time

            final Calendar k = Calendar.getInstance();
            mHour = k.get(Calendar.HOUR_OF_DAY);
            mMinute = k.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(Home.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            returnvalue[0] = hourOfDay + ":" + minute;
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
            return returnvalue[0];
        }

    }*/

}

