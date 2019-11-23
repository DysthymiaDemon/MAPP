package mapp.com.sg.projectattendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.markAttendance:
                Intent markAttnIntent = new Intent(this, MarkAttendanceActivity.class);
                startActivity(markAttnIntent);
                break;

            case R.id.attendanceSummary:
                Intent attnSumIntent = new Intent(this, AttendanceSummaryActivity.class);
                startActivity(attnSumIntent);
                break;

            case R.id.Employer:
                Intent toEmployerIntent = new Intent(this, ToEmployerActivity.class);
                startActivity(toEmployerIntent);
                break;
        }
    }
}
