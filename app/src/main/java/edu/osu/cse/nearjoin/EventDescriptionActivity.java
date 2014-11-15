package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextClock;
import android.widget.TextView;


public class EventDescriptionActivity extends Activity implements OnClickListener{

	private final String TAG = this.getClass().getSimpleName();

    public TextView eventTitle;
    public TextView eventCategory;
    public TextView hostName;
    public RatingBar hostRating;
    public ImageButton profilePicture;
    public EditText description;
    public TextClock time;
    public EditText location;
    public CheckBox notification;
    public ListView attendees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        eventTitle = (TextView) findViewById(R.id.event_title);
        eventCategory = (TextView) findViewById(R.id.event_category);
        hostName = (TextView) findViewById(R.id.host_name);
        hostRating = (RatingBar) findViewById(R.id.host_rating);
        profilePicture = (ImageButton) findViewById(R.id.profile_picture);
        description = (EditText) findViewById(R.id.description);
        time = (TextClock) findViewById(R.id.time);
        location = (EditText) findViewById(R.id.location);
        notification = (CheckBox) findViewById(R.id.notification);
        attendees = (ListView) findViewById(R.id.attendees);
        profilePicture.setOnClickListener(this);
        View btnAttend = findViewById(R.id.attend_button);
        btnAttend.setOnClickListener(this);

    }

	protected void onStart(){
		super.onStart();
		Log.e(this.TAG, "+++ In onStart() +++");

	}

	protected void onResume(){
		super.onResume();
		Log.e(this.TAG, "+++ In onResume() +++");
	}

	protected void onPause(){
		super.onPause();
		Log.e(this.TAG, "+++ In onPause() +++");
	}

	protected void onStop(){
		super.onStop();
		Log.e(this.TAG, "+++ In onStop() +++");
	}

	protected void onDestroy(){
		super.onDestroy();
		Log.e(this.TAG, "+++ In onDestroy() +++");
	}


    public void attend(){}
    public void viewProfile(){}

    public void onClick(View v){
        switch (v.getId()){
            case R.id.attend_button:
                attend();
                break;
            case R.id.profile_picture:
                viewProfile();
                break;
        }
    }
}
