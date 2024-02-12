package com.haram3rbat.tanaqol.Model;

public class User_Hagz {
    String time, data, location, time_of_hagz, kind_car, size_car, state_car, time_data_recive, state_of_hagz, code_car, none;

    public User_Hagz(String time, String location, String data, String size_car, String state_car, String time_data_recive, String state_of_hagz, String time_of_hagz, String kind_car, String code_car, String none) {
        this.time = time;
        this.data = data;
        this.location = location;
        this.time_of_hagz = time_of_hagz;
        this.size_car = size_car;
        this.state_car = state_car;
        this.time_data_recive = time_data_recive;
        this.kind_car = kind_car;
        this.state_of_hagz = state_of_hagz;
        this.code_car = code_car;
        this.none = none;
    }

    public User_Hagz()
    {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime_of_hagz() {
        return time_of_hagz;
    }

    public void setTime_of_hagz(String time_of_hagz) {
        this.time_of_hagz = time_of_hagz;
    }

    public String getKind_car() {
        return kind_car;
    }

    public void setKind_car(String kind_car) {
        this.kind_car = kind_car;
    }

    public String getSize_car() {
        return size_car;
    }

    public void setSize_car(String size_car) {
        this.size_car = size_car;
    }

    public String getState_car() {
        return state_car;
    }

    public void setState_car(String state_car) {
        this.state_car = state_car;
    }

    public String getTime_data_recive() {
        return time_data_recive;
    }

    public void setTime_data_recive(String time_data_recive) {
        this.time_data_recive = time_data_recive;
    }

    public String getState_of_hagz() {
        return state_of_hagz;
    }

    public void setState_of_hagz(String state_of_hagz) {
        this.state_of_hagz = state_of_hagz;
    }

    public String getCode_car() {
        return code_car;
    }

    public void setCode_car(String code_car) {
        this.code_car = code_car;
    }

    public String getNone() {
        return none;
    }

    public void setNone(String none) {
        this.none = none;
    }
}
