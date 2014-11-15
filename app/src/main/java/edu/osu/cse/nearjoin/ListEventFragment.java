package edu.osu.cse.nearjoin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;

/**
 * Created by Fish on 11/11/2014.
 */
public class ListEventFragment extends Fragment {

    private ListView listView;
    public LazyAdapter adapter;

    public int category = 1;

    public ArrayList<EventRecord> studyList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> sportList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> entertainmentList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> othersList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> searchList = new ArrayList<EventRecord>();
    //public ArrayList<EventRecord> curList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adapter = new LazyAdapter(getActivity(), studyList); //getActivity().getApplicationContext()

        EventRecord e1 = new EventRecord();
        e1.setTitle("netwroking study group");
        e1.setCategory(1);
        e1.setHost("Jim");
        e1.setStartDate("2014-11-15 09:00:00");
        e1.setEndDate("2014-11-15 12:00:00");
        e1.setLocation("SEL");
        ArrayList<String> ps = new ArrayList<String>();
        ps.add("Tom"); ps.add("Jery");
        e1.setParticipants(ps);
        studyList.add(e1);

        EventRecord e2 = new EventRecord();
        e2.setTitle("Operating System study group");
        e2.setCategory(1);
        e2.setHost("Lucy");
        e2.setStartDate("2014-11-15 08:00:00");
        e2.setEndDate("2014-11-15 11:00:00");
        e2.setLocation("Oval");
        ps.add("Obama");
        e2.setParticipants(ps);
        studyList.add(e2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View messageLayout = inflater.inflate(R.layout.event_list_fragment, container, false);

        listView = (ListView)messageLayout.findViewById(R.id.event_list_fragment_listView);

        //getActivity().getApplicationContext()
        switch (category){
            case 1:  adapter = new LazyAdapter(getActivity(), studyList);           break;
            case 2:  adapter = new LazyAdapter(getActivity(), sportList);           break;
            case 3:  adapter = new LazyAdapter(getActivity(), entertainmentList);   break;
            case 4:  adapter = new LazyAdapter(getActivity(), othersList);          break;
            case 5:  adapter = new LazyAdapter(getActivity(), searchList);          break;
            default: adapter = new LazyAdapter(getActivity(), studyList);           break;
        }

        listView.setAdapter(adapter);

        // Click event for single list row
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // start a new activity to show the event details

            }
        });
        return messageLayout;
    }

    public void changeAdapterData(int c)
    {
        switch (c){
            case 1: category = 1;          break;
            case 2: category = 2;          break;
            case 3: category = 3;          break;
            case 4: category = 4;          break;
            case 5: category = 5;          break;
            default:category = 1;          break;
        }

    }

}
