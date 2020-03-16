package com.smr.estate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smr.estate.R;

import java.util.ArrayList;

import wiadevelopers.com.library.DivarUtils;

public class SpinnerAdapter extends ArrayAdapter<String>
{
    private ArrayList<String> data;
    LayoutInflater inflater;

    private final int CLOSE = 0;
    private final int OPEN = 1;

    public SpinnerAdapter(ArrayList<String> data)
    {
        super(DivarUtils.mContext, R.layout.item_spinner_item_open, data);
        this.data = data;
        inflater = DivarUtils.mLayoutInflater;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, parent, OPEN);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, parent, CLOSE);
    }

    public View getCustomView(int position, ViewGroup parent, int type)
    {
        View row = null;
        if (type == OPEN)
            row = inflater.inflate(R.layout.item_spinner_item_open, parent, false);
        else if (type == CLOSE)
            row = inflater.inflate(R.layout.item_spinner_item_close, parent, false);

        TextView txtSpinnerText = row.findViewById(R.id.txtSpinnerText);
        txtSpinnerText.setTypeface(DivarUtils.faceLight);
        txtSpinnerText.setText(data.get(position));
        return row;
    }
}
