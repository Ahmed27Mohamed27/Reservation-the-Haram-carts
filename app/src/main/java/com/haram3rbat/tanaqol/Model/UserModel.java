package com.haram3rbat.tanaqol.Model;

public class UserModel {
    String name,email,country,ID,password,location,mobileNumber,deviceID,Image,phoneCode,NumberOld,SickHag;

    public UserModel(String name, String NumberOld, String location, String SickHag, String phoneCode, String email, String Image, String ID, String country, String password, String mobileNumber, String deviceID) {
        this.name = name;
        this.email = email;
        this.NumberOld = NumberOld;
        this.location = location;
        this.SickHag = SickHag;
        this.ID = ID;
        this.phoneCode = phoneCode;
        this.Image = Image;
        this.country = country;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.deviceID = deviceID;
    }

    public UserModel(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getNumberOld() {
        return NumberOld;
    }

    public void setNumberOld(String numberOld) {
        NumberOld = numberOld;
    }

    public String getSickHag() {
        return SickHag;
    }

    public void setSickHag(String sickHag) {
        SickHag = sickHag;
    }
}
