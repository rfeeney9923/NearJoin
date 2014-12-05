package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;

/**
 * Created by Fish on 11/12/2014.
 */
public class PostEventActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private View cancelButton;
    private View postButton;
    private EditText titleEditText;
    private EditText locationEditText;
    private EditText phoneEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;
    private Integer categorySelection;
    private TimePicker eventTime;
    private Spinner durationSpinner;
    private String durationSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        cancelButton = (Button) findViewById(R.id.cancel_post_event_button);
        postButton = (Button) findViewById(R.id.post_post_event_button);
        titleEditText = (EditText) findViewById(R.id.title_post_event_editText);
        locationEditText = (EditText) findViewById(R.id.location_browse_event_editText);
        phoneEditText = (EditText) findViewById(R.id.phone_browse_event_editText);
        descriptionEditText = (EditText)findViewById(R.id.description_post_event_editText);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        durationSpinner = (Spinner) findViewById(R.id.duration_spinner);
        eventTime = (TimePicker) findViewById(R.id.browse_event_timePicker);

        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this,R.array.Categories,android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        ArrayAdapter durationAdapter = ArrayAdapter.createFromResource(this,R.array.Duration,android.R.layout.simple_spinner_item);
        durationSpinner.setAdapter(durationAdapter);
        durationSpinner.setOnItemSelectedListener(this);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.cancel_post_event_button:

                break;
            case R.id.post_post_event_button:
                postEvent();
                break;
            default:
                break;
        }
    }

    private void postEvent()
    {
        String title = titleEditText.getText().toString();
        String time = eventTime.getCurrentHour().toString() + ":" + eventTime.getCurrentMinute().toString();


        String duration = durationSelection;
        String location = locationEditText.getText().toString();
        //int category = Integer.parseInt(categoryEditText.getText().toString());
        int category = categorySelection;
        String phone = phoneEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        // try to use Date picker and Time picker to get start time and duration
        //Calendar calendar = new GregorianCalendar();
       // TimeZone timeZone = calendar.getTimeZone();//TimeZone.getTimeZone("UTC")
/*
        Date startDate=new Date();
        startDate.setYear(2014 - 1900);
        startDate.setMonth(11 - 1);
        startDate.setDate(31);
        startDate.setHours(18 + 0);
        startDate.setMinutes(34);
        //Date endDate=new Date(startDate.getTime() + 20 * 3600000);
        DateTime start_date=new DateTime(startDate, timeZone);

        Date endDate = new Date();
        endDate.setYear (2014 - 1900);
        endDate.setMonth(11-1);
        endDate.setDate(31);
        endDate.setHours(21 + 0);
        endDate.setMinutes(20);
        DateTime end_date = new DateTime(endDate,timeZone);
*/

        EventRecord event = new EventRecord();
        event.setHost(MainActivity.userName);
        event.setHostUrl("");
        event.setTitle(title);
        event.setStartDate(time);
        event.setEndDate(duration);
        event.setLocation(location);
        event.setCategory(category);
        event.setExtraContactInfo(phone);
        event.setDescription(description);

        GcmApiWrapper.addEvent(event);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.duration_spinner:
               //set duration variable
                durationSelection = Integer.toString(i);
                break;
            case R.id.category_spinner:
                categorySelection = i;
               //set category variable
              break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}









