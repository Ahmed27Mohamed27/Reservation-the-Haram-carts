package com.haram3rbat.tanaqol.Admin.Motabara_data;

public class Fetch2 {
    private String name, email, password, mobile, id, image, uid, location, sick, number_old;
    public Fetch2()
    {

    }

    public Fetch2(String name, String location, String sick, String number_old, String email, String mobile, String password, String uid, String id, String image, String imageId1,  String imageId2) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.sick = sick;
        this.number_old = number_old;
        this.password = password;
        this.image = image;
        this.mobile = mobile;
        this.id = id;
        this.uid = uid;
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
