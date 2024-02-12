package com.haram3rbat.tanaqol.Model;

public class Fetch3 {
    private String name, email, password, mobile, id, image, uid, location, sick, number_old,
    name_hag, uid_hag, location_hag, number_old_hag, sick_hag, mobile_hag;
    public Fetch3()
    {

    }

    public Fetch3(String name, String name_hag, String uid_hag, String location_hag, String number_old_hag, String sick_hag, String mobile_hag, String location, String sick, String number_old, String email, String mobile, String password, String uid, String id, String image, String imageId1,  String imageId2) {
        this.name = name;
        this.email = email;
        this.mobile_hag = mobile_hag;
        this.name_hag = name_hag;
        this.uid_hag = uid_hag;
        this.location_hag = location_hag;
        this.number_old_hag = number_old_hag;
        this.sick_hag = sick_hag;
        this.location = location;
        this.sick = sick;
        this.number_old = number_old;
        this.password = password;
        this.image = image;
        this.mobile = mobile;
        this.id = id;
        this.uid = uid;
    }

    public String getName_hag() {
        return name_hag;
    }

    public void setName_hag(String name_hag) {
        this.name_hag = name_hag;
    }

    public String getUid_hag() {
        return uid_hag;
    }

    public void setUid_hag(String uid_hag) {
        this.uid_hag = uid_hag;
    }

    public String getLocation_hag() {
        return location_hag;
    }

    public void setLocation_hag(String location_hag) {
        this.location_hag = location_hag;
    }

    public String getNumber_old_hag() {
        return number_old_hag;
    }

    public void setNumber_old_hag(String number_old_hag) {
        this.number_old_hag = number_old_hag;
    }

    public String getSick_hag() {
        return sick_hag;
    }

    public void setSick_hag(String sick_hag) {
        this.sick_hag = sick_hag;
    }

    public String getMobile_hag() {
        return mobile_hag;
    }

    public void setMobile_hag(String mobile_hag) {
        this.mobile_hag = mobile_hag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSick() {
        return sick;
    }

    public void setSick(String sick) {
        this.sick = sick;
    }

    public String getNumber_old() {
        return number_old;
    }

    public void setNumber_old(String number_old) {
        this.number_old = number_old;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
