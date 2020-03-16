package com.smr.estate.CreatePostItems;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smr.estate.Application.Masking;
import com.smr.estate.R;

import java.util.HashMap;

import wiadevelopers.com.library.DivarUtils;
import wiadevelopers.com.library.ToolTips.Tooltip;

public class JustInputNumber extends CreatePostRootView
{
    private String hint;
    private String help;

    private ImageView imgHelp;
    private TextView txtTitle;
    private EditText edtInput;

    public JustInputNumber(String title, String key, String hint)
    {
        this(title, key, hint, null);
    }

    public JustInputNumber(String title, String key, String hint, String help)
    {
        super(title, key, CreatePostRootView.JUST_INPUT_NUMBER);
        this.hint = hint;
        this.help = help;
        initialize();
    }

    private void initialize()
    {
        findViews();
        setTypeFaces();
        setupViews();
        setupListeners();
    }

    private void findViews()
    {
        txtTitle = super.view.findViewById(R.id.txtTitle);
        imgHelp = super.view.findViewById(R.id.imgHelp);
        edtInput = super.view.findViewById(R.id.edtInput);
        //1
        edtInput.addTextChangedListener(new Masking());



    }

    private void setTypeFaces()
    {
        txtTitle.setTypeface(DivarUtils.face);
        edtInput.setTypeface(DivarUtils.face);

    }

    private void setupViews()
    {
        txtTitle.setText(this.title);
        edtInput.setHint(this.hint);

        //1
        // edtInput.addTextChangedListener(new Masking());

        if (this.help == null)
            imgHelp.setVisibility(View.GONE);
        else
            imgHelp.setVisibility(View.VISIBLE);
    }

    private void setupListeners()
    {
        imgHelp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new Tooltip.Builder(imgHelp)
                        .setText(help)
                        .setTypeface(DivarUtils.face)
                        .setCancelable(true)
                        .setGravity(Gravity.TOP)
                        .setBackgroundColor(Color.parseColor("#444444"))
                        .setCornerRadius(10f)
                        .setPadding(12f)
                        .setTextSize(12f)
                        .setTextColor(Color.parseColor("#FAFAFA"))
                        .show();
            }
        });
    }

    public String getHint()
    {
        return hint;
    }

    public void setHint(String hint)
    {
        this.hint = hint;
    }

    public String getHelp()
    {
        return help;
    }

    public void setHelp(String help)
    {
        this.help = help;
    }

    public HashMap getData()
    {
        HashMap hashMap = new HashMap();
        hashMap.put(super.key, edtInput.getText().toString().trim());
        return hashMap;
    }

    public void restoreData(String data)
    {
        edtInput.setText(data);
    }
}
