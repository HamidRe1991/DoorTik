package com.smr.estate.Filter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.smr.estate.R;

public class TwoInput extends FilterRootView
{
    private String strFrom;
    private String strTo;
    private EditText edtFrom;
    private EditText edtTo;

    public TwoInput(String title, String key, String strFrom, String strTo)
    {
        super(title, key, strFrom + "-" + strTo, FilterRootView.TWO_INPUT);
        this.strFrom = strFrom;
        this.strTo = strTo;

        super.data = super.defData;

        initialize();
    }

    private void initialize()
    {
        findViews();
        setupListeners();
        setData();
    }

    private void findViews()
    {
        edtFrom = super.view.findViewById(R.id.edtFrom);
        edtTo = super.view.findViewById(R.id.edtTo);
    }

    private void setData()
    {
        edtTo.setText(strTo);
        edtFrom.setText(strFrom);
    }

    private void setupListeners()
    {
        super.imgClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TwoInput.super.data = defData;
                edtTo.setText(strTo);
                edtFrom.setText(strFrom);
                TwoInput.super.checkState();
            }
        });

        edtFrom.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String to = edtTo.getText().toString();
                String from = edtFrom.getText().toString();
                TwoInput.super.data = from + "-" + to;
                TwoInput.super.checkState();

            }
        });

        edtTo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String to = edtTo.getText().toString();
                String from = edtFrom.getText().toString();

                TwoInput.super.data = from + "-" + to;
                TwoInput.super.checkState();
            }
        });
    }
}