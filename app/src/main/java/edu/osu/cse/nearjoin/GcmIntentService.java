package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Fish on 10/31/2014.
 */
public class GcmIntentService extends IntentService {

    public static final String EVENT = "EVENT";
    public static final String USER = "USER";
    public static final String RESULT = "RESULT";
    public static final String NOTIFICATION = "edu.osu.cse.nearjoin";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                Intent i = new Intent(NOTIFICATION);

                String message = extras.getString("message");
                //if(message!=null)   showToast(message);

                String event = extras.getString("event");
                if(event!=null)
                {
                    i.putExtra(EVENT, event);
                    i.putExtra(RESULT, Activity.RESULT_OK);
                    //showToast(event);
                }

                String user = extras.getString("user");
                if(user!=null)
                {
                    i.putExtra(USER, user);
                    i.putExtra(RESULT, Activity.RESULT_OK);
                    //showToast(user);
                }
                sendBroadcast(i);

                // delivery it to MainActivity
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Intent Service:" + msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}