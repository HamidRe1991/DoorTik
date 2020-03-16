package com.smr.estate.Dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.smr.estate.Activities.MainActivity;
import com.smr.estate.Fragment.AdsFragment;
import com.smr.estate.R;

public class Confirmation extends Dialog implements
        android.view.View.OnClickListener {

    public Dialog dialog;
    public Button yes;
    public static TextView showMsg;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Confirmation(@NonNull Context context, String str) {
        super(context);
        msg = str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirmation);

        yes = findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);
        showMsg = findViewById(R.id.txt_dia);

        showMsg.setText(msg);

    }

    public static void sett(String strREq) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                //  c.finish();
                break;

            default:
                break;
        }
        dismiss();
    }
}