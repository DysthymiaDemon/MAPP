package mapp.com.sg.projectattendancetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.provider.BaseColumns._ID;
import static mapp.com.sg.projectattendancetracker.Constants.DETAILS;
import static mapp.com.sg.projectattendancetracker.Constants.NAME;
import static mapp.com.sg.projectattendancetracker.Constants.TITLE;
import static mapp.com.sg.projectattendancetracker.Constants.USERNAME;

public class requestOthersFragment extends Fragment {
    private static final String ARG_USERNAME = "argUsername";

    private String username;
    private String title;
    private String details;

    private static DatabaseHelper databaseHelper;
    private static String[] FROM_REQOTHERS = {_ID, USERNAME, NAME, TITLE, DETAILS};

    public static requestOthersFragment newInstance(String uName){
        requestOthersFragment fragment = new requestOthersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, uName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requestothers, container,false);

        //sui sheng your part
        //title  = (EditText) getActivity().findViewById(R.id.titleReqEditText);
        //details = (EditText) getActivity().findViewById(R.id.detailsReqEditText);

        //init db
        databaseHelper = DatabaseHelper.getInstance(getActivity());

        if(getArguments() != null){
            username = getArguments().getString(ARG_USERNAME);
        }
        return view;
    }
}
