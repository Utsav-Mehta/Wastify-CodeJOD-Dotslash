package com.example.wastify_codejod;

public class Users {
    String name_complain,phone_complain,complain;

    public Users(){}

    public Users(String name_complain, String phone_complain, String complain) {
        this.name_complain = name_complain;
        this.phone_complain = phone_complain;
        this.complain = complain;
    }

    public String getName_complain() {
        return name_complain;
    }

    public void setName_complain(String name_complain) {
        this.name_complain = name_complain;
    }

    public String getPhone_complain() {
        return phone_complain;
    }

    public void setPhone_complain(String phone_complain) {
        this.phone_complain = phone_complain;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }
}
