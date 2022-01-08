package com.example.wastify_codejod;

public class UserForLeave {
    String name_leave,date_leave,leave_reason;
    public UserForLeave(){}
    public UserForLeave(String name_leave, String date_leave, String leave_reason) {
        this.name_leave = name_leave;
        this.date_leave = date_leave;
        this.leave_reason = leave_reason;
    }

    public String getName_leave() {
        return name_leave;
    }

    public void setName_leave(String name_leave) {
        this.name_leave = name_leave;
    }

    public String getDate_leave() {
        return date_leave;
    }

    public void setDate_leave(String date_leave) {
        this.date_leave = date_leave;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }
}
