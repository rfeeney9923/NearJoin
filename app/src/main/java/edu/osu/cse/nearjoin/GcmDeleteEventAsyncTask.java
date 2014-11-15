package edu.osu.cse.nearjoin;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.MyEvent;

/**
 * Created by Fish on 11/11/2014.
 */
public class GcmDeleteEventAsyncTask extends AsyncTask<String, Void, String> {
    private static MyEvent eventService = null;
    private String msg;

    @Override
    protected String doInBackground(String... params) {
        if (eventService == null) {
            MyEvent.Builder builder = new MyEvent.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            eventService = builder.build();
        }

        String event_title = params[0];
        msg = "delete event" + event_title +" successfully";

        try {
            eventService.deleteEvent(event_title).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: "+ event_title + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Logger.getLogger("Client delete event: ").log(Level.INFO, msg);
    }
}
