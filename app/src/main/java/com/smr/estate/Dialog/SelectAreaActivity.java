package com.smr.estate.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.smr.estate.Adapter.CategoriesAdapter;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Model.Category;
import com.smr.estate.R;

import java.util.ArrayList;

import wiadevelopers.com.library.DivarUtils;

public class SelectAreaActivity extends AppCompatActivity
{
    TextView txtTitle;
    ListView lstCategories;
    ArrayList<String> stackIds = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);
        initialize();
    }

    private void initialize()
    {
        findViews();
        setTypefaces();
        setupActivity();
        setListeners();
    }

    private void findViews()
    {
        txtTitle = findViewById(R.id.txtTitle);
        lstCategories = findViewById(R.id.lstCategories);
    }

    private void setTypefaces()
    {
        txtTitle.setTypeface(DivarUtils.face);
    }

    private void setupActivity()
    {
        adapter = new CategoriesAdapter(SelectAreaActivity.this, categories);
        lstCategories.setAdapter(adapter);
        assignArrayList("", false);

        ArrayList<String> itemCategories = new ArrayList<>();
        for (int i = 0; i < G.AREA.size(); i++)
            if (G.AREA.get(i).isLastNode())
                itemCategories.add(G.AREA.get(i).getTitle());
    }

    private void assignArrayList(String id, boolean isBackward)
    {
        categories.clear();
        for (int i = 0; i < G.AREA.size(); i++)
        {
            if (G.AREA.get(i).getId().length() == id.length() + 1)
                if (G.AREA.get(i).getId().startsWith(id))
                    categories.add(G.AREA.get(i));
        }

        adapter.notifyDataSetChanged();
        if (!isBackward)
            stackIds.add(id);
    }

    @Override
    public void onBackPressed()
    {
        if (stackIds.size() > 1)
        {
            stackIds.remove(stackIds.size() - 1);
            String id = stackIds.get(stackIds.size() - 1);
            lstCategories.setAnimation(DivarUtils.animSlideLeft());
            assignArrayList(id, true);
        } else
            super.onBackPressed();
    }

    private void setListeners()
    {
        lstCategories.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                if (!categories.get(position).isLastNode())
                {
                    assignArrayList(categories.get(position).getId(), false);
                    lstCategories.setAnimation(DivarUtils.animSlideRight());
                } else
                {
                    String mId = categories.get(position).getId();
                    String mTitle = categories.get(position).getTitle();
                    Intent intent = new Intent();
                    intent.putExtra(Constant.KEY_ID, mId);
                    intent.putExtra(Constant.KEY_TITLE, mTitle);
                    setResult(Constant.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
