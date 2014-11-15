package edu.osu.cse.nearjoin;

/**
 * Created by Fish on 10/31/2014.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.myUser.MyUser;

/**
 * Created by Fish on 10/13/2014.
 */
class GcmUserAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyUser userService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "231364826414";

    @Override
    protected String doInBackground(Context... params) {
        if (userService == null) {
         /*
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end of optional local run code
            */
            MyUser.Builder builder = new MyUser.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

            userService = builder.build();
        }

        context = params[0];

        String msg = "name";
        try {
            userService.add("user one").execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("USER").log(Level.INFO, msg);
    }
}
