package com.example.wastify_codejod;

public class UserForLoc {
    String locality,address_line, date, time,driver_name;
    double longitude,latitude;

    public UserForLoc(){}

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public UserForLoc(String locality, String address_line, String date, String time, String driver_name, double longitude, double latitude) {
        this.locality = locality;
        this.address_line = address_line;
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.driver_name=driver_name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
