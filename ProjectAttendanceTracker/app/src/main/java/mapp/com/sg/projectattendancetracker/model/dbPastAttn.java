package mapp.com.sg.projectattendancetracker.model;

public class dbPastAttendance {
    private int leaveCount;
    private int attendanceRate;

    public dbPastAttendance(){}

    public dbPastAttendance(int leaveCount, int attendanceRate) {
        this.leaveCount = leaveCount;
        this.attendanceRate = attendanceRate;
    }

    //get methods
    public int getLeaveCount() {
        return leaveCount;
    }

    public int getAttendanceRate() {
        return attendanceRate;
    }

    //set methods
    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public void setAttendanceRate(int attendanceRate) {
        this.attendanceRate = attendanceRate;
    }
}