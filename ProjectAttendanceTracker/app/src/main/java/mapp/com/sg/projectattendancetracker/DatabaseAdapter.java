package mapp.com.sg.projectattendancetracker;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.ATTNRATE;
import static mapp.com.sg.projectattendancetracker.Constants.LEAVES;
import static mapp.com.sg.projectattendancetracker.Constants.MONTH;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PASTATTN;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;

public class DatabaseAdapter {
    Context ctx;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    private static String[] FROM_PASTNATTN = {_ID, MONTH, ATTNRATE, LEAVES};

    public DatabaseAdapter(Context ctx){
        this.ctx = ctx;
        dbHelper = new DatabaseHelper(ctx);
    }

    public ArrayList<PastAttn> getPastAttn(){
        ArrayList<PastAttn> pastAttns = new ArrayList<>();
        String[] columns={_ID, MONTH, ATTNRATE, LEAVES};

        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_PASTATTN, FROM_PASTNATTN,null,null,null,null,null);

            PastAttn pastAttn = new PastAttn();

            if(cursor != null){
                while (cursor.moveToNext()){
                    pastAttn.setMonth(cursor.getString(1));
                    pastAttn.setAttnRate(cursor.getString(2));
                    pastAttn.setLeaves(cursor.getString(3));

                    pastAttns.add(pastAttn);
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return pastAttns;
    }
}
