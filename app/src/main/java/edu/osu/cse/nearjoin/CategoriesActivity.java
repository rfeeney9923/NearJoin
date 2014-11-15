package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class CategoriesActivity extends Activity implements OnClickListener{

    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(this.TAG, "+++ In onCreate() +++");
	    setContentView(R.layout.activity_categories);

        View btnSports = findViewById(R.id.sportsButton);
        btnSports.setOnClickListener(this);
        View btnEducation = findViewById(R.id.educationButton);
        btnEducation.setOnClickListener(this);
        View btnEntertainment = findViewById(R.id.entertainmentButton);
        btnEntertainment.setOnClickListener(this);
        View btnOther = findViewById(R.id.otherButton);
        btnOther.setOnClickListener(this);

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


    public void getListing(String filter){
        startActivity(new Intent(this, EventDescriptionActivity.class));
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.sportsButton:
                getListing("sports");
                break;
            case R.id.educationButton:
                getListing("education");
                break;
            case R.id.entertainmentButton:
                getListing("entertainment");
                break;
            case R.id.otherButton:
                getListing("other");
                break;
        }
    }
}
