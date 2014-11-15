package edu.osu.cse.nearjoin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class LoginActivity extends FragmentActivity implements android.view.View.OnClickListener{
    private DatabaseHelper dh;
    private EditText userNameEditableField;
    private EditText passwordEditableField;
    private final static String OPT_NAME="name";
    private final static String TAG="LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.e(this.TAG, "+++ In onCreate() +++");

        FragmentManager.enableDebugLogging(true);
        setContentView(R.layout.activity_login);

        userNameEditableField=(EditText)findViewById(R.id.username_text);
        passwordEditableField=(EditText)findViewById(R.id.password_text);
        android.view.View btnLogin=(Button)findViewById(R.id.login_button);
        btnLogin.setOnClickListener(this);
        android.view.View btnCancel=(Button)findViewById(R.id.cancel_button);
        btnCancel.setOnClickListener(this);
        android.view.View btnNewUser=(Button)findViewById(R.id.new_user_button);
        if (btnNewUser!=null) btnNewUser.setOnClickListener(this);

    }

    private void checkLogin(){
        String username=this.userNameEditableField.getText().toString();
        String password=this.passwordEditableField.getText().toString();
        this.dh=new DatabaseHelper(this);
        List<String> names=this.dh.selectAll(username,password);
        if(names.size() >0){ // Login successful
            // Save username as the name of the player
            SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=settings.edit();
            editor.putString(OPT_NAME, username);
            editor.commit();

            // save user name and password
            MainActivity.userName = username;
            MainActivity.password = password;

            //startActivity(new Intent(this, CategoriesActivity.class));
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            // Try again?
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Login failed")
                    .setNeutralButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .show();
        }
    }

    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.login_button:
                checkLogin();
                break;
            case R.id.cancel_button:
                finish();
                break;
            case R.id.new_user_button:
                startActivity(new Intent(this, AccountActivity.class));
                break;
        }
    }



	private void displayFailure() {
		// Try again
		new AlertDialog.Builder(this)
				.setTitle("Authentication Failed")
				.setMessage("Incorrect Username/Password")
				.setNeutralButton("Re-Enter Username/Password", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.show();
	}

	protected void onStart() {
		super.onStart();
		Log.e(this.TAG, "+++ In onStart() +++");

	}

	protected void onResume() {
		super.onResume();
		Log.e(this.TAG, "+++ In onResume() +++");
	}

	protected void onPause() {
		super.onPause();
		Log.e(this.TAG, "+++ In onPause() +++");
	}

	protected void onStop() {
		super.onStop();
		Log.e(this.TAG, "+++ In onStop() +++");
	}

	protected void onDestroy() {
		super.onDestroy();
		Log.e(this.TAG, "+++ In onDestroy() +++");
		finish();
	}

}
