package com.smr.estate.CreatePostItems;

import android.widget.CheckBox;

import com.smr.estate.R;

import org.json.JSONArray;

import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;

public class Checkbox extends CreatePossRootView
{
    private String titleChk;
    private CheckBox chk;
    HashMap hashMap = new HashMap();
    JSONArray jsonArray = new JSONArray();

    public Checkbox(String title, String key)
    {
        super(title, key, CreatePossRootView.CHECKBOX);
        this.titleChk = title;

        initialize();
    }

    private void initialize()
    {
        findViews();
        setupViews();
    }

    private void findViews()
    {
        chk = super.view.findViewById(R.id.chk);
        chk.setTypeface(DivarUtils.face);
    }

    private void setupViews()
    {
        chk.setText(titleChk);
    }

    public JSONArray getData()
    {
        if (chk.isChecked())
        {
            jsonArray.put(super.key);
            jsonArray.put("uncountable");

            return jsonArray;
        }

        return null;
    }

    public void restoreData(String data)
    {
        if (data.equals("uncountable"))
            chk.setChecked(true);
    }
}
