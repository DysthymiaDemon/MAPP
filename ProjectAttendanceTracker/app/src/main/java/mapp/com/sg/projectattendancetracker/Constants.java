package mapp.com.sg.projectattendancetracker;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns{
    public static final String TABLE_NAME = "profile";

    // columns
    public static final String BIRTHDATE = "date";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String JOB = "job";
    public static final String WORKPLACE = "workplace";
    public static final String MAXANNUAL = "maxannual";
    public static final String SALARYTIER = "salarytier";

}
