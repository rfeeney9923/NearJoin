package edu.osu.cse.nearjoin;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.MyEvent;

public class GcmDeleteParticipantAsyncTask extends AsyncTask<ArrayList<String>, Void, String> {

    private static MyEvent eventService = null;
    private String msg;

    @Override
    protected String doInBackground(ArrayList<String>... params) {
        if (eventService == null) {
            MyEvent.Builder builder = new MyEvent.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            eventService = builder.build();
        }

        msg = "delete participant successfully";
        ArrayList<String> list = params[0];
        String event_title = list.get(0);
        String participant = list.get(1);
        try {
//java.lang.String participant, java.lang.String title
            eventService.deleteParticipant(participant, event_title).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Logger.getLogger("Client delete participant: ").log(Level.INFO, msg);
    }
}

