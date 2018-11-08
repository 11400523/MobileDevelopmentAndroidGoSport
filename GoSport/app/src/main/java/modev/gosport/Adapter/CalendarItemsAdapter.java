package modev.gosport.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import modev.gosport.R;
import modev.gosport.View.DetailActivity;
import modev.librarycalendar.domain.Event;

public class CalendarItemsAdapter extends RecyclerView.Adapter<CalendarItemsAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private List<Event> mDataSet;

    public CalendarItemsAdapter(List<Event> dataSet) {
        mDataSet = dataSet;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context mContext = v.getContext();
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("event", mDataSet.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);


                }
            });
            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.calendar_row_items, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getTextView().setText(mDataSet.get(i).getData().toString());
        viewHolder.getTextView().setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
