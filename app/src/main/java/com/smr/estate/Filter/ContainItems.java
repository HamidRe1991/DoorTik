package com.smr.estate.Filter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.smr.estate.Adapter.SpinnerAdapter;
import com.smr.estate.R;

import java.util.ArrayList;

public class ContainItems extends FilterRootView
{
    private ArrayList<String> spinnerItems;
    private Spinner spinner;

    public ContainItems(String title, String key, ArrayList<String> items)
    {
        super(title, key, items.get(0), FilterRootView.CONTAIN_ITEMS);

        super.data = super.defData;
        this.spinnerItems = items;
        initialize();
    }

    private void initialize()
    {
        findViews();
        setData();
        setupListeners();
    }

    private void findViews()
    {
        spinner = super.view.findViewById(R.id.filterContainItemsSpnrItems);
    }

    private void setData()
    {
        super.txtData.setText(data);
        SpinnerAdapter customAdapter = new SpinnerAdapter(spinnerItems);
        spinner.setAdapter(customAdapter);
    }

    private void setupListeners()
    {
        super.imgClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final int itemPos = 0;
                spinner.setSelection(itemPos);
                defData = "همه";
                ContainItems.super.data = defData;
                ContainItems.super.checkState();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                ContainItems.super.data = spinner.getSelectedItem().toString();
                ContainItems.super.sendData = String.valueOf(spinner.getSelectedItemId());
                ContainItems.super.checkState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }
}