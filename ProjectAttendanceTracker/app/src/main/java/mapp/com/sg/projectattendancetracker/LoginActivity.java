package mapp.com.sg.projectattendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;
    private EditText emailTextbox;
    private EditText passwordTextbox;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        emailTextbox = (EditText) findViewById(R.id.);
        passwordTextbox = (EditText) findViewById(R.id.);
        buttonLogin.setOnClickListener(this);
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
        switch (item.getItemId()){
            //to complete
            //case R.id.settings:
                //Intent settingsIntent = new Intent(this, Settings.class);
                //startActivity(settingsIntent);
                //break;
        }

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
}
