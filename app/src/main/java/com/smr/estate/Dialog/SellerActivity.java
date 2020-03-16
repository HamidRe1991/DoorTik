package com.smr.estate.Dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smr.estate.R;

public class SellerActivity extends AppCompatActivity
{
    TextView tvSelName, tvSelTell;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        tvSelName = findViewById(R.id.sellerName);
        tvSelTell = findViewById(R.id.sellerTell);
        btnExit = findViewById(R.id.btnClose);

        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SellerActivity.this.finish();
            }
        });

        tvSelName.setText(getIntent().getStringExtra("sellerName"));
        tvSelTell.setText(getIntent().getStringExtra("sellerTell"));
    }
}
