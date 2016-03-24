package lightning.cyborg.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    String id, name, email, fname, lname, dob, lat, lon, avatar, edu_level;

    public User() {
    }

    public User(JSONObject jsonIn){
        try {
            this.id = jsonIn.getString("id");
            this.email = jsonIn.getString("email");
            this.fname = jsonIn.getString("fname");
            this.lname = jsonIn.getString("lname");
            this.dob = jsonIn.getString("dob");
            this.lat = jsonIn.getString("lat");
            this.lon = jsonIn.getString("lon");
            this.avatar = jsonIn.getString("avatar");
            this.edu_level = jsonIn.getString("edu_level");
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEdu_level() {
        return edu_level;
    }

    public void setEdu_level(String edu_level) {
        this.edu_level = edu_level;
    }
}
