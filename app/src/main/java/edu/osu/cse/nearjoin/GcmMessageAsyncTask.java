package edu.osu.cse.nearjoin;

/**
 * Created by Fish on 10/31/2014.
 */

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.messaging.Messaging;

/**
 * Created by Fish on 10/13/2014.
 */
class GcmMessageAsyncTask extends AsyncTask<String, Void, String> {
    private static Messaging messagingService = null;
    private GoogleCloudMessaging gcm;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "231364826414";

    @Override
    protected String doInBackground(String... params) {
        if (messagingService == null) {
            Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            messagingService = builder.build();
        }

        String msg = "";
        String info = params[0];
        int semi_column_index = info.indexOf(";");
        String event_title = info.substring(0,semi_column_index);
        String regId = info.substring(semi_column_index+1,info.length());
        Logger.getLogger("EVENTTITLE").log(Level.INFO, event_title);
        Logger.getLogger("REGID").log(Level.INFO, regId);
        try {
            messagingService.sendEvent(event_title, regId).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}
