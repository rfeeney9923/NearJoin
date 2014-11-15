package edu.osu.cse.nearjoin;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class RegistrationRecord {

    @Id
    Long id;

    @Index
    private String regId;

    private String userName;

    private String userURL;

    private String password;

    private String phone;

    public RegistrationRecord() {}

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setUserName(String name){this.userName = name;}
    public String getUserName(){return userName;}

    public void setUserURL(String url){this.userURL = url;}
    public String getUserURL(){return this.userURL;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

    public void setPhone(String phone) {this.phone = phone;}
    public String getPhone(){return this.phone;}
}