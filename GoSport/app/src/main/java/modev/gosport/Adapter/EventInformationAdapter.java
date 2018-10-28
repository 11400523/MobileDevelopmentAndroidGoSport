package modev.gosport.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import modev.gosport.Class.EventInformation;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;
import modev.gosport.View.EventActivity;

public class EventInformationAdapter extends ArrayAdapter<EventInformation> implements View.OnClickListener {
    Context mContext;
    private ArrayList<EventInformation> eventInfo;
    String userID;

    public EventInformationAdapter(ArrayList<EventInformation> data, Context context, String userID) {
        super(context, R.layout.item_list_custom, data);
        this.mContext = context;
        this.eventInfo = data;
        this.userID = userID;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        EventInformation dataModel=(EventInformation)object;


        if (mContext != null) {
            Intent intent = new Intent(mContext, EventActivity.class);
            intent.putExtra("event", dataModel.getId());
            intent.putExtra("user", userID);
            mContext.startActivity(intent);
        }
    }

    private class ViewHolder {
        public TextView eventName;
        protected TextView location;
        protected TextView date;
        protected ImageView info;
        protected TextView going;
    }


    private int lastPosition = -1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EventInformation dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_custom, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventnameTextView);
            viewHolder.location = (TextView) convertView.findViewById(R.id.locationTextView);
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.going = (TextView) convertView.findViewById(R.id.goingTextView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }



        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.eventName.setText(dataModel.getEventName());
        viewHolder.location.setText(dataModel.getLocation());
        String tekst = dataModel.getDay() + "/" + dataModel.getMonth() + "/" + dataModel.getYear();
        viewHolder.date.setText(dataModel.getDay() + "/" + dataModel.getMonth() + "/" + dataModel.getYear());
        DBHandler db = new DBHandler(mContext);
        ArrayList<EventInformation> test = db.getAllEventsOfUser(userID);
        if (test.size() == 0){
            viewHolder.going.setText("Maybe can you join this event?");
        }
        for (EventInformation e :test) {
            if (e.getId() == dataModel.getId()){
                viewHolder.going.setText("You are going");
                break;
            } else {
                    viewHolder.going.setText("Maybe can you join this event?");
            }
        }
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
