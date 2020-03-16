package com.smr.estate.Filter;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.smr.estate.R;

import wiadevelopers.com.library.DivarUtils;

public class TwoState extends FilterRootView
{
    private String leftValue;
    private String rightValue;

    private Button btnLeft;
    private Button btnRight;

    private final static int SELECTED_COLOR = Color.parseColor("#CCCCCC");
    private final static int UN_SELECTED_COLOR = Color.parseColor("#EEEEEE");

    public TwoState(String title, String key, String leftValue, String rightValue)
    {
        super(title, key, leftValue, FilterRootView.TWO_STATE);
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        super.data = "همه";
        initialize();
    }

    private void initialize()
    {
        findViews();
        setTypefaces();
        setData();
        setupListeners();
    }

    private void findViews()
    {
        btnLeft = super.view.findViewById(R.id.btnLeft);
        btnRight = super.view.findViewById(R.id.btnRight);
    }

    private void setTypefaces()
    {
        btnLeft.setTypeface(DivarUtils.face);
        btnRight.setTypeface(DivarUtils.face);
    }

    private void setData()
    {
        btnLeft.setText(leftValue);
        btnRight.setText(rightValue);
        TwoState.super.checkState();
    }

    private void setupListeners()
    {
        super.imgClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TwoState.super.data = defData;
                btnLeft.setBackgroundColor(SELECTED_COLOR);
                btnRight.setBackgroundColor(UN_SELECTED_COLOR);
                TwoState.super.checkState();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TwoState.super.data = "همه";
                btnLeft.setBackgroundColor(SELECTED_COLOR);
                btnRight.setBackgroundColor(UN_SELECTED_COLOR);
                TwoState.super.checkState();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TwoState.super.data = "عکسدار";
                TwoState.super.sendData = "1";
                btnLeft.setBackgroundColor(UN_SELECTED_COLOR);
                btnRight.setBackgroundColor(SELECTED_COLOR);
                TwoState.super.checkState();
            }
        });
    }
}
