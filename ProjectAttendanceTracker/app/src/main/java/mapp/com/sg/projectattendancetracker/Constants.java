package mapp.com.sg.projectattendancetracker;

import android.provider.BaseColumns;

import java.sql.Timestamp;

public interface Constants extends BaseColumns{
    public static final String TABLE_NAME_PROFILE = "profile";
    public static final String TABLE_NAME_CURRATTN = "currattn";
    public static final String TABLE_NAME_PASTATTN = "pastattn";
    public static final String TABLE_NAME_APPLYLEAVE = "applyleave";
    public static final String TABLE_NAME_REQOTHERS = "reqothers";
    public static final String TABLE_NAME_REPORTPROB = "reportprob";

    // columns profile
    public static final String BIRTHDATE = "date";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String JOB = "job";
    public static final String WORKPLACE = "workplace";
    public static final String MAXANNUAL = "maxannual";
    public static final String SALARYTIER = "salarytier";

    //columns currAttn
    public static final String USERNAME = "name";
    public static final String CLOCKIN = "clockin";
    public static final String CLOCKOUT = "clockout";
    public static final String ATTNSTATUS = "attnstatus";
    public static final String LEAVE = "leave";

    //columns pastAttn
//    public static final String USERNAME = "name";
    public static final String MONTH = "month";
    public static final String ATTNRATE = "attnrate";
    public static final String LEAVES = "leaves";

    //columns applyforleave
//    public static final String USERNAME = "name";
    public static final String TYPE = "type";
    public static final String START = "startLeave";
    public static final String END = "endLeave";
    public static final String DETAILS = "details";

    //columns requestOthers
//    public static final String USERNAME = "name";
    public static final String TITLE = "title";
//    public static final String DETAILS = "details";

    //columns reportProblems
//    public static final String USERNAME = "name";
//    public static final String TITLE = "title";
//    public static final String DETAILS = "details";

}
