package modev.gosport.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import modev.gosport.Adapter.EventInformationAdapter;
import modev.gosport.Class.EventInformation;
import modev.gosport.R;
import modev.gosport.SqlLite.DBHandler;

public class ListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_events_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final DBHandler db = new DBHandler(getActivity().getBaseContext());
        String userID = db.getUserID();
        ArrayList<EventInformation> listAllEvents;
        listAllEvents = db.getAllEvents();

        ListView listView = (ListView) getView().findViewById(R.id.listViewExample);
        Button makeEvent = (Button) getView().findViewById(R.id.makeEventButton);

        FragmentManager m = getFragmentManager();

        EventInformationAdapter adapter;
        adapter= new EventInformationAdapter(listAllEvents, getActivity().getApplicationContext(), userID, m);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
}
