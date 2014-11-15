package edu.osu.cse.nearjoin;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class AccountActivity extends FragmentActivity implements OnClickListener{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        //Create the fragment
        AccountFragment accountFragment = new AccountFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.accountdetails, accountFragment);
        fragmentTransaction.commit();

        // Initialize the Exit button
        View buttonExit= (Button)findViewById(R.id.exit_button);
        buttonExit.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_button:
                finish();
                break;
        }
    }
}