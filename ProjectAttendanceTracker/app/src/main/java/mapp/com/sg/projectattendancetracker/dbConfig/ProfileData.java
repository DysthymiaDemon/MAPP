package mapp.com.sg.projectattendancetracker.dbConfig;

import android.content.Context;
import static android.provider.BaseColumns._ID;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.prefs.Preferences;

import mapp.com.sg.projectattendancetracker.AttnSummActivity;

import static mapp.com.sg.projectattendancetracker.Constants.BIRTHDATE;
import static mapp.com.sg.projectattendancetracker.Constants.EMAIL;
import static mapp.com.sg.projectattendancetracker.Constants.JOB;
import static mapp.com.sg.projectattendancetracker.Constants.MAXANNUAL;
import static mapp.com.sg.projectattendancetracker.Constants.NAME;
import static mapp.com.sg.projectattendancetracker.Constants.SALARYTIER;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PROFILE;
import static mapp.com.sg.projectattendancetracker.Constants.WORKPLACE;

public class ProfileData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ronnietan.db";
    private static final int DATABASE_VERSION = 1;

    public ProfileData(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME_PROFILE+ " ("+_ID
        +" INTEGER PRIMARY KEY AUTOINCREMENT, "+BIRTHDATE+
                " TEXT,"+EMAIL+" TEXT NOT NULL,"+NAME+"TEXT NOT NULL, "+JOB+" TEXT NOT NULL,"+WORKPLACE+" TEXT NOT NULL,"+
                MAXANNUAL+" INTEGER, "+SALARYTIER+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROFILE);
        onCreate(db);
    }

}
