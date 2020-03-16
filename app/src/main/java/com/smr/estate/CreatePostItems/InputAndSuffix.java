package com.smr.estate.CreatePostItems;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

import com.smr.estate.Activities.CreatePostActivity;
import com.smr.estate.Application.Masking;
import com.smr.estate.R;

import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;

public class InputAndSuffix extends CreatePostRootView
{
    private String hint;
    private EditText edtInput;
    private TextView txtTitle;
    private TextView txtUnit;

    public InputAndSuffix(String title, String key, String hint)
    {
        super(title, key, INPUT_AND_SELECT);
        this.hint = hint;
        initialize();
    }

    private void initialize()
    {
        findViews();
        setTypeFaces();
        setupViews();
    }

    private void setupViews()
    {
        edtInput.setHint(this.hint);
        txtTitle.setText(this.title);



   }

    private void setTypeFaces()
    {
        txtTitle.setTypeface(DivarUtils.faceLight);
        edtInput.setTypeface(DivarUtils.face);

        txtUnit.setTypeface(DivarUtils.faceLight);
    }


    private void findViews()
    {
        edtInput = super.view.findViewById(R.id.edtInput);
        txtTitle = super.view.findViewById(R.id.txtTitle);
        txtUnit = super.view.findViewById(R.id.txtUnit);

        //1
        edtInput.addTextChangedListener(new Masking());
    }

    public HashMap getData()
    {
        HashMap hashMap = new HashMap();
        String value = edtInput.getText().toString();
        hashMap.put(super.key, value);
        return hashMap;
    }

    public void restoreData(String data)
    {
        edtInput.setText(data);
    }
}