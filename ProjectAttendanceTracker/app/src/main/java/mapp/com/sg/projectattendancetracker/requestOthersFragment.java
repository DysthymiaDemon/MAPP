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
import static mapp.com.sg.projectattendancetracker.Constants.TABLE_NAME_REQOTHERS;
import static mapp.com.sg.projectattendancetracker.Constants.TITLE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;

public class requestOthersFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_USERNAME = "argUsername";

    private String username;
    private EditText requestTitle;
    private EditText requestDetail;
    private Button submitBtn;

    private static DatabaseHelper databaseHelper;
    private static String[] FROM_REQUESTOTHER={_ID, USERNAME, TITLE, DETAILS};

    public static requestOthersFragment newInstance(String uName){
        requestOthersFragment fragment=new requestOthersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, uName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requestothers, container, false);
        getActivity().setTitle(R.string.reqOthers);

        databaseHelper = DatabaseHelper.getInstance(getActivity());

        if(getArguments() != null){
            username = getArguments().getString(ARG_USERNAME);
        }

        submitBtn = (Button) view.findViewById(R.id.submitReqButton);
        submitBtn.setOnClickListener(this);
        return view;
    }

    public void submitForm(View button){
        //write to db
        System.out.println("submitForm triggered");
        addRequestOther(username);
        System.out.println("addRequestOther doneS");

        //check and confirm
        String selection = USERNAME+" = ?";
        String[] selectionArgs = {username};
        Cursor cursorCheck = getRequestOther(selection, selectionArgs);
        if(cursorCheck.moveToNext()){
            Toast.makeText(getActivity(),"Request Submitted!", Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), "ERROR NO RECORD FOUND", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRequestOther(String username){
        //request other EditText title and detail
        requestTitle = (EditText)getActivity().findViewById(R.id.requestTitleEditText);
        requestDetail = (EditText)getActivity().findViewById(R.id.requestDetailEditText);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(TITLE, requestTitle.getText().toString());
        values.put(DETAILS, requestDetail.getText().toString());
        db.insertOrThrow(TABLE_NAME_REQOTHERS, null, values);
    }

    private Cursor getRequestOther(String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_REQOTHERS, FROM_REQUESTOTHER, selection, selectionArgs, null, null, null);
        Log.d("Cursor: ", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }

    @Override
    public void onClick(View v) {
        if(v == submitBtn){
            submitForm(v);
        }
    }
}