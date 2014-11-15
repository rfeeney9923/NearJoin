package edu.osu.cse.nearjoin;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class EventRecord {

    @Id
    Long id;

    @Index
    private String title;
    // @Load
    // User host;
    private String host;
    private String host_url;
    public final static int SPORT =1;
    public final static int STUDY =2;
    public final static int ENTERTAINMENT =3;
    public final static int OTHEREVENT =4;
    private int category;
    // private String subCategory;
    private String location;
    private String start_date;
    private String end_date;
    private String description;

    public final static int BEFORE =1;
    public final static int ONGOING =2;
    public final static int OVER =3;
    private int status; // one of BEFORE, ONGOING, OVER

    private String extraContactInfo;

    private ArrayList<String> participants = new ArrayList<String>();

    public EventRecord() {}

    public EventRecord(String title, String location, String start_date, String end_date,String description,
                       int category, String host, String host_url,String extraContactInfo){
        this.title = title;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.category = category; // Warning: make sure category is 1, 2, 3, or 4
        this.host = host;
        this.host_url = host_url;
        this.extraContactInfo = extraContactInfo;

        this.status = BEFORE; // the status is BEFORE when it is created.
        participants = new ArrayList<String>();
    }


    public void setCategory(int category)    {
        this.category = category;
    }
    public int getCategory(){
        return this.category;
    }

    public void setStart_date(String date){
        this.start_date = date;
    }
    public String getStart_date(){
        return this.start_date;
    }
    public void setEnd_date(String date){this.end_date = date;}
    public String getEnd_date(){return this.end_date;}
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setExtraContactInfo(String extraContactInfo){
        this.extraContactInfo = extraContactInfo;
    }
    public String getExtraContactInfo(){
        return this.extraContactInfo;
    }
    public void setHost(String host){
        this.host = host;
    }
    public String getHost(){
        return this.host;
    }
    public void setHost_url(String host_url){this.host_url = host_url;}
    public String getHost_url(){return host_url;}
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
    public void addParticipant(String participant){
        if(!participants.contains(participant))
            this.participants.add(participant);
    }
    public void deleteParticipant(String participant){
        if(participants.contains(participant))
            this.participants.remove(participant);
    }
    public List<String> getParticipants(){
        return this.participants;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    // be careful the format of Event!!!
    public String toString(){
        StringBuilder builder = new StringBuilder(1000);
        builder.append(title);                          builder.append(";");
        builder.append(category);                       builder.append(";");
        builder.append(location);                       builder.append(";");
        builder.append(start_date);                     builder.append(";");
        builder.append(end_date);                       builder.append(";");
        builder.append(description);                    builder.append(";");
        builder.append(status);                         builder.append(";");
        builder.append(host);                           builder.append(";");
        builder.append(host_url);                       builder.append(";");
        builder.append(extraContactInfo);               builder.append(";");
        for(String participant: participants ){
            builder.append(participant);                builder.append(",");
        }
        //builder.append(";"); // no need for the last field
        return builder.toString();
    }

    // parse a string into EventRecord
    public EventRecord parseString(String str)
    {
        EventRecord new_event = new EventRecord();

        String[] components = str.split(";");
        int components_length = components.length;

        // check the format!!!
        if(components_length!=11)
        {
            // wrong format of event
            return null;
        }
        String new_event_title = components[0];
        int new_event_category = Integer.parseInt(components[1]);
        String new_event_location = components[2];
        String new_event_start_date =components[3];
        String new_event_end_date = components[4];
        String new_event_description = components[5];
        int new_event_status = Integer.parseInt(components[6]);
        String new_event_host = components[7];
        String new_event__host_url = components[8];
        String new_event_extraContactInfo = components[9];
        String new_event_participants = components[10];
        String[] participantsArray = new_event_participants.split(",");
        ArrayList<String> new_event_participantsList = new ArrayList<String>(participantsArray.length);
        for(int i=0;i<participantsArray.length;i++)
            new_event_participantsList.add(participantsArray[i]);

        new_event.setTitle(new_event_title);
        new_event.setCategory(new_event_category);
        new_event.setLocation(new_event_location);
        new_event.setStart_date(new_event_start_date);
        new_event.setEnd_date(new_event_end_date);
        new_event.setDescription(new_event_description);
        new_event.setStatus(new_event_status);
        new_event.setHost(new_event_host);
        new_event.setHost_url(new_event__host_url);
        new_event.setExtraContactInfo(new_event_extraContactInfo);
        new_event.participants = new_event_participantsList;

        return new_event;
    }
}