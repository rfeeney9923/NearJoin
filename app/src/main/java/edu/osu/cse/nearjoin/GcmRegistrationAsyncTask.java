package edu.osu.cse.nearjoin;


import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.registration.Registration;


class GcmRegistrationAsyncTask extends AsyncTask<String, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "251722876598";

    @Override
    protected String doInBackground(String... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            regService = builder.build();
        }

        String record = params[0];
        int semi_column_index = record.indexOf(";");
        String user_name = record.substring(0,semi_column_index);
        String password = record.substring(semi_column_index+1,record.length());

        String msg = "register device successfully ";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(MainActivity.MAIN_CONTEXT);
            }
            String regId = gcm.register(SENDER_ID);
            msg = regId;
            MainActivity.regId = regId;
            Logger.getLogger("REGISTRATION").log(Level.INFO, "MainActivity.regId=" + regId);

// java.lang.String regId, java.lang.String userName, java.lang.String password,
// java.lang.String userURL, java.lang.String phone
            regService.register(regId, user_name, password, "my google+ image URL","6142181721").execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        //Toast.makeText(MainActivity.MAIN_CONTEXT, "Device registered, registration ID=" + msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, "Device registered, registration ID=" + msg);

    }
}

