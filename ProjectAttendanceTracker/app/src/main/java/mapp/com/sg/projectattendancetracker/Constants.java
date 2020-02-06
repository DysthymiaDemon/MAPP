package mapp.com.sg.projectattendancetracker;

import android.provider.BaseColumns;

import java.sql.Timestamp;

public interface Constants extends BaseColumns{
    public static final String TABLE_NAME_PROFILE = "profile";
    public static final String TABLE_NAME_CURRATTN = "currattn";
    public static final String TABLE_NAME_PASTATTN = "pastattn";

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

}
