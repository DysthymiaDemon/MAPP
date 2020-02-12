package mapp.com.sg.projectattendancetracker;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import com.google.android.material.navigation.NavigationView;

import java.security.spec.ECField;
import java.util.Calendar;

import static android.provider.BaseColumns._ID;
import static android.text.style.TtsSpan.ARG_USERNAME;
import static mapp.com.sg.projectattendancetracker.Constants.ATTNRATE;
import static mapp.com.sg.projectattendancetracker.Constants.ATTNSTATUS;
import static mapp.com.sg.projectattendancetracker.Constants.BIRTHDATE;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKIN;
import static mapp.com.sg.projectattendancetracker.Constants.CLOCKOUT;
import static mapp.com.sg.projectattendancetracker.Constants.DETAILS;
import static mapp.com.sg.projectattendancetracker.Constants.EMAIL;
import static mapp.com.sg.projectattendancetracker.Constants.END;
import static mapp.com.sg.projectattendancetracker.Constants.JOB;
import static mapp.com.sg.projectattendancetracker.Constants.LEAVE;
import static mapp.com.sg.projectattendancetracker.Constants.LEAVES;
import static mapp.com.sg.projectattendancetracker.Constants.MAXANNUAL;
import static mapp.com.sg.projectattendancetracker.Constants.MONTH;
import static mapp.com.sg.projectattendancetracker.Constants.NAME;
import static mapp.com.sg.projectattendancetracker.Constants.SALARYTIER;
import static mapp.com.sg.projectattendancetracker.Constants.START;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_APPLYLEAVE;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_CURRATTN;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PASTATTN;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_PROFILE;
import static mapp.com.sg.projectattendancetracker.Constants.TYPE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;
import static mapp.com.sg.projectattendancetracker.Constants.WORKPLACE;

public class AttnSummActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private String email;
    private String username;
    private EditText monthEditText;
    private EditText attnRateEditText;
    private EditText leavesEditText;
    private DatabaseHelper databaseHelper;
    private DrawerLayout drawerLayout;
    TableView<String[]> tb;
    TableHelper tableHelper;

    private apply4LeaveFragment apply4leavefragment;
    private requestOthersFragment requestothersfragment;
    private reportProblemFragment reportproblemfragment;

    //init SharedPreferences
    public static SharedPreferences preferences;
    public static final String MYPREFERENCES = "myPrefs";
    public static final String UsernameKey = "user_name";
    public static final String EmailKey = "email_id";
    public static final String loadDbProfileKey = "db_profile_num";
    public static final String loadDbCurrAttnKey = "db_currattn_num";
    public static final String loadDbPastAttnKey = "db_pastattn_num";

    //db fetch prep
    private static String[] FROM_PROFILE = {_ID, BIRTHDATE, EMAIL, NAME, JOB, WORKPLACE, MAXANNUAL, SALARYTIER};
    private static String[] FROM_CURRATTN = {_ID, USERNAME, CLOCKIN, CLOCKOUT, ATTNSTATUS, LEAVE};
    private static String[] FROM_PASTNATTN = {_ID, MONTH, ATTNRATE, LEAVES};
    private static String[] FROM_APPLYLEAVE = {_ID, USERNAME, TYPE, START, END, DETAILS};

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_attnsumm);
        setTitle(R.string.attn_summ);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigationDrawerOpen, R.string.navigationDrawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //get email, convert to username
        if (getIntent().getStringExtra("EMAIL_ID") != null) {
            email = getIntent().getStringExtra("EMAIL_ID");
            System.out.println("email="+email);
            username = getUsername(email);
            System.out.println("username="+username);
            setSharedPreferences(EmailKey, email);
            setSharedPreferences(UsernameKey, username);

        } else {
            email = getSharedPreferences(EmailKey);
            username = getSharedPreferences(UsernameKey);
        }

        databaseHelper = new DatabaseHelper(this);

        //profile image
        //loadProfileImg(username);
        System.out.println("USERNAME = "+username);
        System.out.println("EMAIL = "+email);

        try{
            //profile db
            if (getSharedPreferences(loadDbProfileKey) != "1") {
                addProfile(email, username);
                setSharedPreferences(loadDbProfileKey, "1");
                showProfile(getProfile());
            } else {
                showProfile(getProfile());
            }
        } catch (Exception ex){
            Log.d("profile db", "Exception occured", ex);
            System.out.println(ex);
        }

        try{
            //currAttn db
            if (getSharedPreferences(loadDbCurrAttnKey) != "1") {
                this.addCurrAttn(username);
                setSharedPreferences(loadDbCurrAttnKey, "1");
                showCurrAttn(getCurrAttn());
            } else {
                showCurrAttn(getCurrAttn());
            }
        }catch (Exception ex){
            /*Log.d("currAttn db", "Exception occured", ex);*/
            System.out.println(ex);
        }

        /*try{
            //pastAttn db
            if (getSharedPreferences(loadDbPastAttnKey) != "1") {
                addPastAttn(username);
                setSharedPreferences(loadDbPastAttnKey, "1");
            }
        } catch (Exception ex){
            Log.d("PastAttn db", "Exception occured", ex);
            System.out.println(ex);
        }

        try{
            //pastattn db
            System.out.println("pastattn db display start");
            tableHelper=new TableHelper(this);
            tb = (TableView<String[]>) findViewById(R.id.tableView);
            tb.setColumnCount(3);
            tb.setHeaderBackgroundColor(Color.parseColor(String.valueOf(R.color.colorPrimary)));
            tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getPastAttnHeaders()));
            tb.setDataAdapter(new SimpleTableDataAdapter(this, tableHelper.getPastAttn()));
        }catch (Exception ex){
            Log.d("PastAttn db", "Exception occured", ex);
            System.out.println(ex);
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navAttnSumm:
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                //onCreate(new Bundle()); trying to remove fragments and just shot the AttnSummActivity page
                break;
            case R.id.navMarkAttn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new markAttnFragment()).commit();
                break;
            case R.id.navApplyForLeave:
                apply4LeaveFragment apply4leavefragment = apply4LeaveFragment.newInstance(username);
                apply4LeaveFragment fragment = new apply4LeaveFragment();
                Bundle args = new Bundle();
                args.putString(ARG_USERNAME, username);
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        apply4leavefragment).commit();
                break;
            case R.id.navReqOthers:
                requestOthersFragment requestothersfragment = requestOthersFragment.newInstance(username);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        requestothersfragment).commit();
                break;
            case R.id.navRepProblem:
                reportProblemFragment reportproblemfragment = reportProblemFragment.newInstance(username);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        reportproblemfragment).commit();
                break;
            case R.id.navSettings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navLogout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attnsumm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switch (item.getItemId()){
        //case R.id.settings:
        //Intent settingsIntent = new Intent(this, Settings.class);
        //startActivity(settingsIntent);
        //break;
        //}

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //use as reference
            //case R.id.markAttn:
            //Intent markAttnIntent = new Intent(this, MarkAttendanceActivity.class);
            //startActivity(markAttnIntent);
            //break;
        }
    }

    public void submitForm(View button){
            apply4leavefragment.submitForm(button);
    }

    public void submitRequest(View button){
        requestothersfragment.submitForm(button);
    }

    public void submitReport(View button){
        reportproblemfragment.submitReport(button);
    }

    //general callable methods
//--------------------------------------------------------------------------------------------------
    public String getUsername(String email) {
        String arr[] = email.split("@", 2);
        username = arr[0];
        Log.d("Username", username);
        return username;
    }



    public void setAppBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getText(R.string.attn_summ));
    }

    //db-to-DatabaseHelper callable methods
//--------------------------------------------------------------------------------------------------
    //profile methods
    private void addProfile(String email, String username) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BIRTHDATE, "12/08/1962");
        values.put(EMAIL, email);
        values.put(NAME, "Ronnie Tan");
        values.put(JOB, "Security Guard");
        values.put(WORKPLACE, "Singapore Polytechnic");
        values.put(MAXANNUAL, 12);
        values.put(SALARYTIER, 2);
        db.insertOrThrow(TABLE_NAME_PROFILE, null, values);
        System.out.println("addProfile done");
    }

    private Cursor getProfile() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_PROFILE, FROM_PROFILE, null, null, null, null, null);
        System.out.println("getProfile done");
        return cursor;
    }

    private void showProfile(Cursor cursor) {
        String bday;
        String email;
        String name = null;
        String job = null;
        String workplace = null;
        int maxannual;
        int salarytier = 0;

        while (cursor.moveToNext()) {
            name = cursor.getString(3);
            job = cursor.getString(4);
            workplace = cursor.getString(5);
            salarytier = cursor.getInt(7);
        }

        ((TextView) findViewById(R.id.nameTextView)).setText(name);
        ((TextView) findViewById(R.id.jobTitleTextView)).setText(job);
        ((TextView) findViewById(R.id.jobLocTextView)).setText(workplace);
        ((TextView) findViewById(R.id.salaryTierTextView)).setText("Tier " + Integer.toString(salarytier));

        System.out.println("showProfile done");
        cursor.close();
    }

    //currAttn methods
    private void addCurrAttn(String username) {
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_hh:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());*/
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CURRATTN, FROM_CURRATTN, null, null, null, null, null);

        if (cursor.getCount() < 3) {
            db = databaseHelper.getWritableDatabase();
            ContentValues values1 = new ContentValues();
            values1.put(USERNAME, username);
            values1.put(CLOCKIN, "01/02/2020 07:13:21");
            values1.put(CLOCKOUT, "01/02/2020 18:10:31");
            values1.put(ATTNSTATUS, "1");
            values1.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values1);

            ContentValues values2 = new ContentValues();
            values2.put(USERNAME, username);
            values2.put(CLOCKIN, "02/02/2020 07:01:56");
            values2.put(CLOCKOUT, "02/02/2020 18:00:06");
            values2.put(ATTNSTATUS, "1");
            values2.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values2);

            /*ContentValues values3 = new ContentValues();
            values3.put(USERNAME, username);
            values3.put(CLOCKIN, "03/02/2020 07:01:56");
            values3.put(CLOCKOUT, "03/02/2020 18:00:06");
            values3.put(ATTNSTATUS, "1");
            values3.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values3);

            ContentValues values4 = new ContentValues();
            values4.put(USERNAME, username);
            values4.put(CLOCKIN, "04/02/2020 07:01:56");
            values4.put(CLOCKOUT, "04/02/2020 18:00:06");
            values4.put(ATTNSTATUS, "1");
            values4.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values4);

            ContentValues values5 = new ContentValues();
            values5.put(USERNAME, username);
            values5.put(CLOCKIN, "05/02/2020 07:01:56");
            values5.put(CLOCKOUT, "05/02/2020 18:00:06");
            values5.put(ATTNSTATUS, "1");
            values5.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values5);

            ContentValues values6 = new ContentValues();
            values6.put(USERNAME, username);
            values6.put(CLOCKIN, "06/02/2020 07:01:56");
            values6.put(CLOCKOUT, "06/02/2020 18:00:06");
            values6.put(ATTNSTATUS, "1");
            values6.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values6);

            ContentValues values7 = new ContentValues();
            values7.put(USERNAME, username);
            values7.put(CLOCKIN, "07/02/2020 07:01:56");
            values7.put(CLOCKOUT, "07/02/2020 18:00:06");
            values7.put(ATTNSTATUS, "1");
            values7.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_CURRATTN, null, values7);*/
            System.out.println("addCurrAttn added db entries");
        } else {
            System.out.println("addCurrAttn did not add db entries");
            return;
        }
        cursor.close();
    }

    private Cursor getCurrAttn() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CURRATTN, FROM_CURRATTN, null, null, null, null, null);
        System.out.println("getCurrAttn done");
        return cursor;
    }

    private void showCurrAttn(Cursor cursor) {
        System.out.println("showCurrAttn start");
        int countPresent = 0;
        int countLeave = 0;
        int MIA = 0;

        while (cursor.moveToNext()) {
            if (cursor.getString(4).equals("1")) {
                countPresent++;
            } else if (!cursor.getString(5).equals("1")) {
                countLeave++;
            } else {
                MIA++;
            }
        }

        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        ((ProgressBar) findViewById(R.id.attnRateProgressBar)).setMax(monthMaxDays);
        ((ProgressBar) findViewById(R.id.attnRateProgressBar)).setProgress(countPresent);
        ((TextView) findViewById(R.id.attnRateNumTextView)).setText(countPresent + "/" + monthMaxDays);

        ((ProgressBar) findViewById(R.id.leaveProgressBar)).setMax(3);
        ((ProgressBar) findViewById(R.id.leaveProgressBar)).setProgress(countLeave);
        ((TextView) findViewById(R.id.leavesNumTextView)).setText(countLeave + "/" + 3);

        System.out.println("showCurrAttn done");
    }

    //pastAttn methods
    /*private void addPastAttn(String username){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_PASTATTN, FROM_PASTNATTN,null,null,null,null,null);
        if(cursor.getCount() < 5){
            db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USERNAME, username);
            values.put(MONTH, "January");
            values.put(ATTNSTATUS, "87");
            values.put(LEAVE, "3");
            db.insertOrThrow(TABLE_NAME_PASTATTN, null, values);

            ContentValues values1 = new ContentValues();
            values1.put(USERNAME, username);
            values1.put(MONTH, "December");
            values1.put(ATTNSTATUS, "94");
            values1.put(LEAVE, "2");
            db.insertOrThrow(TABLE_NAME_PASTATTN, null, values1);

            ContentValues values2 = new ContentValues();
            values2.put(USERNAME, username);
            values2.put(MONTH, "November");
            values2.put(ATTNSTATUS, "98");
            values2.put(LEAVE, "1");
            db.insertOrThrow(TABLE_NAME_PASTATTN, null, values2);

            ContentValues values3 = new ContentValues();
            values3.put(USERNAME, username);
            values3.put(MONTH, "October");
            values3.put(ATTNSTATUS, "85");
            values3.put(LEAVE, "4");
            db.insertOrThrow(TABLE_NAME_PASTATTN, null, values3);
            System.out.println("addPastAttn added db entries");
        } else{
            System.out.println("addPastAttn did not add db entries");
            return;
        }
        cursor.close();
    }

    private Cursor getPastAttn(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_PASTATTN, FROM_PASTNATTN,null,null,null, null, _ID+" DESC");
        System.out.println("getPastAttn done");
        return cursor;
    }

    //apply4leave methods
    private void addApply4Leave(String username, String leaveType, String leaveFrom, String leaveTo, String details){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(TYPE, leaveType);
        values.put(START, String.valueOf(leaveFrom));
        values.put(END, String.valueOf(leaveTo));
        values.put(DETAILS, String.valueOf(details));
        db.insertOrThrow(TABLE_NAME_APPLYLEAVE, null, values);
        System.out.println("addApply4Leave called???");
    }

    private Cursor getApply4Leave(String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_APPLYLEAVE, FROM_APPLYLEAVE, selection, selectionArgs, null, null, null);
        System.out.println("getApply4Leave called???");
        return cursor;
    }*/

    //SharedPreferences callable methods
//--------------------------------------------------------------------------------------------------
    public void setSharedPreferences(String key, String value) {
        preferences = getSharedPreferences(MYPREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        System.out.println("setSharedPreferences called");
        System.out.println(key);
        System.out.println(value);
    }

    public String getSharedPreferences(String key) {
        preferences = getSharedPreferences(MYPREFERENCES, 0); //0 for private mode
        String keyValue = preferences.getString(key, "");
        System.out.println("getSharedPreferences called");
        System.out.println(key);
        System.out.println(keyValue);
        return keyValue;
    }


    //ImageView callable methods
//--------------------------------------------------------------------------------------------------
    /*public void loadProfileImg(String username){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference picRef = storageRef.child("profile/"+username+".jpg");

        ImageView imageView = findViewById(R.id.profileImageView);

        Glide.with(this)
                .load(picRef)
                .into(imageView);

        System.out.println("loadProfileImg done");
    }*/
}
