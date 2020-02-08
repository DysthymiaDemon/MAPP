package mapp.com.sg.projectattendancetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.DETAILS;
import static mapp.com.sg.projectattendancetracker.Constants.END;
import static mapp.com.sg.projectattendancetracker.Constants.START;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_APPLYLEAVE;
import static mapp.com.sg.projectattendancetracker.Constants.TYPE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;

public class apply4LeaveFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_USERNAME = "argUsername";

    private String username;
    private String leaveType;
    private EditText leaveFrom;
    private EditText leaveTo;
    private EditText details;

    private static DatabaseHelper databaseHelper;
    private static String[] FROM_APPLYLEAVE = {_ID, USERNAME, TYPE, START, END, DETAILS};

    public static apply4LeaveFragment newInstance(String uName){
        apply4LeaveFragment fragment = new apply4LeaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, uName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply4leave, container, false);

        //apply for leave dropdown list
        Spinner spinner = (Spinner) view.findViewById(R.id.leaveTypeSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.leaveTypeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //get all editText
        details = (EditText) getActivity().findViewById(R.id.detailsApplyEditText);
        leaveFrom = (EditText) getActivity().findViewById(R.id.startEditText);
        leaveTo = (EditText) getActivity().findViewById(R.id.endEditText);
        System.out.println(leaveFrom);
        System.out.println(leaveTo);

        //init db
        databaseHelper = DatabaseHelper.getInstance(getActivity());

        if(getArguments() != null){
            username = getArguments().getString(ARG_USERNAME);
        }

        final Button submitButton = (Button) getActivity().findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm(submitButton);
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Context context = parent.getContext();
        CharSequence text = selected;

        switch (position) {
            case 0:
                leaveType = "Childcare Leave";
                break;
            case 1:
                leaveType = "Medical Leave";
            case 2:
                leaveType = "Paternity Leave";
            case 3:
                leaveType = "Annual Leave";
            case 4:
                leaveType = "Emergency Leave";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    submitForm is fragment_apply4leave.xml
    <Button android:id="@+id/button" android:onClick="submitForm"/>*/
    public void submitForm(View button){
        //write to db
        System.out.println("submitForm triggered");
        addApply4Leave(username);
        System.out.println("addApply4Leave doneS");

        //check and confirm
        String selection = USERNAME+" = ?";
        String[] selectionArgs = {username};
        Cursor cursorCheck = getApply4Leave(selection, selectionArgs);
        if(cursorCheck != null){
            Toast.makeText(getActivity(),"Please enter your username", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "ERROR NO RECORD FOUND", Toast.LENGTH_SHORT).show();
        }
    }

    //db callable methods
//--------------------------------------------------------------------------------------------------
    private void addApply4Leave(String username){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(TYPE, leaveType);
        values.put(START, String.valueOf(leaveFrom));
        values.put(END, String.valueOf(leaveTo));
        values.put(DETAILS, String.valueOf(details));
        db.insertOrThrow(TABLE_NAME_APPLYLEAVE, null, values);
    }

    private Cursor getApply4Leave(String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_APPLYLEAVE, FROM_APPLYLEAVE, selection, selectionArgs, null, null, null);
        return cursor;
    }

}
