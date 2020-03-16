package com.smr.estate.CreatePostItems;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.smr.estate.R;

import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;

public class Optional extends CreatePostRootView
{
    private String radioButtonTitle1;
    private String radioButtonTitle2;
    private String radioButtonTitle3;
    private String radioButtonTitle4;

    private RadioButton rdBtn1;
    private RadioButton rdBtn2;
    private RadioButton rdBtn3;
    private RadioButton rdBtn4;

    private TextView txtTitle;

    public Optional(String title, String key, String title1, String title2, String title3, String title4)
    {
        super(title, key, CreatePostRootView.OPTIONAL);
        this.radioButtonTitle1 = title1;
        this.radioButtonTitle2 = title2;
        this.radioButtonTitle3 = title3;
        this.radioButtonTitle4 = title4;

        initialize(4);
    }

    private void initialize(int numberOfOption)
    {
        findViews();
        setTypeFaces();
        setupViews(numberOfOption);
    }

    private void findViews()
    {
        txtTitle = super.view.findViewById(R.id.txtTitle);

        rdBtn1 = super.view.findViewById(R.id.rdBtn1);
        rdBtn2 = super.view.findViewById(R.id.rdBtn2);
        rdBtn3 = super.view.findViewById(R.id.rdBtn3);
        rdBtn4 = super.view.findViewById(R.id.rdBtn4);

    }

    private void setTypeFaces()
    {
        txtTitle.setTypeface(DivarUtils.faceLight);
        rdBtn1.setTypeface(DivarUtils.face);
        rdBtn2.setTypeface(DivarUtils.face);
        rdBtn3.setTypeface(DivarUtils.face);
        rdBtn4.setTypeface(DivarUtils.face);
    }

    private void setupViews(int numberOfOption)
    {
        txtTitle.setText(super.title);
        rdBtn1.setText(radioButtonTitle1);
        rdBtn2.setText(radioButtonTitle2);
        rdBtn3.setText(radioButtonTitle3);
        rdBtn4.setText(radioButtonTitle4);
    }

    public HashMap getData()
    {
        HashMap hashMap = new HashMap();

        String value = null;
        if (rdBtn1.isChecked())
            value = "1";
        else if (rdBtn2.isChecked())
            value = "2";
        else if (rdBtn3.isChecked())
            value = "3";
        else if (rdBtn4.isChecked())
            value = "4";
        hashMap.put(super.key, value);
        return hashMap;
    }

    public void restoreData(String data)
    {
        if (data.equals("1"))
            rdBtn1.setChecked(true);
        else if (data.equals("2"))
            rdBtn2.setChecked(true);
        else if (data.equals("3"))
            rdBtn3.setChecked(true);
        else if (data.equals("4"))
            rdBtn4.setChecked(true);
    }
}