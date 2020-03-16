package com.smr.estate.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.smr.estate.Adapter.CitiesAdapter;
import com.smr.estate.Constant.Constant;
import com.smr.estate.R;

import java.util.ArrayList;

import wiadevelopers.com.library.DivarUtils;

public class SelectStateActivity extends AppCompatActivity
{
    TextView txtTitle;
    EditText edtSearch;
    ListView lstCities;
    CitiesAdapter citiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        initialize();

        int height = DivarUtils.heightPX;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height - (height / 3));
        getWindow().setGravity(Gravity.BOTTOM);
    }

    private void initialize()
    {
        findViews();
        setupActivity();
    }

    private void findViews()
    {
        txtTitle = findViewById(R.id.txtToolbarTitle);
        edtSearch = findViewById(R.id.edtSearch);
        lstCities = findViewById(R.id.lstCities);
    }

    private void setupActivity()
    {
        setCityData();
        setListeners();
    }

    private void setCityData()
    {
        final ArrayList<String> cities = new ArrayList<>();
        cities.add(Constant.ALBORZ);
        citiesAdapter = new CitiesAdapter(SelectStateActivity.this, cities);
        lstCities.setAdapter(citiesAdapter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        overridePendingTransition(0, R.anim.anim_slide_out_bottom);
    }

    private void setListeners()
    {
        lstCities.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TextView textView = (TextView) view;
                String city = textView.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("city", city);
                setResult(Constant.RESULT_OK, intent);
                finish();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher()
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
                citiesAdapter.getFilter().filter(edtSearch.getText().toString());
            }
        });
    }
}
