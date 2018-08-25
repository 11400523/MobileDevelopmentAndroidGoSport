package facebook.example.com.gosport.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import facebook.example.com.gosport.Class.EventInformation;
import facebook.example.com.gosport.R;
import facebook.example.com.gosport.SqlLite.DBHandler;

public class NewEventActivity extends AppCompatActivity {

    private Button btnSave;
    private Button btnDate;
    private EditText txtEventName;
    private EditText txtLocation;
    private EditText txtInfo;
    private int mYear, mMonth, mDay;
    private EventInformation createEvent = new EventInformation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_main);

        btnSave = (Button) findViewById(R.id.registerButton);
        btnDate = (Button) findViewById(R.id.dateButton);
        txtEventName = (EditText) findViewById(R.id.edittextEventName);
        txtLocation = (EditText) findViewById(R.id.edittextLocation);
        txtInfo = (EditText) findViewById(R.id.edittextInfo);

        final DBHandler db = new DBHandler(this);

        btnSave.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        createEvent.setExtraInfo(txtInfo.getText().toString());
                        createEvent.setLocation(txtLocation.getText().toString());
                        createEvent.setEventName(txtEventName.getText().toString());
                        createEvent.setHour(0);
                        createEvent.setMinute(0);
                        db.addEvent(createEvent);
                        Intent intent = new Intent(NewEventActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });

        btnDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(NewEventActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        //returnvalue[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                        createEvent.setYear(year);
                                        createEvent.setMonth(monthOfYear + 1);
                                        createEvent.setDay(dayOfMonth);
                                        btnSave.setEnabled(true);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                }
        );
    }
}
