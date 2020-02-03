package mapp.com.sg.projectattendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //AttnSummActivity will need sharedPreferences, not the login page right now.

    private Button buttonLogin;
    private EditText usernameTextbox;
    private EditText passwordTextbox;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        usernameTextbox = (EditText) findViewById(R.id.emailTextbox);
        passwordTextbox = (EditText) findViewById(R.id.passwordTextbox);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(currentUser != null){
                    startActivity(
                            new Intent(LoginActivity.this, AttnSummActivity.class)
                    );
                }
            }
        };

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        final String email = usernameTextbox.getText().toString().trim();
        String password = passwordTextbox.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.setMessage("Logging in...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Sign in failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, AttnSummActivity.class);
                        intent.putExtra("EMAIL_ID", email);
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_attnsumm, menu);
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
