package edu.osu.cse.nearjoin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;
import edu.ohio_state.cse.nearjoin.eventbackend.myUser.model.UserRecord;

public final class GcmApiWrapper {

    public static void register(String user_name, String password)
    {
        new GcmRegistrationAsyncTask().execute(user_name+";"+password);
    }
    // add or update an event
    public static void addEvent(EventRecord event)
    {
        new GcmAddEventAsyncTask().execute(event);
    }

    public static void deleteEvent(String event_title)
    {
        new GcmDeleteEventAsyncTask().execute(event_title);
    }
    public static void addParticipant(String event_title, String user)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add(event_title);
        list.add(user);
        new GcmAddParticipantAsyncTask().execute(list);
    }
    public static void deleteParticipant(String event_title, String user)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add(event_title);
        list.add(user);
        new GcmDeleteParticipantAsyncTask().execute(list);
    }
    public static void browseEventDetails(String event_title)
    {

    }
    public static void browseEvents(String regId, int category, double distance) // time filter
    {
        Map<String,String> query = new HashMap<String, String>(3);
        query.put("regId", regId);
        query.put("category", Integer.toString(category));
        query.put("distance", Double.toString(distance));
        new GcmBrowseEventsAsyncTask().execute(query);
    }

    public void addUser(UserRecord user)
    {

    }

    public static void updateUser(UserRecord user)
    {

    }

    public static void deleteUser(UserRecord user)
    {

    }
}

