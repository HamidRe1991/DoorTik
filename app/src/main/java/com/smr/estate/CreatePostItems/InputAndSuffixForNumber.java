package com.smr.estate.CreatePostItems;

import android.widget.EditText;
import android.widget.TextView;

import com.smr.estate.Application.Masking;
import com.smr.estate.R;

import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;

public class InputAndSuffixForNumber extends CreatePostRootView
{
    private String hint;
    private String unit;

    private EditText edtInput;
    private TextView txtTitle;
    private TextView txtUnit;

    public InputAndSuffixForNumber(String title, String key, String hint, String unit)
    {
        super(title, key, INPUT_AND_SUFFIX_FOR_NUMBER);
        this.hint = hint;
        this.unit = unit;
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
        txtUnit.setText(this.unit);
        //1
        //edtInput.addTextChangedListener(new Masking());

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
        //1
        edtInput.addTextChangedListener(new Masking());

        txtTitle = super.view.findViewById(R.id.txtTitle);
        txtUnit = super.view.findViewById(R.id.txtUnit);
    }

    public HashMap getData()
    {
        HashMap hashMap = new HashMap();
        String value = edtInput.getText().toString().trim();
        hashMap.put(super.key, value);
        return hashMap;
    }

    public void restoreData(String data)
    {
        edtInput.setText(data);
    }
}