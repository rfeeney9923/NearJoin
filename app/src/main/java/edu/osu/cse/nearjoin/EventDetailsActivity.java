package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;

/**
 * Created by Fish on 11/14/2014.
 */
public class EventDetailsActivity extends Activity {

    private Button cancelButton;
    private Button postButton;
    private Button attendanceButton;
    private EditText titleEditText;
    private EditText timeEditText;
    private EditText durationEditText;
    private EditText locationEditText;
    private EditText categoryEditText;
    private EditText phoneEditText;
    private EditText descriptionEditText;
    private EditText participantsEditText;

    private boolean isHost;
    private boolean isJoined;
    public String authentication;

    EventRecord event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        event = new EventRecord();
        getIntentExtra();
        if (event.getHost().equals(MainActivity.userName) )
            isHost = true;
        else
            isHost = false;

        cancelButton = (Button) findViewById(R.id.cancel_browse_event_button);
        postButton = (Button) findViewById(R.id.post_browse_event_button);
        attendanceButton = (Button) findViewById(R.id.check_attendance_browse_event_button);

        titleEditText = (EditText) findViewById(R.id.title_browse_event_editText);
        titleEditText.setText(event.getTitle());
        durationEditText = (EditText) findViewById(R.id.duration_browse_event_editText);
        durationEditText.setText(event.getEndDate());
        timeEditText = (EditText) findViewById(R.id.time_browse_event_editText);
        timeEditText.setText(event.getStartDate());
        locationEditText = (EditText) findViewById(R.id.location_browse_event_editText);
        locationEditText.setText(event.getLocation());
        categoryEditText = (EditText) findViewById(R.id.category_browse_event_editText);
        categoryEditText.setText(event.getCategory().toString());
        phoneEditText = (EditText) findViewById(R.id.phone_browse_event_editText);
        phoneEditText.setText(event.getExtraContactInfo());
        descriptionEditText = (EditText)findViewById(R.id.description_browse_event_editText);
        descriptionEditText.setText(event.getDescription());
        participantsEditText = (EditText)findViewById(R.id.participants_browse_event_editText);

        List<String> participants = event.getParticipants();
        StringBuilder builder = new StringBuilder(512);
        for(String participant: participants ){
            builder.append(participant);    builder.append(",");
        }
        participantsEditText.setText(builder.toString());
        participantsEditText.setEnabled(false);

        if(!isHost)
        {
            titleEditText.setEnabled(false);
            durationEditText.setEnabled(false);
            timeEditText.setEnabled(false);
            locationEditText.setEnabled(false);
            categoryEditText.setEnabled(false);
            phoneEditText.setEnabled(false);
            descriptionEditText.setEnabled(false);
            attendanceButton.setEnabled(false);

            if(participants.contains(MainActivity.userName))
            {
                isJoined = true;
                postButton.setText("Check In");
                cancelButton.setText("Drop");
            }
            else
            {
                isJoined = false;
                postButton.setText("Join");
                cancelButton.setText("Cancel");
            }
        }
        else{
            postButton.setText("Update");
            cancelButton.setText("Delete");
        }
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.cancel_browse_event_button:
                if(isHost) {
                    new AlertDialog.Builder(this)
                            .setTitle("Warning")
                            .setMessage("Do you really want to delete the event?")
                            .setNeutralButton("Delete It", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    GcmApiWrapper.deleteEvent(event.getTitle());
                                    Toast.makeText(getApplicationContext(), "Event " + event.getTitle() + "is deleted.", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
                else{
                    if(isJoined)
                        dropEvent();
                }
                break;
            case R.id.post_browse_event_button:
                if(isHost)
                    updateEvent();
                else
                    if(isJoined)
                        checkIn();
                    else
                        joinEvent();
                break;
            case R.id.check_attendance_browse_event_button:
                checkAttendance();
            default:
                break;
        }
    }

    private void checkAttendance(){
        String tmp = event.getAttendanceCode();
        new AlertDialog.Builder(this)
                .setTitle("Attendance Code")
                .setMessage(tmp)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Attendance checked", Toast.LENGTH_LONG).show();
                     }
                })
                .show();
    }

    private void checkIn(){
        final Context c = this;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Check In Code");
        alert.setMessage("Get the Attendance Code from Host");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Toast.makeText(getApplicationContext(), "dialog text:"+value, Toast.LENGTH_LONG).show();

                //check authentication variable against database
                String attendance_code =event.getAttendanceCode();

                //if validated, add user to list of validated participants
                //And notify the user of success
                if(value.equals(attendance_code) ){
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(c);
                    alert2 = new AlertDialog.Builder(c);
                    alert2.setTitle("Check In");
                    alert2.setMessage("Authentication Successful");

                    // add validated participant to the event
                    GcmApiWrapper.addValidatedParticipant(event.getTitle(),MainActivity.userName);

                    // Set up the buttons
                    alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dialog.dismiss();
                        }
                    });
                    alert2.show();
                }
                //else display an error message to the user
                else {
                    AlertDialog.Builder alert3 = new AlertDialog.Builder(c);
                    alert3 = new AlertDialog.Builder(c);
                    alert3.setTitle("Check In");
                    alert3.setMessage("Invalid Check In Code");
                    // Set up the buttons
                    alert3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert3.show();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
    private void dropEvent()
    {
        GcmApiWrapper.deleteParticipant(event.getTitle(), MainActivity.userName);
    }
    private void joinEvent()
    {
        GcmApiWrapper.addParticipant(event.getTitle(), MainActivity.userName);
    }

    private void updateEvent()
    {
        String title = titleEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String duration = durationEditText.getText().toString();
        String location = locationEditText.getText().toString();
        int category = Integer.parseInt(categoryEditText.getText().toString());
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

        EventRecord e = new EventRecord();
        e.setHost(MainActivity.userName);
        e.setHostUrl("");
        e.setTitle(title);
        e.setStartDate(time);
        e.setEndDate(duration);
        e.setLocation(location);
        e.setCategory(category);
        e.setExtraContactInfo(phone);
        e.setDescription(description);
        GcmApiWrapper.addEvent(e);
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        if (intent == null) {
            return ;
        }

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return ;
        }

        event.setTitle(extras.getString("title"));
        event.setStartDate(extras.getString("time"));
        event.setLocation(extras.getString("location"));
        event.setHost(extras.getString("host"));
        event.setHostUrl(extras.getString("host_url"));
        event.setEndDate(extras.getString("duration"));
        event.setDescription(extras.getString("description"));
        event.setStatus(Integer.parseInt(extras.getString("status")));
        event.setExtraContactInfo(extras.getString("extraContactInfo"));

        event.setAttendanceCode(extras.getString("attendance_code"));

        String[] participantsArray = extras.getString("participants").split(",");
        ArrayList<String> participantsList = new ArrayList<String>();
        for(int i=0;i<participantsArray.length;i++)
            participantsList.add(participantsArray[i]);
        event.setParticipants(participantsList);
        event.setCategory(extras.getInt("category"));
    }
}