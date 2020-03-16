package com.smr.estate.CreatePostItems;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.smr.estate.R;

import java.util.ArrayList;
import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;

public class CreatePostRootView
{
    protected static final int JUST_INPUT = 1;
    protected static final int INPUT_AND_SELECT = 2;
    protected static final int OPTIONAL = 3;
    protected static final int SELECT_IMAGE = 5;
    protected static final int INPUT_AND_SUFFIX_FOR_NUMBER = 6;
    protected static final int JUST_INPUT_NUMBER = 7;

    //1
    public static Typeface newFace;


    String title;

    String key;
    View view;
    private View coloredView;
    int type;

    protected static Context context;

    public CreatePostRootView(String title, String key, int type)
    {
        this.title = title;
        this.key = key;
        this.type = type;

        if (type == JUST_INPUT)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_just_input, null);

        else if (type == INPUT_AND_SELECT)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_input_and_suffix, null);
        else if (type == OPTIONAL)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_optional, null);
        else if (type == SELECT_IMAGE)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_select_image, null);
        else if (type == INPUT_AND_SUFFIX_FOR_NUMBER)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_input_and_suffix_for_number, null);
        else if (type == JUST_INPUT_NUMBER)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.cp_just_input_number, null);

        coloredView = view.findViewById(R.id.lnrContainer);
    }

    public static void setContext(Context context)
    {
        CreatePostRootView.context = context;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public HashMap getData()
    {
        HashMap hashMap = new HashMap();
        hashMap.put(key, "NoData");
        return hashMap;
    }

    public void coloredView()
    {
        coloredView.setBackgroundColor(Color.parseColor("#FFFFE9E9"));
    }


    public void noColoredView()
    {
        coloredView.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    public boolean isFill()
    {
        if (getData().get(key).equals("") || getData().get(key) == null)
            return false;
        else
            return true;
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

    public void restoreData(String data)  {  }

    public void restoreData(ArrayList<Bitmap> bitmap){ }
}
