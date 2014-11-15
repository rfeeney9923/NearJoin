package edu.osu.cse.nearjoin;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class UserRecord {

    @Id
    Long id;

    @Index
    private String name;
    // you can add more fields...



    public UserRecord() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}