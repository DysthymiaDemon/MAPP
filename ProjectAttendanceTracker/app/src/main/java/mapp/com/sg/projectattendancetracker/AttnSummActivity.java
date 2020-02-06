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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mapp.com.sg.projectattendancetracker.model.Profiledata;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.BIRTHDATE;
import static mapp.com.sg.projectattendancetracker.Constants.EMAIL;
import static mapp.com.sg.projectattendancetracker.Constants.JOB;
import static mapp.com.sg.projectattendancetracker.Constants.MAXANNUAL;
import static mapp.com.sg.projectattendancetracker.Constants.NAME;
import static mapp.com.sg.projectattendancetracker.Constants.SALARYTIER;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME;
import static mapp.com.sg.projectattendancetracker.Constants.WORKPLACE;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener{

    private String email;
    private String username;
    private int monthNumber;
    private Bitmap profileImgBmp;

    //init SharedPreferences
    private SharedPreferences preferences;
    public static final String MYPREFERENCES = "myPrefs";
    public static final String UsernameKey = "user_name";
    public static final String EmailKey = "email_id";

    //init SQLite
    private static String[] FROM = {_ID,BIRTHDATE,EMAIL,NAME,JOB,WORKPLACE,MAXANNUAL,SALARYTIER};
    private Profiledata profile;

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

    //db-to-model callable methods
//--------------------------------------------------------------------------------------------------
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
        db.insertOrThrow(TABLE_NAME,null, values);
    }
    private Cursor getProfile(){
        SQLiteDatabase db = profile.getReadableDatabase();
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
