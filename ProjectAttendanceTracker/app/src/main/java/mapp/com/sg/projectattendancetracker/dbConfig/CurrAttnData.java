package mapp.com.sg.projectattendancetracker.dbConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.ATTNSTATUS;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKIN;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKOUT;
import static mapp.com.sg.projectattendancetracker.Constants.LEAVE;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_CURRATTN;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;

public class CurrAttnData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ronnietan.db";
    private static final int DATABASE_VERSION = 3;

    public CurrAttnData(Context ctx){
        super(ctx, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME_CURRATTN+"( "+_ID
        +" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERNAME+" TEXT, "
                +CLOCKIN+" TEXT, "+CLOCKOUT+
                " TEXT, "+ATTNSTATUS+" TEXT, "+LEAVE+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CURRATTN);
        onCreate(db);
    }
}
