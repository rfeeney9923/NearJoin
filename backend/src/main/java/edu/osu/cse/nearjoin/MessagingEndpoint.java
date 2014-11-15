/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package edu.osu.cse.nearjoin;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static edu.osu.cse.nearjoin.OfyService.ofy;

/**
 * An endpoint to send messages to devices registered with the backend
 *
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 *
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "messaging", version = "v1", namespace = @ApiNamespace(
        ownerDomain = "eventBackend.nearjoin.cse.ohio_state.edu",
        ownerName = "eventBackend.nearjoin.cse.ohio_state.edu",
        packagePath=""))
public class MessagingEndpoint {
    private static final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /** Api Keys can be obtained from the google cloud console */
    private static final String API_KEY = System.getProperty("gcm.api.key");

    /**
     * Send to the first 10 devices (You can modify this to send to any number of devices or a specific device)
     *
     * @param message The message to send
     */
    public void sendMessage(@Named("message") String message) throws IOException {
        if(message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", message).build();
        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(10).list();
        for(RegistrationRecord record : records) {
            Result result = sender.send(msg, record.getRegId(), 5);
            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getRegId());
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                    record.setRegId(canonicalRegId);
                    ofy().save().entity(record).now();
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                }
                else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }
    }

    // send a event to client in response his query
    @ApiMethod(name = "sendEvent", path = "message/sendEvent")
    public void sendEvent(
            @Named("eventTitle") String eventTitle,
            @Named("regId") String regId) throws IOException {
        if(eventTitle == null || eventTitle.trim().length() == 0) {
            log.warning("Not sending message because the title is empty");
            return;
        }
        //check regId??
        RegistrationRecord record = ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
        if(record==null || !regId.equals(record.getRegId())){
            log.warning("Not sending message because the regId is not found");
            return;
        }

        // crop longer messages
        EventRecord event = ofy().load().type(EventRecord.class).filter("title", eventTitle).first().now();
        String message = event.toString();
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("event", message).build();
        Result result = sender.send(msg, regId, 5);
        if (result.getMessageId() != null) {
            log.info("Message sent to " + record.getRegId());
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
                // if the regId changed, we have to update the datastore
                log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                record.setRegId(canonicalRegId);
                ofy().save().entity(record).now();
            }
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                // if the device is no longer registered with Gcm, remove it from the datastore
                ofy().delete().entity(record).now();
            }
            else {
                log.warning("Error when sending message : " + error);
            }
        }
    }

    /*
    // send message to all clients (participants in the evnet) to tell them that the event has been deleted.
    @ApiMethod(name = "deleteEvent", path = "message/deleteEvent")
    public void deleteEvent(@Named("eventTitle") String eventTitle) throws IOException {
        if(eventTitle == null || eventTitle.trim().length() == 0) {
            log.warning("Not sending message because the title is empty");
            return;
        }
        EventRecord event = ofy().load().type(EventRecord.class).filter("title", eventTitle).first().now();

        ArrayList<String> participants = event.getParticipants();
        for(String participant : participants) {
            // find user in registration by user name


            Result result = sender.send(msg, record.getRegId(), 5);
        }

        // crop longer messages

        String message = event.toString();
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("event", message).build();
        Result result = sender.send(msg, regId, 5);
        if (result.getMessageId() != null) {
            log.info("Message sent to " + record.getRegId());
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
                // if the regId changed, we have to update the datastore
                log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                record.setRegId(canonicalRegId);
                ofy().save().entity(record).now();
            }
        }
        else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                // if the device is no longer registered with Gcm, remove it from the datastore
                ofy().delete().entity(record).now();
            }
            else {
                log.warning("Error when sending message : " + error);
            }
        }
    }
    */

    // send a event to client in response his query
    @ApiMethod(name = "sendEvents", path = "message/sendEvents")
    public void sendEvents(
            @Named("category") int category,
            @Named("distance") double distance,
            @Named("regId") String regId) throws IOException {

        log.info("# event query: category=" + category + "distance=" + distance + "regId = "+ regId);

        RegistrationRecord record = ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
        //RegistrationRecord record = ofy().load().type(RegistrationRecord.class).first().now();
        if(record==null || !regId.equals(record.getRegId())){
            log.warning("Not sending message because the regId is not found");
            return;
        }

        // crop longer messages
        //List<EventRecord> events = ofy().load().type(EventRecord.class).filter("category", category).filter("distance<",distance).list();
        //List<EventRecord> events = ofy().load().type(EventRecord.class).filter("category==", category).list();
        List<EventRecord> events = ofy().load().type(EventRecord.class).list();

        log.info("# event to send: " + events.size());
        for(EventRecord event : events)
        {
            if(event.getCategory()!=category)
                return;

            //double dist = CalDistance();
            //if(dist>distance)
            //    return;

            String message = event.toString();
            if (message.length() > 1000) {
                message = message.substring(0, 1000) + "[...]";
            }
            Sender sender = new Sender(API_KEY);
            Message msg = new Message.Builder().addData("event", message).build();
            Result result = sender.send(msg, regId, 5);
            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getRegId());
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                    record.setRegId(canonicalRegId);
                    ofy().save().entity(record).now();
                }
            }
            else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                }
                else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }

    }

    private double CalDistance (double lat_a, double lng_a, double lat_b, double lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Double(distance * meterConversion).doubleValue();
    }
}
