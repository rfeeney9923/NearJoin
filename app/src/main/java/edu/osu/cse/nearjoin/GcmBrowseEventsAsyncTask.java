package edu.osu.cse.nearjoin;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.messaging.Messaging;

/**
 * Created by Fish on 11/13/2014.
 */
public class GcmBrowseEventsAsyncTask extends AsyncTask<Map<String, String>, Void, String> {
    private static Messaging messagingService = null;
    private GoogleCloudMessaging gcm;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "231364826414";

    @Override
    protected String doInBackground(Map<String, String>... params) {
        if (messagingService == null) {
            Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            messagingService = builder.build();
        }

        String msg = "browse events successfully";
        Map<String, String> query = params[0];
        String regId = query.get("regId");
        int category = Integer.parseInt(query.get("category"));
        double distance = Double.parseDouble(query.get("distance"));
        Logger.getLogger("BrowserEventAsyncTask").log(Level.INFO, regId );
        Logger.getLogger("BrowserEventAsyncTask").log(Level.INFO, ""+category );
        Logger.getLogger("BrowserEventAsyncTask").log(Level.INFO, ""+distance );
        try {
//java.lang.Integer category, java.lang.Double distance, java.lang.String regId
            messagingService.sendEvents(category,distance, regId).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("BrowserEventAsyncTask").log(Level.INFO, msg);
    }
}
