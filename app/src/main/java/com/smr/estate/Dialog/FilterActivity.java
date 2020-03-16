package com.smr.estate.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smr.estate.Constant.Constant;
import com.smr.estate.Filter.ContainItems;
import com.smr.estate.Filter.FilterRootView;
import com.smr.estate.Filter.TwoInput;
import com.smr.estate.Filter.TwoState;
import com.smr.estate.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;

public class FilterActivity extends AppCompatActivity
{
    private TextView tvTitle, tvApplyFilter, tvClearFilter;

    private LinearLayout lnrInsertPoint;
    private ArrayList<FilterRootView> rootViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        int height = DivarUtils.heightPX;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height/2);
        getWindow().setGravity(Gravity.CENTER);

        initialize();
    }

    private void initialize()
    {
        FilterRootView.setExpandCollapseListener(new FilterRootView.expandCollapseListener()
        {
            @Override
            public void expandCollapse(FilterRootView filterRootView)
            {
                for (int i = 0; i < rootViews.size(); i++)
                {
                    FilterRootView rootView = rootViews.get(i);
                    if (filterRootView == rootView)
                    {
                        if (rootView.isExpand())
                            rootView.collapse();
                        else
                            rootView.expand();
                    } else
                    {
                        if (rootView.isExpand())
                            rootView.collapse();
                    }
                }
            }
        });

        findViews();
        setTypefaces();
        setupListeners();
        setData();
    }

    private void findViews()
    {
        tvTitle = findViewById(R.id.tvTitle);
        tvApplyFilter = findViewById(R.id.tvApply);
        tvClearFilter = findViewById(R.id.tvClearFilter);
        lnrInsertPoint = findViewById(R.id.lnrContainer);
    }

    private void setTypefaces()
    {
        tvTitle.setTypeface(DivarUtils.faceLight);
        tvApplyFilter.setTypeface(DivarUtils.face);
        tvClearFilter.setTypeface(DivarUtils.faceLight);
    }

    private void setData()
    {
        if (rootViews.size() != 0)
            rootViews.clear();

        rootViews.add(new TwoInput("قیمت", Constant.PRICE, "0", "0"));
        rootViews.add(new TwoInput("متراژ (متر مربع)", Constant.BREADTH, "0", "0"));

        ArrayList<String> itemsType = new ArrayList<>();
        itemsType.add("همه");
        itemsType.add("فروش");
        itemsType.add("رهن کامل");
        itemsType.add("رهن و اجاره");
        itemsType.add("پیش خرید");
        itemsType.add("معاوضه");
        itemsType.add("مشارکت");
        rootViews.add(new ContainItems("نوع آگهی", Constant.SELLING_TYPE, itemsType));

        rootViews.add(new TwoState("نمایش عکسدار ها", Constant.IMAGE, "همه", "عکسدار"));

        setupLayouts();
    }

    private void setupLayouts()
    {
        lnrInsertPoint.removeAllViews();
        for (int i = 0; i < rootViews.size(); i++)
        {
            View view = rootViews.get(i).getView();
            lnrInsertPoint.addView(view);
        }
    }

    private void setupListeners()
    {
        tvApplyFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HashMap hashMap = new HashMap();

                for (int i = 0; i < rootViews.size(); i++)
                {
                    if (rootViews.get(i).getJustData() != null)
                    {
                        if (rootViews.get(i).getKey().equals(Constant.SELLING_TYPE))
                        {
                            JSONObject object = new JSONObject();
                            try
                            {
                                object.put("type", rootViews.get(i).getJustData());
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            hashMap.put(Constant.SELLING_TYPE, object);
                        }
                        else
                            hashMap.put(rootViews.get(i).getKey(), rootViews.get(i).getJustData());
                    }
                }


                JSONObject object = new JSONObject(hashMap);
                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_FILTER_DATA_JSON, object.toString());
                setResult(Constant.RESULT_OK, intent);
                finish();
            }
        });

        tvClearFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HashMap hashMap = new HashMap();
                JSONObject object = new JSONObject(hashMap);
                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_FILTER_DATA_JSON, object.toString());
                setResult(Constant.RESULT_OK, intent);
                finish();
            }
        });
    }
}
