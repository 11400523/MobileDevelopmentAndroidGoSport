package modev.gosport.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import modev.gosport.Class.EventInformation;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_event_fragment, container, false);

        Bundle bundle = getArguments();
        TextView title = (TextView) view.findViewById(R.id.titleTextView);
        TextView location = (TextView) view.findViewById(R.id.locationTextView);
        TextView date = (TextView) view.findViewById(R.id.dateTextView);
        TextView info = (TextView) view.findViewById(R.id.infoTextView);
        final CheckBox going = (CheckBox) view.findViewById(R.id.chkIos);
        final int eventID;

        if(bundle != null){
            eventID = getArguments().getInt("event");
            final DBHandler db = new DBHandler(getActivity().getBaseContext());
            EventInformation event = db.getEvent(eventID);

            title.setText(event.getEventName());
            location.setText(event.getLocation());
            date.setText(event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
            info.setText(event.getExtraInfo());
            ArrayList<EventInformation> test = db.getAllEventsOfUserIDandEventID(db.getUserID(), eventID);
            if (test.size() == 0){
                going.setChecked(false);
                going.setText("Maybe can you join this event?");
            }else{
                going.setChecked(true);
                going.setText("You are going");
            }

            going.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        db.addUserForEvent(db.getUserID(), eventID);
                        going.setChecked(true);
                        going.setText("You are going");

                    } else {
                        db.delete_byID(db.getUserID(), eventID);
                        going.setChecked(false);
                        going.setText("Maybe can you join this event?");
                    }
                }
            });
        }
        return view;
    }
}
