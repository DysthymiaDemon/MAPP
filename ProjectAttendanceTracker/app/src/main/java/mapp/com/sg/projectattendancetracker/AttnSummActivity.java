package mapp.com.sg.projectattendancetracker;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener {

    private String emailID;

    @Override
    public void onStart(){
        super.onStart();
        if(getIntent().getStringExtra("EMAIL_ID") != null){
            emailID = getIntent().getStringExtra("EMAIL_ID");
        }
    }

    @Override
    public void onClick(View v) {

    }
}
