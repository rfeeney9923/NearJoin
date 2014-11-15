package edu.osu.cse.nearjoin;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class EventListFragment extends ListFragment {
    public ArrayList<HashMap<String,String>> studyList;
    public ArrayList<HashMap<String,String>> sportList ;
    public ArrayList<HashMap<String,String>> entertainmentList;
    public ArrayList<HashMap<String,String>> othersList;
    public ArrayList<HashMap<String,String>> searchList;

    public SimpleAdapter studyAdapter;
    public SimpleAdapter sportAdapter;
    public SimpleAdapter entertainmentAdapter;
    public SimpleAdapter othersAdapter;
    public SimpleAdapter searchAdapter;

    public int category = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studyList = new ArrayList<HashMap<String,String>>();
        sportList = new ArrayList<HashMap<String,String>>();
        entertainmentList = new ArrayList<HashMap<String,String>>();
        othersList = new ArrayList<HashMap<String,String>>();
        searchList = new ArrayList<HashMap<String,String>>();

        // Keys used in Hashmap
        String[] from = { "title","time","location","participant" };

        // Ids of views in listview_layout
        int[] to = { R.id.eventTitle_cell_listView,R.id.eventTime_cell_listView,R.id.eventLocation_cell_listView, R.id.eventParticipant_cell_listView};

        studyAdapter = new SimpleAdapter(getActivity().getBaseContext(), studyList, R.layout.event_listrow, from, to);
        sportAdapter = new SimpleAdapter(getActivity().getBaseContext(), sportList, R.layout.event_listrow, from, to);
        entertainmentAdapter = new SimpleAdapter(getActivity().getBaseContext(), entertainmentList, R.layout.event_listrow, from, to);
        othersAdapter = new SimpleAdapter(getActivity().getBaseContext(), othersList, R.layout.event_listrow, from, to);
        searchAdapter = new SimpleAdapter(getActivity().getBaseContext(), searchList, R.layout.event_listrow, from, to);
        othersAdapter = new SimpleAdapter(getActivity().getBaseContext(), othersList, R.layout.event_listrow, from, to);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        switch (category)
        {
            case 1: setListAdapter(studyAdapter);           break;
            case 2: setListAdapter(sportAdapter);           break;
            case 3: setListAdapter(entertainmentAdapter);   break;
            case 4: setListAdapter(othersAdapter);          break;
            case 5: setListAdapter(searchAdapter);          break;
            default:setListAdapter(othersAdapter);          break;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /*
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }
*/


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
 /*
        EventRecord event = adapter.getItem(position);
        transaction = manager.beginTransaction();

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        detailFragment.setArguments(bundle);
        transaction.replace(R.id.right, detailFragment, "detail");
        transaction.commit();
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
 */
    }

    public void updateCategory(int c)
    {
        switch (c){
            case 1:
                if(studyAdapter!=null){
                    setListAdapter(studyAdapter);
                    studyAdapter.notifyDataSetChanged();
                }
                break;
            case 2:
                if(sportAdapter!=null){
                    setListAdapter(sportAdapter);
                    sportAdapter.notifyDataSetChanged();
                }
                break;
            case 3:
                if(entertainmentAdapter!=null){
                    setListAdapter(entertainmentAdapter);
                    entertainmentAdapter.notifyDataSetChanged();
                }
                break;
            case 4:
                if(othersAdapter!=null){
                    setListAdapter(othersAdapter);
                    othersAdapter.notifyDataSetChanged();
                }
                break;
            case 5:
                if(searchAdapter!=null){
                    setListAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }
                break;
            default:
                if(othersAdapter!=null) {
                    setListAdapter(othersAdapter);
                    othersAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void updateData(int c, HashMap<String,String> data)
    {
        switch (c){
            case 1: studyList.add(data);  studyAdapter.notifyDataSetChanged(); break;
            case 2: sportList.add(data);  sportAdapter.notifyDataSetChanged(); break;
            case 3: entertainmentList.add(data);  entertainmentAdapter.notifyDataSetChanged(); break;
            case 4: othersList.add(data); othersAdapter.notifyDataSetChanged(); break;
            case 5: searchList.add(data); searchAdapter.notifyDataSetChanged();break;
            default:othersList.add(data); othersAdapter.notifyDataSetChanged();break;
        }
    }
}