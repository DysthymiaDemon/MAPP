package mapp.com.sg.projectattendancetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import mapp.com.sg.projectattendancetracker.model.dbCurrMonth;
import mapp.com.sg.projectattendancetracker.model.dbProfile;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener {

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
    public void onClick(View v) {

    }


    //callable methods
//--------------------------------------------------------------------------------------------------
    public String getUsername(String email){
        String arr[] = email.split("@",2);
        username = arr[0];
        Log.d("Username", username);
        return username;
    }

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
}
