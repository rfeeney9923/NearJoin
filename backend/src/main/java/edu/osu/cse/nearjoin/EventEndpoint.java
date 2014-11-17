package edu.osu.cse.nearjoin;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.repackaged.com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static edu.osu.cse.nearjoin.OfyService.ofy;

/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 *
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 *
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "myEvent", version = "v1", namespace = @ApiNamespace(
        ownerDomain = "eventBackend.nearjoin.cse.ohio_state.edu",
        ownerName = "eventBackend.nearjoin.cse.ohio_state.edu",
        packagePath=""))
public class EventEndpoint {

    private static final Logger log = Logger.getLogger(EventEndpoint.class.getName());

    // return all events
    @ApiMethod(name = "listEvents", path = "eventrecord/listEvents")
    public CollectionResponse<EventRecord> listEvents() {
        List<EventRecord> events = ofy().load().type(EventRecord.class).list();
        return CollectionResponse.<EventRecord>builder().setItems(events).build();
    }

    /**
     * Return a collection of events by category
     *
     * @param category The category of events
     * @return a list of Events
     */
    @ApiMethod(name = "listEventsByCategory", path = "eventrecord/listEventsByCategory")
    public CollectionResponse<EventRecord> listEventsByCategory(@Named("category") int category) {
        List<EventRecord> events = ofy().load().type(EventRecord.class).filter("category", category).list();
        return CollectionResponse.<EventRecord>builder().setItems(events).build();
    }

    @ApiMethod(name = "listEventsByHost", path = "eventrecord/listEventsByHost")
    public CollectionResponse<EventRecord> listEventsByHost(@Named("host") String host) {
        List<EventRecord> events = ofy().load().type(EventRecord.class).filter("host", host).list();
        return CollectionResponse.<EventRecord>builder().setItems(events).build();
    }
/*
    // This is Wrong!!! how to compare date, date1.before(date2), date1.after(date2)
    @ApiMethod(name = "listEventsAfterDate")
    public CollectionResponse<Event> listEventsAfterDate(@Named("date") Date date) {
        List<Event> events = ofy().load().type(Event.class).filter("date >", date).list();
        return CollectionResponse.<Event>builder().setItems(events).build();
    }

    listEventsByDistance

    listEventsByCategoryAndDistance
*/

    private EventRecord findEvent(String title) {
        return ofy().load().type(EventRecord.class).filter("title", title).first().now();
    }

    @ApiMethod(name = "addEvent", path = "eventrecord/addEvent")
    public void addEvent(
            @Named("host") String host,
            @Named("host_url") String host_url,
            @Named("title") String title,
            @Named("category") int category,
            @Named("location") String location,
            @Named("start_date") String start_date,
            @Named("end_date") String end_date,
            @Named("description") String description,
            @Named("extraContactInfo") String extraContactInfo) {

        EventRecord event = findEvent(title);
        if( event == null) {  // add event
            log.info("Event " + title + " has not been created, do creation");
            event = new EventRecord();
            event.setTitle(title);
            event.setHost(host);
            event.setHost_url(host_url);
            event.setCategory(category);
            event.setStart_date(start_date);
            event.setEnd_date(end_date);
            event.setLocation(location);
            event.setDescription(description);
            event.setExtraContactInfo(extraContactInfo);
            event.setStatus(1); // BEFORE = 1
            event.addParticipant(host);
            event.setTimeStamp(new DateTime(new Date()).getValue());

            ofy().save().entity(event).now();
            return;
        }

        // update event
        event.setTitle(title);
        event.setHost(host);
        event.setHost_url(host_url);
        event.setCategory(category);
        event.setStart_date(start_date);
        event.setEnd_date(end_date);
        event.setLocation(location);
        event.setDescription(description);
        event.setExtraContactInfo(extraContactInfo);
        event.setStatus(1); // BEFORE = 1

        ofy().save().entity(event).now();
    }

/*
    @ApiMethod(name = "updateDate", path="eventrecord/date")
    public void updateDate(@Named("title") String title,@Named("date") Date date) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        event.setStart_date(date);
        ofy().save().entity(event).now();    // async without the now()
    }


    @ApiMethod(name = "updateLocation",path="eventrecord/location")
    public void updateLocation(@Named("title") String title,@Named("location") String location) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        event.setLocation(location);
        ofy().save().entity(event).now();    // async without the now()
    }

    @ApiMethod(name = "updateDescription", path="eventrecord/description")
    public void updateDescription(@Named("title") String title,@Named("description") String description) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        event.setDescription(description);
        ofy().save().entity(event).now();    // async without the now()
    }

    @ApiMethod(name = "updateExtraContactInfo", path="eventrecord/extraContactInfo")
    public void updateExtraContactInfo(@Named("title") String title,@Named("extraContactInfo") String extraContactInfo) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        event.setExtraContactInfo(extraContactInfo);
        ofy().save().entity(event).now();    // async without the now()
    }
*/
    @ApiMethod(name = "addParticipant", path = "eventrecord/addParticipant")
    public void addParticipant(@Named("title") String title,@Named("participant") String participant) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        event.addParticipant(participant);
        ofy().save().entity(event).now();    // async without the now()

        // inform all participants that a new participant joined the event

    }

    @ApiMethod(name = "listParticipants", path = "eventrecord/listParticipants")
    public CollectionResponse<String> listParticipants(@Named("title") String title) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip listParticipants");
            return null;
        }
        return CollectionResponse.<String>builder().setItems(event.getParticipants()).build();
    }

    @ApiMethod(name = "deleteParticipant", path = "eventrecord/deleteParticipant")
    public void deleteParticipant(@Named("title") String title,@Named("participant") String participant) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        event.deleteParticipant(participant);
        ofy().save().entity(event).now();    // async without the now()

        // inform all participants that a participant dropped the event
        //List<String> participants = event.getParticipants();
        //for(String p : participants )
        //{
        //    RegistrationRecord user = ofy().load().type(RegistrationRecord.class).filter("userName", p).first().now();
        //    sendEvents();
        //}
    }

    /*
    @ApiMethod(name = "updateSatus")
    public void updateSatus(@Named("title") String title) {
        Event event = findEvent(title);
        if(event == null) {
            log.info("Event " + event + " not created, skip updating");
            return;
        }
        ofy().delete().entity(event).now();
    }
    */

    @ApiMethod(name = "deleteEvent",  path = "eventrecord/deleteEvent")
    public void deleteEvent(@Named("title") String title) {
        EventRecord event = findEvent(title);
        if(event == null) {
            log.info("Event " + title + " not registered, skipping unregister");
            return;
        }
        ofy().delete().entity(event).now();

        // inform all participants that the event is deleted.
    }
}
