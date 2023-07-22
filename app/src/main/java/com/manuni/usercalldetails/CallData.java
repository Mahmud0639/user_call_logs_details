package com.manuni.usercalldetails;

public class CallData {
    private String phoneNumber,nameUser;
    private int callType;
    private long callDate;
    private long callDuration;

    public CallData(String phoneNumber, int callType, long callDate, long callDuration,String nameUser) {
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.nameUser = nameUser;

    }


    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public long getCallDate() {
        return callDate;
    }

    public void setCallDate(long callDate) {
        this.callDate = callDate;
    }

    public long getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(long callDuration) {
        this.callDuration = callDuration;
    }
}
