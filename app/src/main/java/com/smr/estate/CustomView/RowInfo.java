package com.smr.estate.CustomView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smr.estate.R;

import wiadevelopers.com.library.DivarUtils;

public class RowInfo
{
    private TextView txtKey;
    private TextView txtValue;

    private View view;

    public RowInfo(String Key, String Value)
    {
        view = DivarUtils.mLayoutInflater.inflate(R.layout.item_row_info, null);
        txtKey = view.findViewById(R.id.txtKey);
        txtValue = view.findViewById(R.id.txtValue);
        LinearLayout lnrParent = view.findViewById(R.id.lnrParent);
        txtValue.setTypeface(DivarUtils.face);
        txtKey.setTypeface(DivarUtils.faceLight);
        this.txtKey.setText(Key + " :");
        if (Value.equals("") || Value.equals("0"))
            lnrParent.setVisibility(View.GONE);
        else
            this.txtValue.setText(Value);
    }

    public View getView()
    {
        return this.view;
    }
}