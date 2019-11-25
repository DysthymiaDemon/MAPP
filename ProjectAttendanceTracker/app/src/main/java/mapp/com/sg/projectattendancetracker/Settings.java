package mapp.com.sg.projectattendancetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                break;
        }

        return false;
    }

    @Override
    public void onClick (View view){
        switch (view.getId()){
            case R.id.logout:
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
//                Toast.makeText(getApplicationContext(),"Logout...",
//                        Toast.LENGTH_SHORT).show();
//
//                AlertDialog alertDialog = new AlertDialog.Builder(Settings.this).create();
//                alertDialog.setTitle("Logout");
//                alertDialog.setMessage("Are you sure you want to log out?");
//
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NO",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            }
//                        });
//                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "YES",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//                                homeIntent.addCategory(Intent.CATEGORY_HOME);
//                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(homeIntent);
//                            }
//                        }
//
//                );
//                alertDialog.show();
                break;

            case R.id.viewPayslip:
                Toast.makeText(getApplicationContext(),"Payslips...",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
