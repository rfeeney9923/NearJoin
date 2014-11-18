package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;

/**
 * Created by Fish on 11/10/2014.
 */

// register the device if it is not registered, check by shared preference or SQLiteDatabase
//
public class MainActivity extends Activity {

    public static Context MAIN_CONTEXT;
    public static String regId;
    public static String userName;
    public static String password;

    private FragmentManager fragmentManager;
    private MapEventFragment mapEventFragment;
    //private ListEventFragment listEventFragment;
    private EventListFragment eventListFragment;

    private View mapLayout;
    private View listLayout;
    private View postLayout;
    private View settingsLayout;

    // load event-list-fragment or event-map-fragment
    private View eventContentLayout;

    private final int selectedBackgroundColor = Color.argb(255,100,100,100);
    private final int unSelectedTextColor = Color.parseColor("#82858b");

    // category
    private View studyLayout;
    private View sportLayout;
    private View entertainmentLayout;
    private View othersLayout;
    private ImageView studyImage;
    private ImageView sportImage;
    private ImageView entertainmentImage;
    private ImageView othersImage;
    private TextView studyTextView;
    private TextView sportTextView;
    private TextView entertainmentTextView;
    private TextView othersTextView;
    private SearchView searchView;
    private EditText searchEditText;
    private String search_keywords;

    public ArrayList<EventRecord> studyList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> sportList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> entertainmentList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> othersList = new ArrayList<EventRecord>();
    public ArrayList<EventRecord> searchList = new ArrayList<EventRecord>();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String eventStr = bundle.getString(GcmIntentService.EVENT);
                String userStr = bundle.getString(GcmIntentService.USER);
                int resultCode = bundle.getInt(GcmIntentService.RESULT);
                if(eventStr!=null)
                {
                    if (resultCode == RESULT_OK) {
                        EventRecord event = parseStringToEventRecord(eventStr);
                        if(event!=null)
                        {
                            int event_category = event.getCategory();

                            switch (event_category){
                                case 1: studyList.add(event);           break;
                                case 2: sportList.add(event);           break;
                                case 3: entertainmentList.add(event);   break;
                                case 4: othersList.add(event);          break;
                                case 5: searchList.add(event);          break;
                                default:othersList.add(event);          break;
                            }

                            HashMap<String,String> new_cell_data = getCellDataFromEvent(event);
                            eventListFragment.updateData(event_category,new_cell_data);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "EVENT failed",Toast.LENGTH_LONG).show();
                    }
                }
                if(userStr!=null)
                {
                    if (resultCode == RESULT_OK) {
                        Toast.makeText(MainActivity.this, "USER: " + userStr, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "USER failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initViews();
        fragmentManager = getFragmentManager();
        setEventContentFragment(0);
        listLayout.setBackgroundColor(selectedBackgroundColor);
        studyTextView.setTextColor(Color.BLUE);

        MAIN_CONTEXT = this;
        GcmApiWrapper.register(userName,password);

        // create the attendance code
        char c1 =(char)( 96 + (int)(Math.random()*26) );
        char c2 =(char)( 96 + (int)(Math.random()*26) );
        char c3 =(char)( 96 + (int)(Math.random()*26) );
        char c4 =(char)( 96 + (int)(Math.random()*26) );
        String tmp = ""+c1+c2+c3+c4;
        int a =0;
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(GcmIntentService.NOTIFICATION));
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(receiver);
    }
    private void initViews() {
        mapLayout = findViewById(R.id.map_layout);
        listLayout = findViewById(R.id.list_layout);
        postLayout = findViewById(R.id.post_layout);
        settingsLayout = findViewById(R.id.settings_layout);
        eventContentLayout = findViewById(R.id.event_content_fragment);
        studyImage = (ImageView) findViewById(R.id.study_imageView);
        sportImage = (ImageView) findViewById(R.id.sport_imageView);
        entertainmentImage = (ImageView) findViewById(R.id.entertainment_imageView);
        othersImage = (ImageView) findViewById(R.id.others_imageView);
        studyTextView = (TextView) findViewById(R.id.study_textView);
        sportTextView = (TextView) findViewById(R.id.sport_textView);
        entertainmentTextView = (TextView) findViewById(R.id.entertainment_textView);
        othersTextView = (TextView) findViewById(R.id.others_textView);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchEditText = (EditText) findViewById(R.id.search_editText);
        searchEditText.addTextChangedListener(searchEditTextListener);
    }

    private TextWatcher searchEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                search_keywords = s.toString();
            }
            catch (NumberFormatException e){
                search_keywords = "";
            }
        }

        @Override
        public void afterTextChanged(Editable s) {  }
    };

    public void onClick(View v) {

        clearSelection();
        switch (v.getId()) {
            case R.id.map_layout:
                setEventContentFragment(1);
                mapLayout.setBackgroundColor(selectedBackgroundColor);
                break;
            case R.id.list_layout:
                setEventContentFragment(0);
                listLayout.setBackgroundColor(selectedBackgroundColor);
                break;
            case R.id.post_layout:
                // start post event activity
                startActivity(new Intent(this,PostEventActivity.class));
                postLayout.setBackgroundColor(selectedBackgroundColor);
                break;
            case R.id.settings_layout:
                // start settings activity
                startActivity(new Intent(this,SettingsActivity.class));
                settingsLayout.setBackgroundColor(selectedBackgroundColor);
                break;
            case R.id.study_layout:
                // THIS IS WRONG!
                // TRY TO STOP THE SERVER TO SEND OLD EVENTS
                studyList.clear(); eventListFragment.studyList.clear();
                GcmApiWrapper.browseEvents(regId,1,1000.0);
                eventListFragment.updateCategory(1); // show study list in the listView Fragment
                studyTextView.setTextColor(Color.BLUE);
                break;
            case R.id.sport_layout:
                sportList.clear(); eventListFragment.sportList.clear();
                GcmApiWrapper.browseEvents(regId,2,1000.0);
                eventListFragment.updateCategory(2);
                sportTextView.setTextColor(Color.BLUE);
                break;
            case R.id.entertainment_layout:
                entertainmentList.clear();  eventListFragment.entertainmentList.clear();
                GcmApiWrapper.browseEvents(regId,3,1000.0);
                eventListFragment.updateCategory(3);
                entertainmentTextView.setTextColor(Color.BLUE);
                break;
            case R.id.others_layout:
                othersList.clear(); eventListFragment.othersList.clear();
                GcmApiWrapper.browseEvents(regId,4,1000.0);
                eventListFragment.updateCategory(4);
                othersTextView.setTextColor(Color.BLUE);
                break;
            case R.id.searchView:
                // do searching using search_keywords

                break;


            default:
                break;
        }
    }


    private void setEventContentFragment(int index) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // to avoid to display several fragments at the same time
        hideFragments(transaction);
        switch (index) {
            case 0: // list event
                if (eventListFragment == null) {
                    eventListFragment = new EventListFragment();
                    //listEventFragment.list.setAdapter(curAdapter);
                    //listEventFragment.list = (ListView) findViewById(R.id.event_list_fragment_listView);
                    //listEventFragment.list.setAdapter(studyAdapter);
                    transaction.add(R.id.event_content_fragment, eventListFragment,"EVENT_LIST");

                    //listEventFragment.list = (ListView) findViewById(R.id.event_list_fragment_listView);
                    //listEventFragment.list.setAdapter(studyAdapter);
                } else {
                    transaction.show(eventListFragment);
                }
                Logger.getLogger("MainActivity").log(Level.INFO, "load list fragment");
                break;
            case 1: // map event
                if (mapEventFragment == null) {
                    mapEventFragment = new MapEventFragment();
                    transaction.add(R.id.event_content_fragment, mapEventFragment);
                } else {
                    transaction.show(mapEventFragment);
                }
                Logger.getLogger("MainActivity").log(Level.INFO, "load map fragment");
                break;
        }
        transaction.commit();
    }

     private void clearSelection() {
         //studyImage.setImageResource(R.drawable.message_unselected);
         //sportImage.setImageResource(R.drawable.contacts_unselected);
         //entertainmentImage.setImageResource(R.drawable.news_unselected);
         //othersImage.setImageResource(R.drawable.setting_unselected);

         studyTextView.setTextColor(unSelectedTextColor);
         sportTextView.setTextColor(unSelectedTextColor);
         entertainmentTextView.setTextColor(unSelectedTextColor);
         othersTextView.setTextColor(unSelectedTextColor);

         mapLayout.setBackgroundColor(Color.BLACK);
         listLayout.setBackgroundColor(Color.BLACK);
         postLayout.setBackgroundColor(Color.BLACK);
         settingsLayout.setBackgroundColor(Color.BLACK);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (eventListFragment != null) {
            transaction.hide(eventListFragment);
        }
        if (mapEventFragment != null) {
            transaction.hide(mapEventFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // parse a string into EventRecord
    public static EventRecord parseStringToEventRecord(String str)
    {
        EventRecord new_event = new EventRecord();

        String[] components = str.split(";");
        int components_length = components.length;

        // check the format!!!
        if(components_length!=13)
        {
            // wrong format of event
            return null;
        }
        String new_event_title = components[0];
        int new_event_category = Integer.parseInt(components[1]);
        String new_event_location = components[2];
        String new_event_start_date =components[3];
        String new_event_end_date = components[4];
        String new_event_description = components[5];
        int new_event_status = Integer.parseInt(components[6]);
        String new_event_host = components[7];
        String new_event__host_url = components[8];
        String new_event_extraContactInfo = components[9];

        String new_event_participants = components[10];
        String[] participantsArray = new_event_participants.split(",");
        ArrayList<String> new_event_participantsList = new ArrayList<String>(participantsArray.length);
        for(int i=0;i<participantsArray.length;i++)
            new_event_participantsList.add(participantsArray[i]);

        String new_event_valid_participants = components[11];
        String[] validParticipantsArray = new_event_valid_participants.split(",");
        ArrayList<String> new_event_valid_participantsList = new ArrayList<String>(validParticipantsArray.length);
        for(int i=0;i<validParticipantsArray.length;i++)
            new_event_valid_participantsList.add(validParticipantsArray[i]);

        String new_event_attendance_code = components[12];

        new_event.setTitle(new_event_title);
        new_event.setCategory(new_event_category);
        new_event.setLocation(new_event_location);
        new_event.setStartDate(new_event_start_date);
        new_event.setEndDate(new_event_end_date);
        new_event.setDescription(new_event_description);
        new_event.setStatus(new_event_status);
        new_event.setHost(new_event_host);
        new_event.setHostUrl(new_event__host_url);
        new_event.setExtraContactInfo(new_event_extraContactInfo);

        new_event.setParticipants(new_event_participantsList);
        new_event.setValidatedParticipants(new_event_valid_participantsList);

        new_event.setAttendanceCode(new_event_attendance_code);

        return new_event;
    }
    public HashMap<String,String> getCellDataFromEvent(EventRecord event)
    {
        HashMap<String,String> cell = new HashMap<String, String>();
        cell.put("title", event.getTitle());
        cell.put("time", event.getStartDate());
        cell.put("location", event.getLocation());
        cell.put("host", event.getHost());
        cell.put("host_url", event.getHostUrl());
        cell.put("duration", event.getEndDate());
        cell.put("description", event.getDescription());
        cell.put("status", event.getStatus().toString());
        cell.put("extraContactInfo", event.getExtraContactInfo());

        cell.put("attendance_code", event.getAttendanceCode());

        List<String> participants = event.getParticipants();
        StringBuilder builder = new StringBuilder(512);
        for(String participant: participants ){
            builder.append(participant);    builder.append(",");
        }
        cell.put("participants", builder.toString());

        return cell;
    }
}