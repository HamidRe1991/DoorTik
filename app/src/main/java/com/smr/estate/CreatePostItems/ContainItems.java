package com.smr.estate.CreatePostItems;

import android.widget.Spinner;
import android.widget.TextView;

import com.smr.estate.R;
import com.smr.estate.Adapter.SpinnerAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

import wiadevelopers.com.library.DivarUtils;

public class ContainItems extends CreatePossRootView
{
    private Spinner spinItems;
    private TextView txtTitle;

    private ArrayList<String> spinnerItems;
    JSONArray jsonArray = new JSONArray();

    public ContainItems(String title, String key, ArrayList<String> items)
    {
        super(title, key, CreatePossRootView.CONTAIN_ITEMS);
        this.spinnerItems = items;
        initialize();
    }

    private void initialize()
    {
        findViews();
        setTypefaces();
        setupViews();
    }

    private void findViews()
    {
        txtTitle = super.view.findViewById(R.id.txtTitle);
        spinItems = super.view.findViewById(R.id.spinItems);
    }

    private void setTypefaces()
    {
        txtTitle.setTypeface(DivarUtils.faceLight);
    }

    private void setupViews()
    {
        txtTitle.setText(super.title);
        SpinnerAdapter customAdapter = new SpinnerAdapter(spinnerItems);
        spinItems.setAdapter(customAdapter);
    }

    public JSONArray getData()
    {
        jsonArray.put(super.key);
        jsonArray.put(String.valueOf(spinItems.getSelectedItemPosition()));

        return jsonArray;
    }

    public void restoreData(String data)
    {
        for (int i = 0; i < spinnerItems.size(); i++)
        {
            if (data.equals(spinnerItems.get(i)))
            {
                spinItems.setSelection(i);
                break;
            }
        }
    }
}