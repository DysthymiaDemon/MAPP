package mapp.com.sg.projectattendancetracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.DETAILS;
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_REPORTPROB;
import static mapp.com.sg.projectattendancetracker.Constants.TITLE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;

public class reportProblemFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_USERNAME = "argUsername";

    private String username;
    private EditText reportTitle;
    private EditText reportDetail;
    private Button submitButt;

    private static DatabaseHelper databaseHelper;
    private static String[] FROM_REPORTPROB={_ID, USERNAME, TITLE, DETAILS};

    public static reportProblemFragment newInstance(String uName){
        reportProblemFragment fragment=new reportProblemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, uName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reportprob, container, false);
        getActivity().setTitle(R.string.repProblem);

        databaseHelper = DatabaseHelper.getInstance(getActivity());

        if(getArguments() != null){
            username = getArguments().getString(ARG_USERNAME);
            System.out.println(username);
        }

        submitButt = (Button) view.findViewById(R.id.submitRepButton);
        submitButt.setOnClickListener(this);
        return view;
    }

    public void submitReport(View button){
        //write to db
        System.out.println("submitForm triggered");
        addReportProblem(username);
        System.out.println("addRequestOther doneS");

        //check and confirm
        String selection = USERNAME+" = ?";
        String[] selectionArgs = {username};
        Cursor cursorCheck = getReportProblem(selection, selectionArgs);
        if(cursorCheck.moveToNext()){
            Toast.makeText(getActivity(),"Report Submitted!", Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), "ERROR NO RECORD FOUND", Toast.LENGTH_SHORT).show();
        }
    }

    private void addReportProblem(String username){
        //request other EditText title and detail
        reportTitle = getActivity().findViewById(R.id.reportTitleEditText);
        reportDetail = getActivity().findViewById(R.id.reportDetailEditText);
        System.out.println(reportTitle);
        System.out.println(reportDetail);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(TITLE, reportTitle.getText().toString());
        values.put(DETAILS, reportDetail.getText().toString());
        db.insertOrThrow(TABLE_NAME_REPORTPROB, null, values);
    }

    private Cursor getReportProblem(String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_REPORTPROB, FROM_REPORTPROB, selection, selectionArgs, null, null, null);
        Log.d("Cursor: ", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }

    @Override
    public void onClick(View v) {
        if(v == submitButt){
            submitReport(v);
        }
    }
}