package mapp.com.sg.projectattendancetracker.model;

import java.sql.Timestamp;

public class dbCurrMonth {
    private int attnStatus;
    private Timestamp clockIn;
    private Timestamp clockOut;

    public dbCurrMonth(){}

    public dbCurrMonth(int attnStatus, Timestamp clockIn, Timestamp clockOut) {
        this.attnStatus = attnStatus;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    //get methods
    public int getAttnStatus() {
        return attnStatus;
    }

    public Timestamp getClockIn() {
        return clockIn;
    }

    public Timestamp getClockOut() {
        return clockOut;
    }

    //set methods
    public void setAttnStatus(int attnStatus) {
        this.attnStatus = attnStatus;
    }

    public void setClockIn(Timestamp clockIn) {
        this.clockIn = clockIn;
    }

    public void setClockOut(Timestamp clockOut) {
        this.clockOut = clockOut;
    }
}
