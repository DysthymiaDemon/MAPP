package mapp.com.sg.projectattendancetracker;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener {

    private String email;
    private String username;
    private SharedPreferences preferences;

    //init db
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Task<QuerySnapshot> query;
    private QuerySnapshot querySnapshot;


    @Override
    public void onStart(){
        super.onStart();
        if(getIntent().getStringExtra("EMAIL_ID") != null){
            email = getIntent().getStringExtra("EMAIL_ID");
            username = getUsername(email);
            setSharedPreferences("email_id",email);
            setSharedPreferences("user_name",username);

        } else{
            email = getSharedPreferences("email_id");
            username = getSharedPreferences("user_name");
        }

        dbQueryProfile(email);
    }

    @Override
    public void onClick(View v) {

    }

    public String getUsername(String email){
        String arr[] = email.split("@",2);
        username = arr[0];
        Log.d("Username", username);
        return username;
    }

    public void dbQueryProfile(String email){
        query = db
                .collection("users").document(username)
                .collection("profile").whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            querySnapshot = task.getResult();
                        }else{
                            querySnapshot = null;
                        }
                    }
                });
    }

    public void setSharedPreferences(String key, String username){
        preferences = getSharedPreferences("myPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, username);
    }

    public String getSharedPreferences(String key){
        preferences =  getSharedPreferences("myPrefs", 0); //0 for private mode
        username = preferences.getString(key,null);
        return username;
    }
}
