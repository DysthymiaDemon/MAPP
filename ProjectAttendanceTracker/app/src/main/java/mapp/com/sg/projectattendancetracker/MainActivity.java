package mapp.com.sg.projectattendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.settings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
                break;
        }

    return false;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.markAttn:
                Intent markAttnIntent = new Intent(this, MarkAttendanceActivity.class);
                startActivity(markAttnIntent);
                break;

            case R.id.attnSumm:
                Intent attnSumIntent = new Intent(this, AttendanceSummaryActivity.class);
                startActivity(attnSumIntent);
                break;

            case R.id.toEmployer:
                Intent toEmployerIntent = new Intent(this, ToEmployerActivity.class);
                startActivity(toEmployerIntent);
                break;
        }
    }
}
