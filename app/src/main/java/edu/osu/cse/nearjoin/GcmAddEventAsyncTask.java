package edu.osu.cse.nearjoin;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.MyEvent;
import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;

/**
 * Created by Fish on 10/31/2014.
 */
public class GcmAddEventAsyncTask extends AsyncTask<EventRecord, Void, String> {
    private static MyEvent eventService = null;
    private String msg;

    @Override
    protected String doInBackground(EventRecord... params) {
        if (eventService == null) {
            MyEvent.Builder builder = new MyEvent.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            eventService = builder.build();
        }

        msg = "add/update event successfully";
        EventRecord e = params[0];
        try {
// java.lang.Integer category,
// java.lang.String description,
// com.google.api.client.util.DateTime endDate,
// java.lang.String extraContactInfo,
// java.lang.String host,
// java.lang.String hostUrl,
// java.lang.String location,
// com.google.api.client.util.DateTime startDate,
// java.lang.String title)
            eventService.addEvent(e.getCategory(),e.getDescription(),e.getEndDate(),
                    e.getExtraContactInfo(),e.getHost(),e.getHostUrl(),e.getLocation(),
                    e.getStartDate(),e.getTitle()).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Logger.getLogger("Client add event: ").log(Level.INFO, msg);
    }
}
/*
http://tutorials.jenkov.com/java-date-time/java-util-timezone.html
Converting Between Time Zones

You can covert the date and time in a Calendar between different time zones. Here is an example of how to do this:
TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
TimeZone timeZone2 = TimeZone.getTimeZone("Europe/Copenhagen");

Calendar calendar = new GregorianCalendar();

long timeCPH = calendar.getTimeInMillis();
System.out.println("timeCPH  = " + timeCPH);
System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));

calendar.setTimeZone(timeZone1);

long timeLA = calendar.getTimeInMillis();
System.out.println("timeLA   = " + timeLA);
System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));


http://www.tutorialspoint.com/java/java_date_time.htm
Use DateFormat. For example,

    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date date = isoFormat.parse("2010-05-23T09:01:02");

 Date dNow = new Date( );
      SimpleDateFormat ft =
      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

      System.out.println("Current Date: " + ft.format(dNow));

 */