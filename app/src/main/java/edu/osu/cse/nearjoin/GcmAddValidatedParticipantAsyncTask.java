package edu.osu.cse.nearjoin;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.MyEvent;

/**
 * Created by Fish on 11/17/2014.
 */

public class GcmAddValidatedParticipantAsyncTask extends AsyncTask<ArrayList<String>, Void, String> {

    private static MyEvent eventService = null;
    private String msg;

    @Override
    protected String doInBackground(ArrayList<String>... params) {
        if (eventService == null) {
            MyEvent.Builder builder = new MyEvent.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            eventService = builder.build();
        }

        msg = "add validated participant successfully";
        ArrayList<String> list = params[0];
        String event_title = list.get(0);
        String validated_participant = list.get(1);
        try {
// java.lang.String title, java.lang.String validatedParticipant
            eventService.addValidatedParticipant(event_title,validated_participant ).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Logger.getLogger("Client add participant: ").log(Level.INFO, msg);
    }
}
