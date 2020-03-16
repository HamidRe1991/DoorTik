package com.smr.estate.CreatePostItems;

import android.content.Context;
import android.view.View;

import com.smr.estate.R;

import org.json.JSONArray;

import wiadevelopers.com.library.DivarUtils;

public class CreatePossRootView
{
    protected static final int CONTAIN_ITEMS = 4;
    protected static final int CHECKBOX = 8;

    String title;
    String key;
    View view;
    private View coloredView;
    int type;

    protected static Context context;

    public CreatePossRootView(String title, String key, int type)
    {
        this.title = title;
        this.key = key;
        this.type = type;

        if (type == CONTAIN_ITEMS)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_contain_items, null);
        else if (type == CHECKBOX)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_check, null);
    }

    public static void setContext(Context context)
    {
        CreatePossRootView.context = context;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public JSONArray getData()
    {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(key);
        jsonArray.put("NoValue");
        return jsonArray;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public View getView()
    {
        return view;
    }

    public void setView(View view)
    {
        this.view = view;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void restoreData(String data)
    {

    }
}
