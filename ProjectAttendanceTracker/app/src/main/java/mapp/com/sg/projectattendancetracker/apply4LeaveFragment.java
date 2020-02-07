package mapp.com.sg.projectattendancetracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;

public class apply4LeaveFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String leaveType;
    Date leaveFrom;
    Date leaveTo;
    String details;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_apply4leave, container,false);

        Spinner spinner = (Spinner) v.findViewById(R.id.leaveTypeSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.leaveTypeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Context context = parent.getContext();
        CharSequence text = selected;

        switch (position){
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
}
