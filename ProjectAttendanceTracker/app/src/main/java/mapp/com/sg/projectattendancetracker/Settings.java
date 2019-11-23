package mapp.com.sg.projectattendancetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onClick (View view){
        switch (view.getId()){
            case R.id.logout:
                Toast.makeText(getApplicationContext(),"Logout...",
                        Toast.LENGTH_SHORT).show();

                AlertDialog alertDialog = new AlertDialog.Builder(Settings.this).create();
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Are you sure you want to log out?");

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });


                alertDialog.show();
                break;

            case R.id.payslip:
                Toast.makeText(getApplicationContext(),"Payslips...",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
