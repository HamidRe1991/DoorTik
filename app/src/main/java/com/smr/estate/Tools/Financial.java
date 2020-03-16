package com.smr.estate.Tools;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import wiadevelopers.com.library.MaskdEditText.MaskedEditText;

public class Financial extends AppCompatActivity
{
    private ImageView imgBack;
    private RadioButton rdbConvert, rdbCommission, rdbSale, rdbMortgage;
    private LinearLayout lnrConvert, lnrCommission, lnrSale, lnrRent;
    private AppCompatButton btnSubmitRent, btnSubmitSale, btnConvert;
    private MaskedEditText edtRealMortgage, edtRealRent, edtMortgage, edtRent, edtRentRent, edtRentMortgage, edtSale;
    private CoordinatorLayout coordinatorLayoutFinancial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);

        initialize();
    }

    private void initialize()
    {
        findViews();
        setUpListener();
    }

    private void findViews()
    {
        imgBack = findViewById(R.id.imgFinancialBack);

        rdbCommission = findViewById(R.id.rdbCommission);
        rdbConvert = findViewById(R.id.rdbConvert);
        rdbSale = findViewById(R.id.rdbSale);
        rdbMortgage = findViewById(R.id.rdbMortgage);

        lnrCommission = findViewById(R.id.lnrCommission);
        lnrConvert = findViewById(R.id.lnrConvert);
        lnrSale = findViewById(R.id.lnrSale);
        lnrRent = findViewById(R.id.lnrRent);

        edtRealMortgage = findViewById(R.id.edtRealMortgage);
        edtRealRent = findViewById(R.id.edtRealRent);
        edtMortgage = findViewById(R.id.edtMortgage);
        edtRent = findViewById(R.id.edtRent);
        edtRentRent = findViewById(R.id.edtRentRent);
        edtRentMortgage = findViewById(R.id.edtRentMortgage);
        edtSale = findViewById(R.id.edtSale);

        btnSubmitRent = findViewById(R.id.btnSubmitRent);
        btnSubmitSale = findViewById(R.id.btnSubmitSale);
        btnConvert = findViewById(R.id.btnConvert);

        coordinatorLayoutFinancial = findViewById(R.id.coordinatorFinancial);
    }

    private void setUpListener()
    {
        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        rdbConvert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lnrCommission.setVisibility(View.INVISIBLE);
                lnrConvert.setVisibility(View.VISIBLE);
            }
        });

        rdbCommission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lnrCommission.setVisibility(View.VISIBLE);
                lnrConvert.setVisibility(View.INVISIBLE);
            }
        });

        rdbSale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lnrSale.setVisibility(View.VISIBLE);
                lnrRent.setVisibility(View.INVISIBLE);
            }
        });

        rdbMortgage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lnrSale.setVisibility(View.INVISIBLE);
                lnrRent.setVisibility(View.VISIBLE);
            }
        });

        btnSubmitRent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!edtRentRent.getRawText().equals("") || !edtRentMortgage.getRawText().equals(""))
                {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);

                    Double a = Double.parseDouble(edtRentMortgage.getRawText());
                    Double b = a / 1000000;
                    a = b * 30000;
                    Double c = a + Double.parseDouble(edtRentRent.getRawText());
                    c = (c * 25) / 100;
                    a = (c * 9) / 100;
                    c = a + c;

                    int finalNum = (int) Math.round(c);
                    SnackBar.Create(coordinatorLayoutFinancial, "سهم پرداختی هر طرف: " + decimalFormat.format(finalNum) + " تومان",10);
                }
                else
                    SnackBar.Create(coordinatorLayoutFinancial, "لطفا تمام موارد را پر نمایید",3);
            }
        });

        btnSubmitSale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!edtSale.getRawText().equals(""))
                {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);

                    Double a = Double.parseDouble(edtSale.getRawText());
                    Double b = (a * 5) / 1000;
                    Double c = (b * 9) / 100;
                    a = b + c;

                    int finalNum = (int) Math.round(a);
                    SnackBar.Create(coordinatorLayoutFinancial, "سهم پرداختی هر طرف: " + decimalFormat.format(finalNum) + " تومان",10);
                } else
                    SnackBar.Create(coordinatorLayoutFinancial, "لطفا تمام موارد را پر نمایید",3);
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!edtRealRent.getRawText().equals("") || !edtRealMortgage.getRawText().equals("") || !edtMortgage.getRawText().equals(""))
                {
                    Double realRent = Double.parseDouble(edtRealRent.getRawText());
                    Double realMortgage = Double.parseDouble(edtRealMortgage.getRawText());
                    Double mortgage = Double.parseDouble(edtMortgage.getRawText());

                    edtRent.setText(ConvertRent(realRent, realMortgage, mortgage));
                }
                else
                    SnackBar.Create(coordinatorLayoutFinancial, "لطفا تمام موارد را پر نمایید",3);
            }
        });
    }

    private String ConvertRent(Double realRent, Double realMortgage, Double mortgage)
    {
        Double a = realMortgage - mortgage;
        a = a / 1000000;
        Double b = a * 30000;
        Double c = b + realRent;

        return String.valueOf(c);
    }
}
