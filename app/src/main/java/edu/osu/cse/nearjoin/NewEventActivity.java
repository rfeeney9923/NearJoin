package edu.osu.cse.nearjoin;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class NewEventActivity extends Activity implements OnClickListener {

    public EditText title;
    public EditText description;
    public EditText time;
    public EditText location;
    public CheckBox usePhoneNumber;


	private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        title = (EditText) findViewById(R.id.title_field);
        description = (EditText) findViewById(R.id.description_field);
        time = (EditText) findViewById(R.id.time_select);
        location = (EditText) findViewById(R.id.location_field);
        usePhoneNumber = (CheckBox) findViewById(R.id.include_phone);

        View btnSubmit = (Button) findViewById(R.id.submit_button);
        btnSubmit.setOnClickListener(this);
        View btnUseCurrentLocation = (Button) findViewById(R.id.current_location_button);
        btnUseCurrentLocation.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_event, menu);
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

    public void submit(){}
    public void getCurrentLocation(){}

    public void onClick(View v){
        switch (v.getId()){
            case R.id.submit_button:
                submit();
                break;
            case R.id.current_location_button:
                getCurrentLocation();
                break;
        }
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
}
