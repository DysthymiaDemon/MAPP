package mapp.com.sg.projectattendancetracker;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mapp.com.sg.projectattendancetracker.dbConfig.CurrAttnData;
import mapp.com.sg.projectattendancetracker.dbConfig.ProfileData;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.ATTNSTATUS;
import static mapp.com.sg.projectattendancetracker.Constants.BIRTHDATE;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKIN;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKOUT;
import static mapp.com.sg.projectattendancetracker.Constants.EMAIL;
import static mapp.com.sg.projectattendancetracker.Constants.JOB;
import static mapp.com.sg.projectattendancetracker.Constants.LEAVE;
import static mapp.com.sg.projectattendancetracker.Constants.MAXANNUAL;
import static mapp.com.sg.projectattendancetracker.Constants.NAME;
import static mapp.com.sg.projectattendancetracker.Constants.SALARYTIER;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_CURRATTN;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PROFILE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;
import static mapp.com.sg.projectattendancetracker.Constants.WORKPLACE;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener{

    private String email;
    private String username;
    private int monthNumber;
    private Bitmap profileImgBmp;

    //init SharedPreferences
    public static SharedPreferences preferences;
    public static final String MYPREFERENCES = "myPrefs";
    public static final String UsernameKey = "user_name";
    public static final String EmailKey = "email_id";

    //profile db prep
    private static String[] FROM_PROFILE = {_ID,BIRTHDATE,EMAIL,NAME,JOB,WORKPLACE,MAXANNUAL,SALARYTIER};
    private ProfileData profile;

    //currAttn db prep
    private static String[] FROM_CURRATTN = {_ID,CLOCKIN,CLOCKOUT,ATTNSTATUS};
    private CurrAttnData currAttn;

    //init firestore storage
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();


    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_attnsumm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //get email, convert to username
        if(getIntent().getStringExtra("EMAIL_ID") != null){
            email = getIntent().getStringExtra("EMAIL_ID");
            username = getUsername(email);
            setSharedPreferences(EmailKey,email);
            setSharedPreferences(UsernameKey,username);

        } else{
            email = getSharedPreferences(EmailKey);
            username = getSharedPreferences(UsernameKey);
        }

        //profile image
        profileImgBmp = getProfileImage(username);
        loadImageToUi(profileImgBmp);

        //profile db
        addProfile(email,username);
        showProfile(getProfile());

        //currAttn db



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attnsumm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //switch (item.getItemId()){
            //case R.id.settings:
            //Intent settingsIntent = new Intent(this, Settings.class);
            //startActivity(settingsIntent);
            //break;
        //}

        return false;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //use as reference
            //case R.id.markAttn:
            //Intent markAttnIntent = new Intent(this, MarkAttendanceActivity.class);
            //startActivity(markAttnIntent);
            //break;
        }
    }


    //general callable methods
//--------------------------------------------------------------------------------------------------
    public String getUsername(String email){
        String arr[] = email.split("@",2);
        username = arr[0];
        Log.d("Username", username);
        return username;
    }

    public void setAppBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getText(R.string.attn_summ));
    }

    //db-to-dbConfig callable methods
//--------------------------------------------------------------------------------------------------
    //profile methods
    private void addProfile(String email, String username){
        SQLiteDatabase db = profile.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BIRTHDATE, "12/08/1962");
        values.put(EMAIL, email);
        values.put(NAME, username);
        values.put(JOB, "Security Guard");
        values.put(WORKPLACE, "Singapore Polytechnic");
        values.put(MAXANNUAL,12);
        values.put(SALARYTIER,2);
        db.insertOrThrow(TABLE_NAME_PROFILE,null, values);
    }
    private Cursor getProfile(){
        SQLiteDatabase db = profile.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_PROFILE, FROM_PROFILE, null,null,null,null,null);
        return cursor;
    }

    private void showProfile(Cursor cursor){
        long id;
        String bday;
        String email;
        String name = null;
        String job = null;
        String workplace = null;
        int maxannual;
        int salarytier = 0;

        while(cursor.moveToFirst()){
            id = cursor.getLong(0);
            bday = cursor.getString(1);
            email = cursor.getString(2);
            name = cursor.getString(3);
            job = cursor.getString(4);
            workplace = cursor.getString(5);
            maxannual = cursor.getInt(6);
            salarytier = cursor.getInt(7);
        }

        ((TextView) findViewById(R.id.nameTextView)).setText(name);
        ((TextView) findViewById(R.id.jobTitleTextView)).setText(job);
        ((TextView) findViewById(R.id.jobLocTextView)).setText(workplace);
        ((TextView) findViewById(R.id.salaryTierTextView)).setText("Tier "+salarytier);

        cursor.close();
    }

    //currAttn methods
    private void addCurrAttn(String username){
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_hh:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());*/

        SQLiteDatabase db = currAttn.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put(USERNAME, username);
        values1.put(CLOCKIN, "01/02/2020 07:13:21");
        values1.put(CLOCKOUT, "01/02/2020 18:10:31");
        values1.put(ATTNSTATUS, "1");
        values1.put(LEAVE, "1");
        db.insertOrThrow(TABLE_NAME_CURRATTN,null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(USERNAME, username);
        values2.put(CLOCKIN, "01/02/2020 07:13:21");
        values2.put(CLOCKOUT, "01/02/2020 18:10:31");
        values2.put(ATTNSTATUS, "1");
        values2.put(LEAVE, "1");
        db.insertOrThrow(TABLE_NAME_CURRATTN,null, values2);
    }

    private Cursor getCurrAttn(){
        SQLiteDatabase db = currAttn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CURRATTN, FROM_CURRATTN,null,null,null,null,null);
        return cursor;
    }

    private void showCurrAttn(Cursor cursor){
        int countPresent = 0;
        int countLeave = 0;
        int MIA = 0;

        while(cursor.moveToNext()){
            if(cursor.getString(4) == "1"){
                countPresent++;
            } else if (cursor.getString(5) == "1"){
                countLeave++;
            } else{
                MIA++;
            }
        }

        //numbers to progressbar
    }

    //SharedPreferences callable methods
//--------------------------------------------------------------------------------------------------
    public void setSharedPreferences(String key, String username){
        preferences = getSharedPreferences(MYPREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, username);
        editor.commit();
    }

    public String getSharedPreferences(String key){
        preferences =  getSharedPreferences(MYPREFERENCES, 0); //0 for private mode
        String keyValue = preferences.getString(key,"");
        return keyValue;
    }

    //ImageView callable methods
//--------------------------------------------------------------------------------------------------
    public Bitmap getProfileImage(String username){
        StorageReference profileImgRef = storageRef.child("profileImages/"+username+".jpg");
        //final ImageView profileImage = new ImageView(this);
        final Bitmap[] bmp = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        profileImgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bmp[0] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                //profileImage.setImageBitmap(bmp[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception ex) {
                Log.d("profileImageEx", "exception", ex);
            }
        });

        return bmp[0];
    }

    public void loadImageToUi(Bitmap profileImageBmp){
        ImageView defaultImage = (ImageView) findViewById(R.id.profileImageView);
        defaultImage.setImageBitmap(profileImageBmp);
    }
}
