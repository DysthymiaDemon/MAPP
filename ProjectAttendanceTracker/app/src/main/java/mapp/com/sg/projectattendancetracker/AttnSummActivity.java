package mapp.com.sg.projectattendancetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import mapp.com.sg.projectattendancetracker.model.dbCurrMonth;
import mapp.com.sg.projectattendancetracker.model.dbProfile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener{

    private String email;
    private String username;
    private int monthNumber;
    private SharedPreferences preferences;
    public static final String MYPREFERENCES = "myPrefs";
    public static final String UsernameKey = "user_name";
    public static final String EmailKey = "email_id";

    //init db
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_attnsumm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void onStart(){
        super.onStart();
        if(getIntent().getStringExtra("EMAIL_ID") != null){
            email = getIntent().getStringExtra("EMAIL_ID");
            username = getUsername(email);
            setSharedPreferences(EmailKey,email);
            setSharedPreferences(UsernameKey,username);

        } else{
            email = getSharedPreferences(EmailKey);
            username = getSharedPreferences(UsernameKey);
        }
        dbSetProfile(username);
        dbSetCurrMonthAttn(username);
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

    //db and related callable methods
//--------------------------------------------------------------------------------------------------
    public void dbSetProfile(String username) {
        DocumentReference docRef = db.collection("users").document(username)
                .collection("profile").document();
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dbProfile profile = documentSnapshot.toObject(dbProfile.class);
            }
        });
    }

    public void dbSetCurrMonthAttn(String username){
        int monthAsInt = Calendar.getInstance().get(Calendar.MONTH);
        DocumentReference documentReference = db.collection("users").document(username)
                .collection("currMonthAttn").document(Integer.toString(monthAsInt));
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dbCurrMonth currMonth = documentSnapshot.toObject(dbCurrMonth.class);
            }
        });
    }

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
//--------------------------------------------------------------------------------------------------

}
