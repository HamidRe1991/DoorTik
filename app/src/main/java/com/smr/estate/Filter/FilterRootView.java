package com.smr.estate.Filter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smr.estate.R;

import wiadevelopers.com.library.DivarUtils;
import wiadevelopers.com.library.ExpandableLayout.ExpandableLayout;

public class FilterRootView
{
    protected static final int TWO_STATE = 1;
    protected static final int CONTAIN_ITEMS = 2;
    protected static final int TWO_INPUT = 3;

    protected LinearLayout lnrParent;
    protected TextView txtTitle;
    protected TextView txtData;
    protected ImageView imgTick;
    protected ImageView imgClear;
    protected ImageView imgArrow;
    protected ExpandableLayout expandableLayout;

    protected String title;
    protected String key;
    protected String defData;
    protected String data;
    protected String sendData;
    protected int type;
    protected View view;

    private static expandCollapseListener expandCollapseListener;

    public interface expandCollapseListener
    {
        void expandCollapse(FilterRootView filterRootView);
    }

    public FilterRootView(String title, String key, String defData, int type)
    {
        this.title = title;
        this.key = key;
        this.defData = defData;
        this.type = type;

        if (type == TWO_STATE)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.filter_two_state, null);
        else if (type == CONTAIN_ITEMS)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.filter_contain_items, null);
        else if (type == TWO_INPUT)
            this.view = DivarUtils.mLayoutInflater.inflate(R.layout.filter_two_input, null);

        initialize();
    }

    private void initialize()
    {
        finViews();
        setTypefaces();
        setupListeners();
        setData();
    }

    private void setData()
    {
        txtTitle.setText(title);
    }

    private void finViews()
    {
        lnrParent = view.findViewById(R.id.lnrParent);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtData = view.findViewById(R.id.txtData);
        imgArrow = view.findViewById(R.id.imgArrow);
        imgClear = view.findViewById(R.id.imgClear);
        imgTick = view.findViewById(R.id.imgTick);

        expandableLayout = view.findViewById(R.id.expandLayout);
    }

    public static void setExpandCollapseListener(FilterRootView.expandCollapseListener expandCollapseListener)
    {
        FilterRootView.expandCollapseListener = null;
        FilterRootView.expandCollapseListener = expandCollapseListener;
    }

    private void setupListeners()
    {
        lnrParent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (expandCollapseListener != null)
                    expandCollapseListener.expandCollapse(FilterRootView.this);
            }
        });
    }

    private void setTypefaces()
    {
        txtData.setTypeface(DivarUtils.faceLight);
        txtTitle.setTypeface(DivarUtils.faceLight);
    }

    protected Boolean checkState()
    {
        if (defData.equals(data))
        {
            imgTick.setVisibility(View.INVISIBLE);
            imgClear.setVisibility(View.INVISIBLE);

            txtData.setText(data);

            return false;
        } else
        {
            imgTick.setVisibility(View.VISIBLE);
            imgClear.setVisibility(View.VISIBLE);

            txtData.setText(data);

            return true;
        }
    }

    public void expand()
    {
        txtData.startAnimation(DivarUtils.slideOutBottom(200));
        imgArrow.setRotation(180);
        expandableLayout.expand();
    }

    public void collapse()
    {
        txtData.startAnimation(DivarUtils.slideInBottom(200));
        imgArrow.setRotation(0);
        expandableLayout.collapse();
    }

    public boolean isExpand()
    {
        return expandableLayout.isExpanded();
    }

    public View getView()
    {
        return view;
    }

    public String getKey()
    {
        return this.key;
    }

    public String getJustData()
    {
        if (this.sendData != null && this.checkState())
            return this.sendData;
        else
            return null;
    }
}
