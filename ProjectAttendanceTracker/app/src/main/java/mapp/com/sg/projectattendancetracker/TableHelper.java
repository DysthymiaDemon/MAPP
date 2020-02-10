package mapp.com.sg.projectattendancetracker;

import android.content.Context;

import java.util.ArrayList;

public class TableHelper {
    Context ctx;

    private String[] pastAttnHeaders = {String.valueOf(R.string.month), String.valueOf(R.string.attnRate), String.valueOf(R.string.leaves)};
    private String[][] pastAttn;

    public TableHelper(Context ctx){
        this.ctx = ctx;
    }

    public String[] getPastAttnHeaders(){
        return pastAttnHeaders;
    }

    public String[][] getPastAttn(){
        ArrayList<PastAttn> pastAttns = new DatabaseAdapter(ctx).getPastAttn();
        PastAttn pastAttnModel;

        pastAttn = new String[pastAttns.size()][3];

        for (int i=0; i<pastAttns.size();i++){

            pastAttnModel = pastAttns.get(i);

            pastAttn[i][0] = pastAttnModel.getMonth();
            pastAttn[i][1] = pastAttnModel.getAttnRate();
            pastAttn[i][2] = pastAttnModel.getLeaves();
        }

        return pastAttn;
    }
}
