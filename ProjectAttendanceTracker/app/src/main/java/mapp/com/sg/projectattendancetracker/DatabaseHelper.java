package mapp.com.sg.projectattendancetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.ATTNSTATUS;
import static mapp.com.sg.projectattendancetracker.Constants.BIRTHDATE;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKIN;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKOUT;
import static mapp.com.sg.projectattendancetracker.Constants.DETAILS;
import static mapp.com.sg.projectattendancetracker.Constants.EMAIL;
import static mapp.com.sg.projectattendancetracker.Constants.END;
import static mapp.com.sg.projectattendancetracker.Constants.JOB;
import static mapp.com.sg.projectattendancetracker.Constants.LEAVE;
import static mapp.com.sg.projectattendancetracker.Constants.MAXANNUAL;
import static mapp.com.sg.projectattendancetracker.Constants.NAME;
import static mapp.com.sg.projectattendancetracker.Constants.SALARYTIER;
import static mapp.com.sg.projectattendancetracker.Constants.START;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_CURRATTN;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PASTATTN;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PROFILE;
import static mapp.com.sg.projectattendancetracker.Constants.TYPE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;
import static mapp.com.sg.projectattendancetracker.Constants.WORKPLACE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ronnietan.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME_PROFILE+ " ("+_ID
        +" INTEGER PRIMARY KEY AUTOINCREMENT, "+BIRTHDATE+
                " TEXT, "+EMAIL+" TEXT NOT NULL, "+NAME+" TEXT NOT NULL, "+JOB+" TEXT NOT NULL,"+WORKPLACE+" TEXT NOT NULL,"+
                MAXANNUAL+" INTEGER, "+SALARYTIER+" INTEGER);");

        db.execSQL("CREATE TABLE "+TABLE_NAME_CURRATTN+"( "+_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERNAME+" TEXT, "
                +CLOCKIN+" TEXT, "+CLOCKOUT+
                " TEXT, "+ATTNSTATUS+" TEXT, "+LEAVE+" TEXT);");

        db.execSQL("CREATE TABLE "+TABLE_NAME_PASTATTN+"( "+_ID
        +" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERNAME+" TEXT NOT NULL, "+TYPE+" TEXT, "+START+" TEXT, "+END+" TEXT, "+DETAILS+" TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CURRATTN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PASTATTN);
        onCreate(db);
    }

}
