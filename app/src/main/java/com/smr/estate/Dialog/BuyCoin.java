package com.smr.estate.Dialog;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smr.estate.Constant.Constant;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;

public class BuyCoin extends AppCompatActivity
{

    private TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven;
    private String cons_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

        setContentView(R.layout.activity_buy_coin);

        cons_id = Read.readDataFromStorage("user_id", "0", getApplicationContext());

        findViews();
        setUpListener();
    }

    private void findViews()
    {
        tvOne = findViewById(R.id.tvOne);
        tvTwo = findViewById(R.id.tvTwo);
        tvThree = findViewById(R.id.tvThree);
        tvFour = findViewById(R.id.tvFour);
        tvFive = findViewById(R.id.tvFive);
        tvSix = findViewById(R.id.tvSix);
        tvSeven = findViewById(R.id.tvSeven);
    }

    private void setUpListener()
    {
        tvOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "10");
            }
        });

        tvTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "50");
            }
        });

        tvThree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "100");
            }
        });

        tvFour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "200");
            }
        });

        tvFive.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "500");
            }
        });

        tvSix.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "1000");
            }
        });

        tvSeven.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCoin(cons_id, "2000");
            }
        });
    }

    private void getCoin(String cons_id, String coin)
    {
        //http://89.42.211.54/payRequest?cons_id=1&coin=1
        Intent your_browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BASE_URL + "PayRequest?" + "cons_id=" + cons_id + "&" + "coin=" + coin));
        startActivity(your_browser_intent);
    }
}
